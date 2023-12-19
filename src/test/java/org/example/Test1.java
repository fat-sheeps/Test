package org.example;


import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.netflix.hystrix.*;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.example.domain.DspHystrixCommand;
import org.example.domain.User;
import org.example.utils.HttpUtil;
import org.example.utils.MDCUtil;
import org.junit.jupiter.api.Test;
import org.springframework.util.StopWatch;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.lang.management.ManagementFactory;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

@Slf4j
public class Test1 {

    @Test
    public void test01(){
        System.out.println(1);
    }

    @Test
    public void test2() {

        System.out.println(~(-1L << 9L));
        System.out.println(32 * 32);
    }

    @Test
    public void test3() {
//        log.info("{}Application started!", "\u001B[29m");
//        log.info("{}Application started!", "\u001B[30m");
//        log.info("{}Application started!", "\u001B[31m");
//        log.info("{}Application started!", "\u001B[32m");
//        log.info("{}Application started!", "\u001B[33m");
//        log.info("{}Application started!", "\u001B[34m");
//        log.info("{}Application started!", "\u001B[35m");
//        log.info("{}Application started!", "\u001B[36m");
//        log.info("{}Application started!", "\u001B[37m");
//        log.info("{}Application started!", "\u001B[38m");

        log.trace("{}trace!", "");
        log.info("{}info!", "");
        log.error("{}error!", "");
        log.warn("{}warn!", "");
        log.debug("{}debug!", "");
        System.out.println(1);
        User user = new User();
    }

    @Test
    public void test4() throws InterruptedException, ExecutionException, TimeoutException {
        long start = System.currentTimeMillis();
        CountDownLatch countDownLatch = new CountDownLatch(5);
        ExecutorService executor = Executors.newFixedThreadPool(5);
        List<String> futures = new ArrayList<>();
        // 执行耗时操作，例如发送HTTP请求或数据库查询
        for (int i = 0; i < 5; i++) {
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

        String result = futures.toString();
        log.info(result);
        executor.shutdown(); // 关闭线程池


//        if (!countDownLatch.await(3, TimeUnit.SECONDS)) {
//            System.out.println("计数器已经等待了3s countDown的值还没有成为0！");
//        }
        long end = System.currentTimeMillis();
        log.info("HttpUtil.doGet 耗时：{}", end - start);
    }

    @Test
    public void test5() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(5);
        System.out.println(countDownLatch.getCount());
        for (int i = 0; i < 1; i++) {
            countDownLatch.countDown();
        }
        if (!countDownLatch.await(3, TimeUnit.SECONDS)) {
            System.out.println("计数器已经等待了3s countDown的值还没有成为0！");
        }
        System.out.println("end");
    }

