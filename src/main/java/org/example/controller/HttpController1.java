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
//                            .withCircuitBreakerRequestVolumeThreshold(10000)
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
//            log.info("run!");
            Thread.sleep(t);
//            HttpUtil.doPost("http://localhost:8888/server?time=" + t,null);
            return t + "";
        }
    }

    //设置超时时间
    private final static int timeout = 100;
    @RequestMapping(value = "/hystrix", produces = "application/json;charset=UTF-8")
    public Object hystrix(int num) throws InterruptedException, ExecutionException, TimeoutException {
        int errors = 0;
        int totals = 0;
         List<String> results = new ArrayList<>();
        for (int i = 1; i <= num; i++) {
            long start = System.currentTimeMillis();
            int t = RandomUtil.randomInt(50, 200);
            try {
                totals ++;
                Thread.sleep(100L);
                MyHystrixCommand command = new MyHystrixCommand("group", "command" + timeout, timeout, t);
                String res = command.execute();
                long end = System.currentTimeMillis();
                //记录程序执行时间 结束
                String s = i + "-Completed-timeout:" + timeout + ",执行time:[" + (end - start) + "],t:" + t;
                log.info("errors：{}，totals：{}， 错误率：{}%" ,errors, totals, (errors * 100 / totals) );
                results.add(s);

            } catch (HystrixRuntimeException e) {
                log.error("HystrixRuntimeException:{}", e.getMessage());
                if (e.getMessage().contains("short-circuited")) {
                    errors = 0;
                    totals = 0;
                } else {
                    errors ++;
                    log.info("errors：{}，totals：{}， 错误率：{}%" ,errors, totals, (errors * 100 / (totals == 0 ? 1 : totals)) );
                    long end = System.currentTimeMillis();
                    //记录程序执行时间 结束
                    String ex_str = ",执行time:[";
                    String s = i + "-Hystrixed-timeout:" + timeout + ex_str + (end - start) + "],t:" + t + "e:" +e.getMessage();
                    //log.error(s);
                    results.add(s);
                }

            }
        }

//        AtomicReference<AtomicInteger> errors = new AtomicReference<>(new AtomicInteger(0));
//        long start1 = System.currentTimeMillis();
//        ExecutorService executorService = Executors.newFixedThreadPool(1);
//        List<Future<String>> futures = new ArrayList<>();
//        for (int i = 1; i < num; i++) {
//            AtomicInteger j = new AtomicInteger(i);
////            futures.add(DspThreadPoolExecutor.getDspThreadPoolExecutor().submit(() -> {
//            futures.add(executorService.submit(() -> {
//
//                //记录程序执行时间 开始
//                long start = System.currentTimeMillis();
//                //随机生成100~2000ms的业务执行时长
//                int t = RandomUtil.randomInt(50, 200);
//                try {
//                    Thread.sleep(100L);
//                    MyHystrixCommand command = new MyHystrixCommand("group", "command" + timeout, timeout, t);
//                    String res = command.execute();
//                    long end = System.currentTimeMillis();
//                    //记录程序执行时间 结束
//                    String s = j + "-Completed-timeout:" + timeout + ",执行time:[" + (end - start) + "],t:" + t;
//                    log.info("错误率：" + (errors.get().get() * 100 / j.get()) + "%");
//                    return s;
//
//                } catch (HystrixRuntimeException e) {
//                    if (e.getMessage().contains("short-circuited")) {
//                        errors.set(new AtomicInteger(0));
//                        j.set(1);
//                    }
//                    errors.get().getAndIncrement();
//                    log.info("错误率：" + (errors.get().get() * 100 / j.get()) + "%");
//                    log.error("HystrixRuntimeException:{}", e.getMessage());
//                    long end = System.currentTimeMillis();
//                    //记录程序执行时间 结束
//                    String ex_str = ",执行time:[";
//                    String s = j + "-Hystrixed-timeout:" + timeout + ex_str + (end - start) + "],t:" + t + "e:" +e.getMessage();
//                    //log.error(s);
//                    return s;
//                }
//            }));
//        }
//
//        List<String> resultsTimeout = new ArrayList<>();
//        List<String> resultsReject = new ArrayList<>();
//        for (Future<String> future : futures) {
//            String result = future.get();
//            results.add(result);
//            if (result.contains("Hystrixed")) {
//                resultsTimeout.add(result);
//            }
//            if (result.contains("执行time-x:")) {
//                resultsReject.add(result);
//            }
//        }
        //executorService.shutdown();
        long start11 = System.currentTimeMillis();
//        log.info();
        return results;
    }



}
