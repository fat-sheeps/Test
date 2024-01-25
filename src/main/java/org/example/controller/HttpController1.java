package org.example.controller;

import cn.hutool.core.util.RandomUtil;
import com.netflix.hystrix.*;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;


@RestController()
@RequestMapping("a")
@Slf4j
public class HttpController1 {

    public static class MyHystrixCommand extends HystrixCommand<String> {
        private final Integer t;
        public MyHystrixCommand(String groupKey, String commandKey, Integer timeOut, int t) {
            super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(groupKey))
                    .andCommandKey(HystrixCommandKey.Factory.asKey(commandKey))
                    .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                            .withCircuitBreakerRequestVolumeThreshold(10000)
                            .withCircuitBreakerErrorThresholdPercentage(100) //默认【20 * 50% = 10】10秒内有10个错误的熔断
                            .withExecutionTimeoutInMilliseconds(timeOut)
                    )
                    //线程池属性配置
                    .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter()
                            .withCoreSize(5)
                            .withMaximumSize(8)
                            .withMaxQueueSize(8)
                            //任务拒绝的任务队列阈值
                            .withQueueSizeRejectionThreshold(8)));
            this.t = t;
        }

        @Override
        protected String run() throws InterruptedException {
            log.info("run! t:" + t);
            Thread.sleep(t);
//            HttpUtil.doPost("http://localhost:8888/server?time=" + t,null);
            return t + "";
        }
    }

    //设置超时时间 可通过接口修改超时时间
    private static int timeout = 100;
    private static String groupKey = "groupKey";
    private static String commandKey = "commandKey";
    @RequestMapping(value = "/timeout", produces = "application/json;charset=UTF-8")
    public Object timeout(int t, String group, String command) {
        timeout = t;
        groupKey = group;
        commandKey = command;
        return timeout;
    }
    @RequestMapping(value = "/hystrix", produces = "application/json;charset=UTF-8")
    public Object hystrix(int num) {

        int errors = 0;
        int totals = 0;
         List<String> results = new ArrayList<>();
        for (int i = 1; i <= num; i++) {
            long start = System.currentTimeMillis();
            int t = RandomUtil.randomInt(50, 200);
            try {
                totals ++;
                MyHystrixCommand command = new MyHystrixCommand(groupKey, commandKey, timeout, t);
                String res = command.execute();
                long end = System.currentTimeMillis();
                //记录程序执行时间 结束
                String s = i + "-Completed-timeout:" + timeout + ",执行time:[" + (end - start) + "],t:" + t;
//                log.info("errors：{}，totals：{}， 错误率：{}%" ,errors, totals, (errors * 100 / totals) );
                results.add(s);

            } catch (HystrixRuntimeException e) {
                log.error("HystrixRuntimeException:{}", e.getMessage());
                if (e.getMessage().contains("short-circuited")) {
                    errors = 0;
                    totals = 0;
                } else {
                    errors ++;
//                    log.info("errors：{}，totals：{}， 错误率：{}%" ,errors, totals, (errors * 100 / (totals == 0 ? 1 : totals)) );
                    long end = System.currentTimeMillis();
                    //记录程序执行时间 结束
                    String ex_str = ",执行time:[";
                    String s = i + "-Hystrixed-timeout:" + timeout + ex_str + (end - start) + "],t:" + t + ",e:" +e.getMessage();
                    //log.error(s);
                    results.add(s);
                }

            }
        }

        return results;
    }



}