    @Test
    public void test6() throws InterruptedException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("test1");
        Thread.sleep(2012L);
        stopWatch.stop();
        stopWatch.start("test2");
        Thread.sleep(2200L);
        if (stopWatch.isRunning()) {
            log.info("stopWatch.stop();");
            stopWatch.stop();
        }
        log.info("{} , {}", stopWatch.getLastTaskInfo().getTaskName(), stopWatch.getLastTaskInfo().getTimeNanos());
        stopWatch.start("test3");
        stopWatch.start("test3");
        Thread.sleep(2300L);
        stopWatch.stop();
        log.info("{}", stopWatch.prettyPrint());
    }

    @Test
    public void test7() throws ExecutionException, InterruptedException {
        int num = 50;
        ExecutorService executor = Executors.newFixedThreadPool(30);
        List<Future<String>> futures = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            int j = i;
            futures.add(executor.submit(() -> {
                long start = System.currentTimeMillis();
//                int timeout = RandomUtil.randomInt(700, 1500);
                int timeout = 1000;
                try {
                    //log.info("---------------开始-----------------{}", j);
                    DspHystrixCommand command = new DspHystrixCommand("group", "command" + timeout, timeout);
//                    log.info("{}",command);
//                    log.info("{}",command.getThreadPoolKey());
                    String res = command.execute();
                    long end = System.currentTimeMillis();
                    return j + "-completed-限制timeout:" + timeout + ",执行time:" + (end - start) + "业务耗时：" + res;

                } catch (HystrixRuntimeException e) {
                    //log.error(e.getMessage());
                    long end = System.currentTimeMillis();
                    return j + "-Hystrix-限制timeout:" + timeout + ",执行time:" + (end - start);
                }
            }));
        }
        List<String> results = new ArrayList<>();
        for (Future<String> future : futures) {
            results.add(future.get());
        }
        System.out.println(JSON.toJSONString(results));

    }

    @Test
    public void test8() throws ExecutionException, InterruptedException {
        int num = 50;
        ExecutorService executor = Executors.newFixedThreadPool(30);
        List<Future<String>> futures = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            futures.add(executor.submit(() -> {
                int t = RandomUtil.randomInt(0, 2000);
                Thread.sleep(t);
                return t+"";
            }));
        }
        List<String> results = new ArrayList<>();
        for (Future<String> future : futures) {
            results.add(future.get());
        }
        System.out.println(JSON.toJSONString(results));

    }

    @Test
    public void test9() throws UnknownHostException, SocketException {
        String mpid = "0";
        mpid += ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
        long maxWorkId = -1L ^ (-1L << 5L);
        System.out.println(mpid.hashCode() & 0xffff % (maxWorkId + 1));

        long maxDatacenterId = -1L ^ (-1L << 5L);
        long id = 0L;
        InetAddress ip = InetAddress.getLocalHost();
        NetworkInterface network = NetworkInterface.getByInetAddress(ip);
        if (network == null) {
            id = 1L;
        } else {
            byte[] mac = network.getHardwareAddress();
            //System.out.println(new String(mac, StandardCharsets.UTF_8));

            id = ((0x000000FF & (long) mac[mac.length - 1]) | (0x0000FF00 & (((long) mac[mac.length - 2]) << 8))) >> 6;
            id = id % (maxDatacenterId + 1);
        }

        System.out.println(id);

    }

    public static String encrypt(String aesKey, String data) {
        try {
            Key secretKey = new SecretKeySpec(aesKey.getBytes(StandardCharsets.UTF_8), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            // 初始化，设置为加密模式
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] plaintext = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
            // 执行操作
            return Base64.encodeBase64URLSafeString(plaintext);
        } catch (Exception ex) {
            return null;
        }
    }

    @Test
    public void test10() {
        MDCUtil.setTraceId();
        log.info(encrypt("0123456789syabcd","4055"));
    }

    @Test
    public void test11() {
        System.out.println(ThreadLocalRandom.current().nextInt(100, 300));
        int a = (int) (225 * 1.5);
        System.out.println(a);
    }

    @Test
    public void test12() throws ExecutionException, InterruptedException {
        int num = 50;
        ExecutorService executor = Executors.newFixedThreadPool(30);
        List<Future<String>> futures = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            int j = i;
            futures.add(executor.submit(() -> {
                long start = System.currentTimeMillis();
                int t = RandomUtil.randomInt(700, 1500);
                int timeout = 1000;
                try {
                    //log.info("---------------开始-----------------{}", j);
                    DspHystrixCommand command = new DspHystrixCommand("group", "command" + timeout, timeout, t);
//                    log.info("{}",command);
//                    log.info("{}",command.getThreadPoolKey());
                    String res = command.execute();
                    long end = System.currentTimeMillis();
                    return j + "-completed-限制timeout:" + timeout + ",执行time:" + (end - start) + "业务耗时：" + res;

                } catch (HystrixRuntimeException e) {
                    //log.error(e.getMessage());
                    long end = System.currentTimeMillis();
                    return j + "-Hystrix-限制timeout:" + timeout + ",执行time:" + (end - start);
                }
            }));
        }
        List<String> results = new ArrayList<>();
        for (Future<String> future : futures) {
            results.add(future.get());
        }
