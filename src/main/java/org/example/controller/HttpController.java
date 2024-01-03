package org.example.controller;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.example.config.MdcThreadPoolExecutor;
import org.example.domain.AdxCommon;
import org.example.domain.DspHystrixCommand;
import org.example.service.CommonService;
import org.example.service.RunService;
import org.example.service.TaskService;
import org.example.service.TranService;
import org.example.utils.IPUtil;
import org.example.utils.SystemUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.instrument.Instrumentation;
import java.util.*;
import java.util.concurrent.*;


@RestController()
@RequestMapping("")
@Slf4j
public class HttpController {

    @Autowired
    private TaskService taskService;

    private ExecutorService executorService;

    @Autowired
    private CommonService commonService;


    private Cache<String, String> cache = CacheBuilder.newBuilder().expireAfterWrite(10, TimeUnit.SECONDS).build();

    //方法入参和返回参数都必须与被服务降级的方法一致
    public Map<String, Object> paymentOkFallbackHandler(long time) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("code", 200);
        resultMap.put("message", "paymentOk超时，服务降级了");
        resultMap.put("time", time);
        return resultMap;
    }

    //    @HystrixCommand(fallbackMethod = "paymentOkFallbackHandler", commandKey= "server", commandProperties = {
//        //value表示当前线程的超时时间为3s
//        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")})
    @RequestMapping(value = "/server")
    public Object server(@RequestParam long time) throws InterruptedException {
        log.error("error");
//        log.info("Thread.currentThread().getName():{}", Thread.currentThread().getName());
//        long start = System.currentTimeMillis();
//        log.info("-------------------executorService start: {}", start);
//        String ip = IPUtil.getIp();
//        String port = IPUtil.getPort();
//        String str = "Server IP :" + ip + " port: " + port;
//        log.info("-------------------str: {}", str);

//        executorService = Executors.newSingleThreadExecutor();
//        executorService.submit(new RunService(1));
//        MdcThreadPoolExecutor threadPoolExecutor = MdcThreadPoolExecutor.getMdcThreadPoolExecutor();
//        threadPoolExecutor.submit(new RunService(1));
//        taskService.async(ip);
//        TranService.getInstance().doProcess("do tran!");
        Thread.sleep(time);
        long end = System.currentTimeMillis();
//        log.info("-------------------executorService timeout: {}", end - start);
        return commonService.queryAllUser();
    }

    @RequestMapping(value = "/hello")
    public Object hello() throws InterruptedException {
//        long start = System.currentTimeMillis();
//        taskService.getMsgMap();
//        long end = System.currentTimeMillis();
//        log.info("time:{}", end - start);
//        cache.put("1", "11");
//        System.out.println((cache.getIfPresent("1")));
//        Thread.sleep(5100L);
//        System.out.println((cache.getIfPresent("1")));
//        cache.put("2", "成功");
//        Thread.sleep(5100L);
//        System.out.println((cache.getIfPresent("1")));
//        System.out.println((cache.getIfPresent("2")));
//        Thread.sleep(5100L);
        Thread.sleep(150L);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("1","你好");
        return jsonObject;

    }

    private ScheduledExecutorService scheduledExecutorService;
    @RequestMapping(value = "/stop")
    public void stop(){
        scheduledExecutorService.shutdown();
    }
    @RequestMapping(value = "/hystrix", produces = "application/json;charset=UTF-8")
    public Object hystrix(int num) throws InterruptedException, ExecutionException {
        scheduledExecutorService = Executors.newScheduledThreadPool(1000);
        AdxCommon.num.set(0);
        for (int i = 0; i < num; i++) {
            scheduledExecutorService.scheduleAtFixedRate(new AdxTask(), (int)(i*(1000.0/num)), 1000, TimeUnit.MILLISECONDS);

        }
//        List<Future<String>> futures = new ArrayList<>();
//        for (int i = 0; i < num; i++) {
//            futures.add(MdcThreadPoolExecutor.getMdcThreadPoolExecutor().submit(() -> {
//                int timeout = 1000;
//                long start = System.currentTimeMillis();
//                int t = RandomUtil.randomInt(0, 2000);
//                try {
//                    //log.info("---------------开始-----------------{}", j);
//                    DspHystrixCommand command = new DspHystrixCommand("group", "command" + timeout, timeout, t);
//                    //log.info("t:{}---------------DspHystrixCommand-----------------{}",t, System.currentTimeMillis() - start);
//                    String res = command.execute();
//                    //log.info("t:{}---------------command.execute()-----------------{}",t, System.currentTimeMillis() - start);
//                    long end = System.currentTimeMillis();
//                    String s = "-Completed-timeout:" + timeout + ",执行time:[" + (end - start) + "],t：" + res + ",t:" + t;
//                    log.info(s);
//                    return s;
//
//                } catch (HystrixRuntimeException e) {
//                    //log.error(e.getMessage());
//                    long end = System.currentTimeMillis();
//                    String s = "-Hystrixed-timeout:" + timeout + ",执行time:[" + (end - start) + "],t:" + t;
//                    log.error(s);
//                    return s;
//                }
//            }));
//        }
//        List<String> results = new ArrayList<>();
//        for (Future<String> future : futures) {
//            results.add(future.get());
//        }
        return "results";
    }

    /**
     * 这个是有效流控的
     * @param num
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     */
    @RequestMapping(value = "/hystrix1", produces = "application/json;charset=UTF-8")
    public Object hystrix1(int num) throws InterruptedException, ExecutionException {
        long start = System.currentTimeMillis();
        List<CompletableFuture<String>> futures = new ArrayList<>();


        for (int i = 0; i < num; i++) {
            int j = i;
            CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
                return callHttp("http:" + j); // 替换为实际的接口地址
            });

            CompletableFuture<String> timedFuture = future
                    //限制响应耗时150毫秒内
                    //.completeOnTimeout("timeout!", 150, TimeUnit.MILLISECONDS)
                    .thenApply(response -> {
                        // 处理超时
                        //log.error("HTTP request timed out");
                        // 或者其他标识，视需求而定
                        return response;
                    });

            futures.add(timedFuture);
        }

        //将请求结果放在results中 包括timeout的
        List<String> results = new ArrayList<>();
        for (CompletableFuture<String> future : futures) {
            String response = future.get();
            if (response != null) {
                results.add(response);
            }
        }

        // 打印成功的响应
        log.info("Successful Responses: " + results);
        long end = System.currentTimeMillis();
        log.info("end time: " + (end - start));
        return results;
    }

    @RequestMapping(value = "/hystrix2", produces = "application/json;charset=UTF-8")
    public Object hystrix2(int num) throws InterruptedException, ExecutionException {
        long start = System.currentTimeMillis();
        CountDownLatch countDownLatch = new CountDownLatch(num);
        ExecutorService executor = Executors.newFixedThreadPool(num);
        List<String> futures = new ArrayList<>();
        // 执行耗时操作，例如发送HTTP请求或数据库查询
        for (int i = 0; i < num; i++) {
            Future<String> future = executor.submit(() -> {

                int t = RandomUtil.randomInt(10, 400);
                try {
                    log.info(" t:{}", t);
                    Thread.sleep(t);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return "Task completed";
            });
            String res;
            try {
                res = future.get(150, TimeUnit.MILLISECONDS);
            } catch (TimeoutException e) {
                //log.error("error:{}", e.getMessage());
                res = "timeout!";
            }
            futures.add(res);
            countDownLatch.countDown();

        }
        long end = System.currentTimeMillis();
        log.info("HttpUtil.doGet 耗时：{}", end - start);

        String result = futures.toString();
        log.info(result);
        executor.shutdown(); // 关闭线程池

        long end2 = System.currentTimeMillis();
        log.info("HttpUtil.doGet 耗时：{}", end2 - start);
        return result;
    }

    private static String callHttp(String url) {
        //模拟http请求耗时100~200毫秒
        int t = RandomUtil.randomInt(100, 200);
        try {
            log.info("{} t:{}", url, t);
            Thread.sleep(t);
        } catch (Exception e) {
            log.error("error:",e);
        }

        return url + " time:" + t;
    }

    private final static List<String> list = new ArrayList<>();
    @RequestMapping(value = "/memory")
    public Object memory(@RequestParam long num) {
        String str = "2023-12-08 18:19:41,128 ERROR [] pool-1-thread-51    |o.e.controller.HttpController1:70  |HystrixRuntimeException:\n" +
                "com.netflix.hystrix.exception.HystrixRuntimeException: command1000 fallback execution rejected.\n" +
                "\tat com.netflix.hystrix.AbstractCommand.handleFallbackRejectionByEmittingError(AbstractCommand.java:1043)\n" +
                "\tat com.netflix.hystrix.AbstractCommand.getFallbackOrThrowException(AbstractCommand.java:875)\n" +
                "\tat com.netflix.hystrix.AbstractCommand.handleTimeoutViaFallback(AbstractCommand.java:997)\n" +
                "\tat com.netflix.hystrix.AbstractCommand.access$500(AbstractCommand.java:60)\n" +
                "\tat com.netflix.hystrix.AbstractCommand$12.call(AbstractCommand.java:609)\n" +
                "\tat com.netflix.hystrix.AbstractCommand$12.call(AbstractCommand.java:601)\n" +
                "\tat rx.internal.operators.OperatorOnErrorResumeNextViaFunction$4.onError(OperatorOnErrorResumeNextViaFunction.java:140)\n" +
                "\tat rx.internal.operators.OnSubscribeDoOnEach$DoOnEachSubscriber.onError(OnSubscribeDoOnEach.java:87)\n" +
                "\tat rx.internal.operators.OnSubscribeDoOnEach$DoOnEachSubscriber.onError(OnSubscribeDoOnEach.java:87)\n" +
                "\tat com.netflix.hystrix.AbstractCommand$HystrixObservableTimeoutOperator$1.run(AbstractCommand.java:1142)\n" +
                "\tat com.netflix.hystrix.strategy.concurrency.HystrixContextRunnable$1.call(HystrixContextRunnable.java:41)\n" +
                "\tat com.netflix.hystrix.strategy.concurrency.HystrixContextRunnable$1.call(HystrixContextRunnable.java:37)\n" +
                "\tat com.netflix.hystrix.strategy.concurrency.HystrixContextRunnable.run(HystrixContextRunnable.java:57)\n" +
                "\tat com.netflix.hystrix.AbstractCommand$HystrixObservableTimeoutOperator$2.tick(AbstractCommand.java:1159)\n" +
                "\tat com.netflix.hystrix.util.HystrixTimer$1.run(HystrixTimer.java:99)\n" +
                "\tat java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:511)\n" +
                "\tat java.util.concurrent.FutureTask.runAndReset$$$capture(FutureTask.java:308)\n" +
                "\tat java.util.concurrent.FutureTask.runAndReset(FutureTask.java)\n" +
                "\tat java.util.concurrent.ScheduledThreadPoolExecutor$ScheduledFutureTask.access$301(ScheduledThreadPoolExecutor.java:180)\n" +
                "\tat java.util.concurrent.ScheduledThreadPoolExecutor$ScheduledFutureTask.run(ScheduledThreadPoolExecutor.java:294)\n" +
                "\tat java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)\n" +
                "\tat java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)\n" +
                "\tat java.lang.Thread.run(Thread.java:750)2023-12-08 18:19:41,128 ERROR [] pool-1-thread-51    |o.e.controller.HttpController1:70  |HystrixRuntimeException:\n" +
                "com.netflix.hystrix.exception.HystrixRuntimeException: command1000 fallback execution rejected.\n" +
                "\tat com.netflix.hystrix.AbstractCommand.handleFallbackRejectionByEmittingError(AbstractCommand.java:1043)\n" +
                "\tat com.netflix.hystrix.AbstractCommand.getFallbackOrThrowException(AbstractCommand.java:875)\n" +
                "\tat com.netflix.hystrix.AbstractCommand.handleTimeoutViaFallback(AbstractCommand.java:997)\n" +
                "\tat com.netflix.hystrix.AbstractCommand.access$500(AbstractCommand.java:60)\n" +
                "\tat com.netflix.hystrix.AbstractCommand$12.call(AbstractCommand.java:609)\n" +
                "\tat com.netflix.hystrix.AbstractCommand$12.call(AbstractCommand.java:601)\n" +
                "\tat rx.internal.operators.OperatorOnErrorResumeNextViaFunction$4.onError(OperatorOnErrorResumeNextViaFunction.java:140)\n" +
                "\tat rx.internal.operators.OnSubscribeDoOnEach$DoOnEachSubscriber.onError(OnSubscribeDoOnEach.java:87)\n" +
                "\tat rx.internal.operators.OnSubscribeDoOnEach$DoOnEachSubscriber.onError(OnSubscribeDoOnEach.java:87)\n" +
                "\tat com.netflix.hystrix.AbstractCommand$HystrixObservableTimeoutOperator$1.run(AbstractCommand.java:1142)\n" +
                "\tat com.netflix.hystrix.strategy.concurrency.HystrixContextRunnable$1.call(HystrixContextRunnable.java:41)\n" +
                "\tat com.netflix.hystrix.strategy.concurrency.HystrixContextRunnable$1.call(HystrixContextRunnable.java:37)\n" +
                "\tat com.netflix.hystrix.strategy.concurrency.HystrixContextRunnable.run(HystrixContextRunnable.java:57)\n" +
                "\tat com.netflix.hystrix.AbstractCommand$HystrixObservableTimeoutOperator$2.tick(AbstractCommand.java:1159)\n" +
                "\tat com.netflix.hystrix.util.HystrixTimer$1.run(HystrixTimer.java:99)\n" +
                "\tat java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:511)\n" +
                "\tat java.util.concurrent.FutureTask.runAndReset$$$capture(FutureTask.java:308)\n" +
                "\tat java.util.concurrent.FutureTask.runAndReset(FutureTask.java)\n" +
                "\tat java.util.concurrent.ScheduledThreadPoolExecutor$ScheduledFutureTask.access$301(ScheduledThreadPoolExecutor.java:180)\n" +
                "\tat java.util.concurrent.ScheduledThreadPoolExecutor$ScheduledFutureTask.run(ScheduledThreadPoolExecutor.java:294)\n" +
                "\tat java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)\n" +
                "\tat java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)\n" +
                "\tat java.lang.Thread.run(Thread.java:750)2023-12-08 18:19:41,128 ERROR [] pool-1-thread-51    |o.e.controller.HttpController1:70  |HystrixRuntimeException:\n" +
                "com.netflix.hystrix.exception.HystrixRuntimeException: command1000 fallback execution rejected.\n" +
                "\tat com.netflix.hystrix.AbstractCommand.handleFallbackRejectionByEmittingError(AbstractCommand.java:1043)\n" +
                "\tat com.netflix.hystrix.AbstractCommand.getFallbackOrThrowException(AbstractCommand.java:875)\n" +
                "\tat com.netflix.hystrix.AbstractCommand.handleTimeoutViaFallback(AbstractCommand.java:997)\n" +
                "\tat com.netflix.hystrix.AbstractCommand.access$500(AbstractCommand.java:60)\n" +
                "\tat com.netflix.hystrix.AbstractCommand$12.call(AbstractCommand.java:609)\n" +
                "\tat com.netflix.hystrix.AbstractCommand$12.call(AbstractCommand.java:601)\n" +
                "\tat rx.internal.operators.OperatorOnErrorResumeNextViaFunction$4.onError(OperatorOnErrorResumeNextViaFunction.java:140)\n" +
                "\tat rx.internal.operators.OnSubscribeDoOnEach$DoOnEachSubscriber.onError(OnSubscribeDoOnEach.java:87)\n" +
                "\tat rx.internal.operators.OnSubscribeDoOnEach$DoOnEachSubscriber.onError(OnSubscribeDoOnEach.java:87)\n" +
                "\tat com.netflix.hystrix.AbstractCommand$HystrixObservableTimeoutOperator$1.run(AbstractCommand.java:1142)\n" +
                "\tat com.netflix.hystrix.strategy.concurrency.HystrixContextRunnable$1.call(HystrixContextRunnable.java:41)\n" +
                "\tat com.netflix.hystrix.strategy.concurrency.HystrixContextRunnable$1.call(HystrixContextRunnable.java:37)\n" +
                "\tat com.netflix.hystrix.strategy.concurrency.HystrixContextRunnable.run(HystrixContextRunnable.java:57)\n" +
                "\tat com.netflix.hystrix.AbstractCommand$HystrixObservableTimeoutOperator$2.tick(AbstractCommand.java:1159)\n" +
                "\tat com.netflix.hystrix.util.HystrixTimer$1.run(HystrixTimer.java:99)\n" +
                "\tat java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:511)\n" +
                "\tat java.util.concurrent.FutureTask.runAndReset$$$capture(FutureTask.java:308)\n" +
                "\tat java.util.concurrent.FutureTask.runAndReset(FutureTask.java)\n" +
                "\tat java.util.concurrent.ScheduledThreadPoolExecutor$ScheduledFutureTask.access$301(ScheduledThreadPoolExecutor.java:180)\n" +
                "\tat java.util.concurrent.ScheduledThreadPoolExecutor$ScheduledFutureTask.run(ScheduledThreadPoolExecutor.java:294)\n" +
                "\tat java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)\n" +
                "\tat java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)\n" +
                "\tat java.lang.Thread.run(Thread.java:750)2023-12-08 18:19:41,128 ERROR [] pool-1-thread-51    |o.e.controller.HttpController1:70  |HystrixRuntimeException:\n" +
                "com.netflix.hystrix.exception.HystrixRuntimeException: command1000 fallback execution rejected.\n" +
                "\tat com.netflix.hystrix.AbstractCommand.handleFallbackRejectionByEmittingError(AbstractCommand.java:1043)\n" +
                "\tat com.netflix.hystrix.AbstractCommand.getFallbackOrThrowException(AbstractCommand.java:875)\n" +
                "\tat com.netflix.hystrix.AbstractCommand.handleTimeoutViaFallback(AbstractCommand.java:997)\n" +
                "\tat com.netflix.hystrix.AbstractCommand.access$500(AbstractCommand.java:60)\n" +
                "\tat com.netflix.hystrix.AbstractCommand$12.call(AbstractCommand.java:609)\n" +
                "\tat com.netflix.hystrix.AbstractCommand$12.call(AbstractCommand.java:601)\n" +
                "\tat rx.internal.operators.OperatorOnErrorResumeNextViaFunction$4.onError(OperatorOnErrorResumeNextViaFunction.java:140)\n" +
                "\tat rx.internal.operators.OnSubscribeDoOnEach$DoOnEachSubscriber.onError(OnSubscribeDoOnEach.java:87)\n" +
                "\tat rx.internal.operators.OnSubscribeDoOnEach$DoOnEachSubscriber.onError(OnSubscribeDoOnEach.java:87)\n" +
                "\tat com.netflix.hystrix.AbstractCommand$HystrixObservableTimeoutOperator$1.run(AbstractCommand.java:1142)\n" +
                "\tat com.netflix.hystrix.strategy.concurrency.HystrixContextRunnable$1.call(HystrixContextRunnable.java:41)\n" +
                "\tat com.netflix.hystrix.strategy.concurrency.HystrixContextRunnable$1.call(HystrixContextRunnable.java:37)\n" +
                "\tat com.netflix.hystrix.strategy.concurrency.HystrixContextRunnable.run(HystrixContextRunnable.java:57)\n" +
                "\tat com.netflix.hystrix.AbstractCommand$HystrixObservableTimeoutOperator$2.tick(AbstractCommand.java:1159)\n" +
                "\tat com.netflix.hystrix.util.HystrixTimer$1.run(HystrixTimer.java:99)\n" +
                "\tat java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:511)\n" +
                "\tat java.util.concurrent.FutureTask.runAndReset$$$capture(FutureTask.java:308)\n" +
                "\tat java.util.concurrent.FutureTask.runAndReset(FutureTask.java)\n" +
                "\tat java.util.concurrent.ScheduledThreadPoolExecutor$ScheduledFutureTask.access$301(ScheduledThreadPoolExecutor.java:180)\n" +
                "\tat java.util.concurrent.ScheduledThreadPoolExecutor$ScheduledFutureTask.run(ScheduledThreadPoolExecutor.java:294)\n" +
                "\tat java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)\n" +
                "\tat java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)\n" +
                "\tat java.lang.Thread.run(Thread.java:750)2023-12-08 18:19:41,128 ERROR [] pool-1-thread-51    |o.e.controller.HttpController1:70  |HystrixRuntimeException:\n" +
                "com.netflix.hystrix.exception.HystrixRuntimeException: command1000 fallback execution rejected.\n" +
                "\tat com.netflix.hystrix.AbstractCommand.handleFallbackRejectionByEmittingError(AbstractCommand.java:1043)\n" +
                "\tat com.netflix.hystrix.AbstractCommand.getFallbackOrThrowException(AbstractCommand.java:875)\n" +
                "\tat com.netflix.hystrix.AbstractCommand.handleTimeoutViaFallback(AbstractCommand.java:997)\n" +
                "\tat com.netflix.hystrix.AbstractCommand.access$500(AbstractCommand.java:60)\n" +
                "\tat com.netflix.hystrix.AbstractCommand$12.call(AbstractCommand.java:609)\n" +
                "\tat com.netflix.hystrix.AbstractCommand$12.call(AbstractCommand.java:601)\n" +
                "\tat rx.internal.operators.OperatorOnErrorResumeNextViaFunction$4.onError(OperatorOnErrorResumeNextViaFunction.java:140)\n" +
                "\tat rx.internal.operators.OnSubscribeDoOnEach$DoOnEachSubscriber.onError(OnSubscribeDoOnEach.java:87)\n" +
                "\tat rx.internal.operators.OnSubscribeDoOnEach$DoOnEachSubscriber.onError(OnSubscribeDoOnEach.java:87)\n" +
                "\tat com.netflix.hystrix.AbstractCommand$HystrixObservableTimeoutOperator$1.run(AbstractCommand.java:1142)\n" +
                "\tat com.netflix.hystrix.strategy.concurrency.HystrixContextRunnable$1.call(HystrixContextRunnable.java:41)\n" +
                "\tat com.netflix.hystrix.strategy.concurrency.HystrixContextRunnable$1.call(HystrixContextRunnable.java:37)\n" +
                "\tat com.netflix.hystrix.strategy.concurrency.HystrixContextRunnable.run(HystrixContextRunnable.java:57)\n" +
                "\tat com.netflix.hystrix.AbstractCommand$HystrixObservableTimeoutOperator$2.tick(AbstractCommand.java:1159)\n" +
                "\tat com.netflix.hystrix.util.HystrixTimer$1.run(HystrixTimer.java:99)\n" +
                "\tat java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:511)\n" +
                "\tat java.util.concurrent.FutureTask.runAndReset$$$capture(FutureTask.java:308)\n" +
                "\tat java.util.concurrent.FutureTask.runAndReset(FutureTask.java)\n" +
                "\tat java.util.concurrent.ScheduledThreadPoolExecutor$ScheduledFutureTask.access$301(ScheduledThreadPoolExecutor.java:180)\n" +
                "\tat java.util.concurrent.ScheduledThreadPoolExecutor$ScheduledFutureTask.run(ScheduledThreadPoolExecutor.java:294)\n" +
                "\tat java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)\n" +
                "\tat java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)\n" +
                "\tat java.lang.Thread.run(Thread.java:750)2023-12-08 18:19:41,128 ERROR [] pool-1-thread-51    |o.e.controller.HttpController1:70  |HystrixRuntimeException:\n" +
                "com.netflix.hystrix.exception.HystrixRuntimeException: command1000 fallback execution rejected.\n" +
                "\tat com.netflix.hystrix.AbstractCommand.handleFallbackRejectionByEmittingError(AbstractCommand.java:1043)\n" +
                "\tat com.netflix.hystrix.AbstractCommand.getFallbackOrThrowException(AbstractCommand.java:875)\n" +
                "\tat com.netflix.hystrix.AbstractCommand.handleTimeoutViaFallback(AbstractCommand.java:997)\n" +
                "\tat com.netflix.hystrix.AbstractCommand.access$500(AbstractCommand.java:60)\n" +
                "\tat com.netflix.hystrix.AbstractCommand$12.call(AbstractCommand.java:609)\n" +
                "\tat com.netflix.hystrix.AbstractCommand$12.call(AbstractCommand.java:601)\n" +
                "\tat rx.internal.operators.OperatorOnErrorResumeNextViaFunction$4.onError(OperatorOnErrorResumeNextViaFunction.java:140)\n" +
                "\tat rx.internal.operators.OnSubscribeDoOnEach$DoOnEachSubscriber.onError(OnSubscribeDoOnEach.java:87)\n" +
                "\tat rx.internal.operators.OnSubscribeDoOnEach$DoOnEachSubscriber.onError(OnSubscribeDoOnEach.java:87)\n" +
                "\tat com.netflix.hystrix.AbstractCommand$HystrixObservableTimeoutOperator$1.run(AbstractCommand.java:1142)\n" +
                "\tat com.netflix.hystrix.strategy.concurrency.HystrixContextRunnable$1.call(HystrixContextRunnable.java:41)\n" +
                "\tat com.netflix.hystrix.strategy.concurrency.HystrixContextRunnable$1.call(HystrixContextRunnable.java:37)\n" +
                "\tat com.netflix.hystrix.strategy.concurrency.HystrixContextRunnable.run(HystrixContextRunnable.java:57)\n" +
                "\tat com.netflix.hystrix.AbstractCommand$HystrixObservableTimeoutOperator$2.tick(AbstractCommand.java:1159)\n" +
                "\tat com.netflix.hystrix.util.HystrixTimer$1.run(HystrixTimer.java:99)\n" +
                "\tat java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:511)\n" +
                "\tat java.util.concurrent.FutureTask.runAndReset$$$capture(FutureTask.java:308)\n" +
                "\tat java.util.concurrent.FutureTask.runAndReset(FutureTask.java)\n" +
                "\tat java.util.concurrent.ScheduledThreadPoolExecutor$ScheduledFutureTask.access$301(ScheduledThreadPoolExecutor.java:180)\n" +
                "\tat java.util.concurrent.ScheduledThreadPoolExecutor$ScheduledFutureTask.run(ScheduledThreadPoolExecutor.java:294)\n" +
                "\tat java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)\n" +
                "\tat java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)\n" +
                "\tat java.lang.Thread.run(Thread.java:750)2023-12-08 18:19:41,128 ERROR [] pool-1-thread-51    |o.e.controller.HttpController1:70  |HystrixRuntimeException:\n" +
                "com.netflix.hystrix.exception.HystrixRuntimeException: command1000 fallback execution rejected.\n" +
                "\tat com.netflix.hystrix.AbstractCommand.handleFallbackRejectionByEmittingError(AbstractCommand.java:1043)\n" +
                "\tat com.netflix.hystrix.AbstractCommand.getFallbackOrThrowException(AbstractCommand.java:875)\n" +
                "\tat com.netflix.hystrix.AbstractCommand.handleTimeoutViaFallback(AbstractCommand.java:997)\n" +
                "\tat com.netflix.hystrix.AbstractCommand.access$500(AbstractCommand.java:60)\n" +
                "\tat com.netflix.hystrix.AbstractCommand$12.call(AbstractCommand.java:609)\n" +
                "\tat com.netflix.hystrix.AbstractCommand$12.call(AbstractCommand.java:601)\n" +
                "\tat rx.internal.operators.OperatorOnErrorResumeNextViaFunction$4.onError(OperatorOnErrorResumeNextViaFunction.java:140)\n" +
                "\tat rx.internal.operators.OnSubscribeDoOnEach$DoOnEachSubscriber.onError(OnSubscribeDoOnEach.java:87)\n" +
                "\tat rx.internal.operators.OnSubscribeDoOnEach$DoOnEachSubscriber.onError(OnSubscribeDoOnEach.java:87)\n" +
                "\tat com.netflix.hystrix.AbstractCommand$HystrixObservableTimeoutOperator$1.run(AbstractCommand.java:1142)\n" +
                "\tat com.netflix.hystrix.strategy.concurrency.HystrixContextRunnable$1.call(HystrixContextRunnable.java:41)\n" +
                "\tat com.netflix.hystrix.strategy.concurrency.HystrixContextRunnable$1.call(HystrixContextRunnable.java:37)\n" +
                "\tat com.netflix.hystrix.strategy.concurrency.HystrixContextRunnable.run(HystrixContextRunnable.java:57)\n" +
                "\tat com.netflix.hystrix.AbstractCommand$HystrixObservableTimeoutOperator$2.tick(AbstractCommand.java:1159)\n" +
                "\tat com.netflix.hystrix.util.HystrixTimer$1.run(HystrixTimer.java:99)\n" +
                "\tat java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:511)\n" +
                "\tat java.util.concurrent.FutureTask.runAndReset$$$capture(FutureTask.java:308)\n" +
                "\tat java.util.concurrent.FutureTask.runAndReset(FutureTask.java)\n" +
                "\tat java.util.concurrent.ScheduledThreadPoolExecutor$ScheduledFutureTask.access$301(ScheduledThreadPoolExecutor.java:180)\n" +
                "\tat java.util.concurrent.ScheduledThreadPoolExecutor$ScheduledFutureTask.run(ScheduledThreadPoolExecutor.java:294)\n" +
                "\tat java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)\n" +
                "\tat java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)\n" +
                "\tat java.lang.Thread.run(Thread.java:750)";
        for (int i = 0; i < num; i++) {
            list.add(str);
        }

        String string = SystemUtil.getMemoryInfoDetail();
        return string;
    }
    private static Instrumentation getInstrumentation() {
        try {
            // 通过反射获取当前线程的Instrumentation实例
            Class<?> instrumentationClass = Class.forName("java.lang.instrument.Instrumentation");
            return (Instrumentation) instrumentationClass.getMethod("getInitiatedClasses").invoke(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