//        System.out.println(JSON.toJSONString(results));
        for (String result : results) {
            log.info(result);
        }
    }
    public static class MyHystrixCommand extends HystrixCommand<String> {
        private final Integer t;
        public MyHystrixCommand(String groupKey, String commandKey, Integer timeOut, int t) {
            super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(groupKey))
                    .andCommandKey(HystrixCommandKey.Factory.asKey(commandKey))
                    .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
//                            .withCircuitBreakerRequestVolumeThreshold(10000)
//                            .withCircuitBreakerErrorThresholdPercentage(99)
                                    .withExecutionTimeoutInMilliseconds(timeOut)
                    )
                    //线程池属性配置
                    .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter()
                            .withCoreSize(225)
                            .withMaxQueueSize(550)
                            //任务拒绝的任务队列阈值
                            .withQueueSizeRejectionThreshold(550)));
            this.t = t;
        }

        @Override
        protected String run() {
            long i = 0;
            log.info("i:{}", i);
            for (; i < 10000000000L; i++) {
                if (i % 100000000L == 0) {
                    log.info("i:{}", i);
                }
            }
            return i + "";
        }
    }

    @Test
    public void test15() throws InterruptedException {
        long start = System.currentTimeMillis();
        long i = 0;
        log.info("i:{}", i);
        for (; i < 10000000000L; i++) {
            if (i % 100000000L == 0) {
                log.info("i:{}", i);
            }
        }

        long end = System.currentTimeMillis();
        Thread.sleep(10000L);
        System.out.println(end - start);
    }
    /**
     * 测试计算类型熔断
     */
    @Test
    public void test13() throws InterruptedException {
        long start = System.currentTimeMillis();
        try {
            String res = new MyHystrixCommand("group", "command", 100, 1).execute();
        } catch (HystrixRuntimeException e) {
            log.error("HystrixRuntimeException:{}", e.getMessage());

        }
        System.out.println();
        long end = System.currentTimeMillis();
        System.out.println(end - start);
        Thread.sleep(10000L);
    }

    public static class HttpHystrixCommand extends HystrixCommand<String> {
        private final Integer t;
        public HttpHystrixCommand(String groupKey, String commandKey, Integer timeOut, int t) {
            super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(groupKey))
                    .andCommandKey(HystrixCommandKey.Factory.asKey(commandKey))
                    .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
//                            .withCircuitBreakerRequestVolumeThreshold(10000)
//                            .withCircuitBreakerErrorThresholdPercentage(99)
                                    .withExecutionTimeoutInMilliseconds(timeOut)
                    )
                    //线程池属性配置
                    .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter()
                            .withCoreSize(225)
                            .withMaxQueueSize(550)
                            //任务拒绝的任务队列阈值
                            .withQueueSizeRejectionThreshold(550)));
            this.t = t;
        }

        @Override
        protected String run() {
            String result = HttpUtil.doPost("http://localhost:8888/server?time=" + t, null);
            return result;
        }
    }
    /**
     * 测试http类型熔断
     */
    @Test
    public void test14() throws InterruptedException {
        String string =  getString();
        //Thread.sleep(10000L);
    }

    private static String getString() {
        String res = null;
        long start = System.currentTimeMillis();
        try {
            res = new HttpHystrixCommand("group", "command", 1000, 2000).execute();
        } catch (HystrixRuntimeException e) {
            log.error("HystrixRuntimeException:{}", e.getMessage());

        }
        System.out.println(res);
        long end = System.currentTimeMillis();
        System.out.println(end - start);
        return "aaa";
    }

    public static class TestHystrixCommand extends HystrixCommand<String> {
        private final Integer t;
        public TestHystrixCommand(String groupKey, String commandKey, Integer timeOut, int t) {
            super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(groupKey))
                    .andCommandKey(HystrixCommandKey.Factory.asKey(commandKey))
                    .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                            .withCircuitBreakerRequestVolumeThreshold(10000)
                            .withCircuitBreakerErrorThresholdPercentage(99)
                                    .withExecutionTimeoutInMilliseconds(timeOut)
                    )
                    //线程池属性配置
                    .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter()
                            .withCoreSize(225)
                            .withMaxQueueSize(550)
                            //任务拒绝的任务队列阈值
                            .withQueueSizeRejectionThreshold(550)));
            this.t = t;
        }

        @Override
        protected String run() throws Exception {
            if (t % 2 == 0) {
                Thread.sleep(1100L);
                throw new Exception("error!");
            }
            return "success";
        }
    }
    @Test
    public void test16() throws InterruptedException {
        long start = System.currentTimeMillis();
        try {
            String res = new TestHystrixCommand("group", "command", 1000, 10).execute();
        } catch (HystrixRuntimeException e) {
            log.error("HystrixRuntimeException:", e);

        } /*catch (SocketTimeoutException e) {
            log.error("SocketTimeoutException:", e);

        }*/catch (Exception e) {
            log.error("Exception:", e);

        }
        Thread.sleep(2000L);
        long end = System.currentTimeMillis();
        System.out.println(end - start);

    }

    @Test
    public void test17() throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();
        int num = 5000;
        ExecutorService executor = Executors.newFixedThreadPool(5);
        List<Future<String>> futures = new ArrayList<>();
        for (int i = 0; i < num; i++) {
//            StopWatch stopWatch = new StopWatch();
            futures.add(executor.submit(() -> {
                try {
                    Thread.sleep(11L);
                    //int j = 1 / 0;
                    log.info("e:{}","e.getMessage()");
//                    stopWatch.start("test1");
//                    Thread.sleep(5L);
//                    stopWatch.stop();
//
//                    stopWatch.start("test2");
//                    Thread.sleep(6L);
//                    stopWatch.stop();
//                    stopWatch.stop();
                } catch (Exception e) {
                    log.error("e:{}",e.getMessage());
                }


                return Thread.currentThread().getName() + "-" + "stopWatch";
            }));
        }
        List<String> results = new ArrayList<>();
        for (Future<String> future : futures) {
            results.add(future.get());
        }
//        for (String result : results) {
//            log.info(result);
//        }
        System.out.println(System.currentTimeMillis() - start);
    }


}
