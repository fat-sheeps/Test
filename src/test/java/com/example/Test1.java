package com.example;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.Week;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.URLUtil;
import com.alibaba.fastjson.JSON;
import com.example.domain.*;
import com.example.enums.CountryEnum;
import com.example.exception.BizCode;
import com.example.exception.BizException;
import com.example.utils.AES;
import com.example.utils.HttpUtil;
import com.example.utils.SystemUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.f4b6a3.ulid.Ulid;
import com.google.common.collect.*;
import com.netflix.hystrix.*;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.core.util.UuidUtil;
import org.apache.tomcat.util.codec.binary.Base64;
import com.example.utils.MDCUtil;
import org.junit.jupiter.api.Test;
import org.springframework.util.DigestUtils;
import org.springframework.util.StopWatch;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.lang.instrument.Instrumentation;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryType;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.sql.Timestamp;
import java.time.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Function;
import java.util.stream.Collectors;

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
//        User user = new User();
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
        log.info(encrypt("0123456789syabcd","8000"));
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

    @Test
    public void test18() {
        long randomThreshold = (long) (10485760L * (1 + (Math.random() - 0.5) * 1));
        System.out.println();
    }

    @Test
    public void test19() {
//        Set<String> set2 = new HashSet<>();
//        Collections.addAll(set2, "apple", "banana", "orange");
//        System.out.println(set2);
//        Map<String, String> map = new HashMap<>();
//        map.put("", "");
//        System.out.println(map);

//        User user = new User();
//        List<Integer> types = new ArrayList<>();
//        types.add(1);
//        types.add(2);
//        types.add(3);
//
//        types.add(0,0);
//        user.setTypes(types);
//
//        System.out.println(user);
//
//        Set<String> set = new LinkedHashSet<>();
//        set.add("A");
//        set.add("B");
//        set.add("C");
//
//        System.out.println("原始集合: " + set);
//        List<String> list = new ArrayList<>(set);
//
//        list.add(0,"D"); // 在头部插入新元素"D"
//        set = new HashSet<>(list);
//
//        System.out.println("插入新元素后的集合: " + set);
//
//        Long a = null;
//        System.out.println((String) a);
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.addAll(Arrays.asList("Hello", "World", "!"));
        list.add("3");
        String[] array = list.toArray(new String[0]);;
        list = new ArrayList<>();
        System.out.println(list.contains("aaa"));


    }

    @Test
    public void test20() {
        StringBuilder str = new StringBuilder();
        for (MemoryPoolMXBean memoryPool : ManagementFactory.getMemoryPoolMXBeans()) {
            if (memoryPool.getType() == MemoryType.HEAP) {
                str.append("堆内内存：").append(SystemUtil.padRightWithSpaces(memoryPool.getName(), 20));
                str.append("初始大小：").append(memoryPool.getUsage().getInit() / (1024 * 1024)).append("MB");
                str.append("\t已使用大小：").append(memoryPool.getUsage().getUsed() / (1024 * 1024)).append("MB");
                str.append("\t最大大小：").append(memoryPool.getUsage().getMax() / (1024 * 1024)).append("MB");
                str.append("\t已提交大小：").append(memoryPool.getUsage().getCommitted() / (1024 * 1024)).append("MB");
                str.append("\t使用率：").append(memoryPool.getUsage().getUsed() * 100d / memoryPool.getUsage().getMax()).append("%");
                str.append("\r\n");
                str.append("=================================================================================================================================\r\n");
            }
        }
        System.out.println(str);
    }

    @Test
    public void test21() {
        StringBuilder str = new StringBuilder();
        //获取堆内存信息
        long maxMemory = Runtime.getRuntime().maxMemory();
        long totalMemory = Runtime.getRuntime().totalMemory();
        str.append("-Xmx(最大可用内存): ").append(maxMemory / 1024 / 1024).append("MB");
        str.append("\r\n");
        str.append("-Xms(已获得内存): ").append(totalMemory / 1024 / 1024).append("MB");
        str.append("\r\n");
        str.append("================================================================================================================\r\n");
        //获取栈内存信息
        int processor = Runtime.getRuntime().availableProcessors();
        String stackSize = System.getProperty("sun.arch.data.model");
        int Kilo = 1024;
        int stackInKilo;
        if ("32".equals(stackSize)) {
            stackInKilo = Kilo * 4;
        } else {
            stackInKilo = Kilo * 8;
        }
        long totalStack = (long)stackInKilo * processor;
        str.append("当前使用的CPU内核数:").append(processor);
        str.append("\r\n");
        str.append("每个线程栈内存大小:").append(stackInKilo / Kilo).append("KB");
        str.append("\r\n");
        str.append("最大可用栈内存:").append(totalStack / Kilo).append("KB");
        str.append("\r\n");

        System.out.println(str.toString());
    }

    @Test
    public void test22() {
        Instrumentation instrumentation = getInstrumentation();
        if (instrumentation == null) {
            System.out.println("无法获取Instrumentation实例");
        }

        // 获取对象大小
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

    @Test
    public void test23() {
//        LocalDateTime date1 = LocalDate.parse("2024-01-08", DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay();
//        LocalDateTime date2 = LocalDate.parse("2024-01-09", DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay().withHour(23).withMinute(59).withSecond(59);
//        System.out.println(date1);
//        System.out.println(date2);
//        Duration duration = Duration.between(date1,date2);
//        System.out.println(duration.toDays());
//        LocalDateTime dd = date1.plusDays(90).withHour(23).withMinute(59).withSecond(59);
//        System.out.println(dd);
        LocalDateTime date = LocalDateTime.now();
        LocalDateTime dateUTC = LocalDateTime.now(ZoneId.of("UTC"));
        System.out.println(date.withHour(23).withMinute(59).withSecond(59));
        System.out.println(dateUTC.withHour(23).withMinute(59).withSecond(59));

    }

    @Test
    public void test24() {
        System.out.println(Math.round(5.0005D * 100.0) / 100.0);
    }

    @Test
    public void test25() {
        List<User> userList = new ArrayList<>();
        User user1 = User.builder().id(1L).name("zhang").age(18).build();
        User user2 = User.builder().id(2L).name("zhang").age(19).build();
        User user3 = User.builder().id(3L).name("wang").age(18).build();
        User user4 = User.builder().id(4L).name("wang").age(19).build();
        User user5 = User.builder().id(5L).name("wang").age(19).build();
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        userList.add(user4);
        userList.add(user5);

//        userList = userList.stream().filter(item->item.getAge() == 18).collect(Collectors.toList());
//        Map<String, List<User>> map = userList.stream().collect(Collectors.groupingBy(item -> item.getName() + item.getAge()));
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getId().equals(3L)) {
                userList.remove(i);
            }
        }
        System.out.println(JSON.toJSONString(userList));
//        System.out.println(JSON.toJSONString(Lists.partition(userList,2)));
//        System.out.println(JSON.toJSONString(Lists.newArrayList("1","2","3")));
//        System.out.println(JSON.toJSONString(map));
    }

    @Test
    public void test26() {
        String email = "asd.qq@qq.com";
        String[] emails = email.split(",");
        for (String e : emails) {
            System.out.println(e);
        }
    }

    @Test
    public void test27() {
        System.out.println(UuidUtil.getTimeBasedUuid().toString().replaceAll("-", ""));
        System.out.println(UuidUtil.getTimeBasedUuid().toString().replaceAll("-", ""));
        System.out.println(UuidUtil.getTimeBasedUuid().toString().replaceAll("-", ""));
        System.out.println(UuidUtil.getTimeBasedUuid().toString().replaceAll("-", ""));
        System.out.println(UuidUtil.getTimeBasedUuid().toString().replaceAll("-", ""));
        System.out.println(UuidUtil.getTimeBasedUuid().toString().replaceAll("-", ""));

        System.out.println("----------------------------");
        System.out.println(UUID.randomUUID().toString().replaceAll("-", ""));
        System.out.println(UUID.randomUUID().toString().replaceAll("-", ""));
        System.out.println(UUID.randomUUID().toString().replaceAll("-", ""));
        System.out.println(UUID.randomUUID().toString().replaceAll("-", ""));
        System.out.println(UUID.randomUUID().toString().replaceAll("-", ""));
        System.out.println(UUID.randomUUID().toString().replaceAll("-", ""));
        System.out.println("----------------------------");
        System.out.println(Ulid.fast());
        System.out.println(Ulid.fast());
        System.out.println(Ulid.fast());
        System.out.println(Ulid.fast());
        System.out.println(Ulid.fast());
    }

    @Test
    public void test28() {
//        Map<String, String> map = Maps.newHashMap();
//        map.put("1","2");
//        map.put("2", null);
//        map.put("3","2");
//        map.values().removeIf(Objects::isNull);
//
//        System.out.println(map);
        List<String> list = Arrays.asList("1","2");
        List<String> list1 = Lists.newArrayList("1","2","3");
        List<String> list2 = Arrays.asList("1","3");
//        list2.remove("1");
        System.out.println(list2);


        Map<String, String> map = MapUtil.of("1","a");
        map.put("a", null);
        System.out.println(map);
        map.values().removeIf(Objects::isNull);
        map.put("aa", "asd");
        System.out.println(map);


        Map<String, String> map2 = MapUtil.builder(new HashMap<String, String>()).put("aa","bb").put("df", "rt").build();
        map2.put("aar","bb");
        System.out.println(map2);

    }
    @Test
    public void test29() {
        //冒泡排序
        int[] arr = {1, 3, 5, 7, 9, 2, 4, 6, 8};
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
        System.out.println(Arrays.toString(arr));
    }
    @Test
    public void test30() {
        //邮箱格式校验
        String emails = "qwe@vip.qq.com,liwen.tang@way.iotuty";
        for (String email : emails.split(",")) {

            if (email.matches("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$")) {
                System.out.println("邮箱格式正确");
            } else {
                System.out.println("邮箱格式错误");
            }
        }
    }

    @Test
    public void test31() {
        //当前时间加1天
        // 获取当前时间
        // 创建一个Calendar对象
        Calendar calendar = Calendar.getInstance();
        // 将当前时间添加一天
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        // 获取新的时间
        Date date = calendar.getTime();
        // 输出新的时间
        System.out.println(date);
        //获取当前时间
//        Date date = new Date();
//        System.out.println(date);

        LocalDateTime localDateTime = LocalDateTime.now().plusDays(1);
        System.out.println(localDateTime);
    }

    @Test
    public void test32() {
        String str = null;
        System.out.println(Optional.ofNullable(str).orElse("aaa"));

    }

    @Test
    public void test33() {
        long start = System.currentTimeMillis();
        int timeout = 0;
        try {
            //log.info("---------------开始-----------------{}", j);
            DspHystrixCommand command = new DspHystrixCommand("group", "command" + timeout, 100);
//                    log.info("{}",command);
//                    log.info("{}",command.getThreadPoolKey());
            String res = command.execute();
            long end = System.currentTimeMillis();
            log.info("-completed-限制timeout:" + timeout + ",执行time:" + (end - start) + "业务耗时：" + res);

        } catch (HystrixRuntimeException e) {
            //log.error(e.getMessage());
            long end = System.currentTimeMillis();
            log.info("-Hystrix-限制timeout:" + timeout + ",执行time:" + (end - start));
        }

    }

    @Test
    public void test34() {
        CountryEnum countryEnum = CountryEnum.valueOf("QIN");
        System.out.println(countryEnum.getName());
    }

    @Test
    public void test35() throws InterruptedException {
        Thread.sleep(500L);
        int iterations = 100000;
        String base = "Hello ";
        long start = System.nanoTime();
//        for (int i = 0; i < iterations; i++) {
//            base += "World";
//        }
        long end = System.nanoTime();
//        System.out.println("String += took " + (end - start) + " nanoseconds");

        start = System.nanoTime();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < iterations; i++) {
            sb.append("World");
        }
        end = System.nanoTime();
        System.out.println("StringBuffer took " + (end - start) + " nanoseconds");

    }

    @Test
    public void test36() {
        log.info("greater than : {}", 1000 - 30 > 1);
    }

    @Test
    public void test37() throws InterruptedException {
        String token = "0123456789syabcd";
        Thread.sleep(1000L);
        long start = System.currentTimeMillis();
        System.out.println(encrypt(token,"4055"));
        /*for (int i = 0; i < 1000; i++) {
            AES.decryptTencentPrice("yRqaQ1pupJ29MxNobwt23w",token);
        }
        long end = System.currentTimeMillis();
        System.out.println("took " + (end - start) + " ms");*/

    }

    @Test
    public void test38() {
        String str = "asdf123";
        StringUtils.replace(str, "df", "aaaa");
        System.out.println(str);
        System.out.println(new StringBuilder().append("q").append("a").append("z"));
    }

    @Test
    public void test39() {
        System.out.println(Runtime.getRuntime().availableProcessors());
    }
    @Test
    public void test40() {
        try {
            extracted();
        } catch (BizException e) {
            log.error("e:{}",e.getErrorCode().getErrorCode());
            log.error("e:{}",e.getMessage());
        }
    }

    @Test
    public void test41() {
        System.out.println(StringUtils.right("vbnm", 30));
    }

    private static void extracted() {
        int a = 0;
        int b = 0;
        int c = 0;
        try {
            c = a/b;
        } catch (Exception e) {
            throw new BizException(BizCode.DEFAULT, "error:" + e.getMessage());
        }
    }

    @Test
    public void test42() {
        int i = 0;
        System.out.println(i++);
        System.out.println(i++);
        System.out.println(i++);
        System.out.println(i);

        System.out.println("aadfjkdaa".replaceFirst("aa", "dd"));
    }

    @Test
    public void test43() {
        List<User> list = new ArrayList<>();
        User user1 = User.builder().id(1L).name("zhangsan").priority(50).priorityPercent(0.3F).build();
        User user2 = User.builder().id(2L).name("lisi").priority(50).priorityPercent(0.5F).build();

        list.add(user1);
        list.add(user2);

        User willSelectUser = list.stream().max(Comparator.comparingInt(User::getPriority)).get();

        double r = Math.random();
        System.out.println(r);

        User selectUser = r > willSelectUser.getPriority() ? list.get(new Random().nextInt(list.size())) : willSelectUser;
        System.out.println(selectUser);
        System.out.println();
    }
    @Test
    public void test44() {
        List<User> list = new ArrayList<>();
        User user0 = User.builder().id(1L).priority(30).priorityPercent(0.3F).subUsers(Arrays.asList(SubUser.builder().id(50L).age(17).build())).build();
        User user1 = User.builder().id(2L).priority(30).priorityPercent(0.3F).subUsers(Arrays.asList(SubUser.builder().id(11L).age(18).build())).build();
        User user2 = User.builder().id(3L).priority(80).priorityPercent(0.5F).subUsers(Arrays.asList(SubUser.builder().id(45L).age(40).build())).build();
        User user3 = User.builder().id(4L).priority(50).priorityPercent(0.5F).subUsers(Arrays.asList(SubUser.builder().id(44L).age(40).build())).build();

        list.add(user0);
        list.add(user1);
        list.add(user2);
        list.add(user3);

        //按首个SubUser的年龄从大到小排序
        //list.sort(Comparator.comparingInt(o -> o.getSubUsers().get(0).getAge()).reversed());
        list.sort(Comparator.comparingInt(o -> o.getSubUsers().get(0).getAge()));
        //list倒序
        Collections.reverse(list);
        System.out.println(JSON.toJSONString(list));
        //年龄最大的 再按id排序 要求非年龄最大的项顺序不变
        Integer maxAge =  list.stream().mapToInt(o -> o.getSubUsers().get(0).getAge()).max().getAsInt();
        //取出非年龄最大的
        List<User> minAgeUsers = list.stream().filter(o -> !o.getSubUsers().get(0).getAge().equals(maxAge)).collect(Collectors.toList());
        //取出年龄最大的
        List<User> maxAgeUsers = list.stream().filter(o -> o.getSubUsers().get(0).getAge().equals(maxAge)).collect(Collectors.toList());
        //按id排序
        maxAgeUsers.sort(Comparator.comparingLong(o -> o.getSubUsers().get(0).getId()));
        Collections.reverse(maxAgeUsers);

        //将maxAgeUsers拼上minAgeUsers
        maxAgeUsers.addAll(minAgeUsers);
        list = maxAgeUsers;
        System.out.println(JSON.toJSONString(list));
    }

    @Test
    public void test45() {
        // 创建一个示例列表
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(4); // 这个元素的值大于3
        list.add(3);
        list.add(5);
        System.out.println(list);
        // 标记是否找到符合条件的元素
        boolean found = false;

        // 遍历列表
        int i = 0;
        for (; i < list.size(); i++) {
            // 检查元素是否大于3
            if (list.get(i) >= 1) {
                // 如果找到符合条件的元素
                found = true;
                // 将元素从当前位置移除
                int element = list.remove(i);
                // 将元素添加到列表的首位
                list.add(0, element);
                // 跳出循环，因为我们只想要移动第一个符合条件的元素
                break;
            }
        }
        System.out.println(i);
        System.out.println(list);
        if (found) {
            for (int j = 1; j <= i; j++) {
                System.out.println(list.get(j));
            }
        }
    }

    @Test
    public void test46() {
        // 假设您已经有了以下数据结构
        List<Long> ids = Lists.newArrayList(11L, 21L); // 存储所需User id的列表
        User user0 = User.builder().id(1L).priority(30).priorityPercent(0.3F).subUsers(Arrays.asList(SubUser.builder().id(50L).age(17).build())).build();
        User user1 = User.builder().id(2L).priority(30).priorityPercent(0.3F).subUsers(Arrays.asList(SubUser.builder().id(11L).age(18).build())).build();
        User user2 = User.builder().id(3L).priority(80).priorityPercent(0.5F).subUsers(Arrays.asList(SubUser.builder().id(45L).age(40).build())).build();
        User user3 = User.builder().id(4L).priority(50).priorityPercent(0.5F).subUsers(Arrays.asList(SubUser.builder().id(44L).age(40).build())).build();
        Map<Long, User> userMap = new HashMap<>() ; // 存储所有User的Map
        userMap.put(1L,user0);
        userMap.put(2L,user1);
        userMap.put(3L,user2);
        userMap.put(4L,user3);

// 使用Java 8 Stream API获取指定id的User列表
        List<User> usersById = ids.stream()
                .map(userMap::get) // 将id映射为对应的User对象
                .filter(Objects::nonNull) // 过滤掉可能不存在于userMap中的id，避免出现null值
                .collect(Collectors.toList());
        System.out.println(usersById);
    }

    @Test
    public void test47() {
        int a = 1;
        int b = 2;
        if (!need(a) || (need(a) && choose(b))) {
            System.out.println(44654);
        }
    }

    private boolean choose(int b) {
        System.out.println("choose" + b);
        return false;
    }

    private boolean need(int a) {
        System.out.println("need" + a);
        return false;
    }

    @Test
    public void test48() {
        User user0 = User.builder().id(1L).name("zhang1").build();
        User user1 = User.builder().id(2L).name("zhang2").build();
        User user2 = User.builder().id(3L).name("zhang3").build();
        User user3 = User.builder().id(4L).name("zhang4").build();
        Map<Long, User> userMap = new HashMap<>() ; // 存储所有User的Map
        List<User> userList = new ArrayList<>() ; // 存储所有User的List
        userMap.put(user0.getId(), user0);
        userMap.put(user1.getId(), user1);
        userMap.put(user2.getId(), user2);
        userMap.put(user3.getId(), user3);
        //用stream 将userMap中userName等于zhang的key放到放到一个字符串中，并用逗号隔开
        List<Long> userIdList = userMap.entrySet().stream()
                .filter(entry -> entry.getValue().getName().equals("zhang1"))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        StringBuilder stringBuilder = new StringBuilder();
        userMap.forEach(
                (k, v) -> {
                    if (v.getName().equals("zhang1")) {
                        stringBuilder.append(v.getId()).append(",");
                    }
                }
        );
        userList = new ArrayList<>(userMap.values());
        user0.setName("wang1");
        System.out.println(userMap);
        System.out.println(userList);

        User userfind = userList.stream().filter(i -> i.getId().equals(22L)).findFirst().orElse(User.builder().name("").build());
        System.out.println(userfind);
        userfind = User.builder().id(88L).name("zhang88").build();
        //将userfind添加到userList中
        userList.add(userfind);
        //将userfind替换userList中的
        userMap.put(userfind.getId(), userfind);

        userList.removeIf(i -> i.getId().equals(1L));
        userList.removeIf(i -> i.getId().equals(2L));
        userList.removeIf(i -> i.getId().equals(88L));
        System.out.println(userList);

    }

    @Test
    public void test49() {

        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        //list.add("3");
        //list.add("4");

        List<String> listAll = new ArrayList<>();
        listAll.add("1");
        listAll.add("2");
        listAll.add("3");
        listAll.add("4");

        //list倒序移除某个

        //listAll里判断list元素 没有的新增 有的修改，list没有的，listAll有的删除
        listAll.removeIf(i -> !list.contains(i));
        System.out.println(listAll);


    }

    @Test
    public void test50() {
        System.out.println(Math.random() >= (10 / 100.0));
        System.out.println(Math.random() >= (10 / 100.0));
        System.out.println(Math.random() >= (10 / 100.0));
        System.out.println(Math.random() >= (10 / 100.0));
        System.out.println(Math.random() >= (10 / 100.0));
        System.out.println(Math.random() >= (10 / 100.0));
        System.out.println(Math.random() >= (10 / 100.0));
        System.out.println(Math.random() >= (10 / 100.0));
        System.out.println(Math.random() >= (10 / 100.0));
        System.out.println(Math.random() >= (10 / 100.0));
        System.out.println(Math.random() >= (10 / 100.0));

        System.out.println(DateUtil.isIn(new Date(), DateUtil.parseDate("2024-03-01 00:00:00"), DateUtil.parseDate("2024-05-02 00:00:00")));

        System.out.println(new Date(0));
    }

    @Test
    public void test51() {
        // 定义两个用于计算的double类型变量
        double value1 = 0.4;
        double value2 = 0.7;

        // 进行乘法运算
        double result = value1 * value2;

        // 打印结果
        //System.out.println("Result of " + value1 + " * " + value2 + " is: " + result);

        System.out.println(0.4 * 0.7);

        System.out.println(100.00 / 11);
    }

    @Test
    public void test52() {
        User user = User.builder().id(1L).build();
        exDate(user.getDate());
        System.out.println(user);
        exDate(user);
        System.out.println(user);

        System.out.println("qwerty".replace("q", "999"));
    }

    private void exDate(Date date) {
        if (date == null) {
            date = new Date();
        }
    }

    private void exDate(User user) {
        if (user.getDate() ==  null) {
            user.setDate(new Date());
        }
    }

    private static final Map<String, List<String>> msgNodesMap = new ConcurrentHashMap<>();
    public static List<String> getNodesMap(String key){
        return msgNodesMap.getOrDefault(key, Lists.newArrayList());
    }
    public static void addNode(String key, String value) {
        msgNodesMap.computeIfAbsent(key, msgId -> new ArrayList<>()).add(value);
    }
    public static void putNodesMap(String key, List<String> value) {
        msgNodesMap.put(key, value);
    }
    public static void removeNode(String key, String value){
        getNodesMap(key).remove(value);
//        msgNodesMap.computeIfPresent(key, (k, v) -> {
//            v.remove(value);
//            return v.isEmpty() ? null : v; // 如果列表为空，可以考虑删除整个entry
//        });
    }
    public static void removeEntry(String key){
        msgNodesMap.remove(key);
    }

   @Test
   public void test53() {
       // 初始化msgNodesMap
       List<String> list = new ArrayList<>();
       for (int i = 0; i < 100; i++) {
           list.add(String.valueOf(i));
       }
       msgNodesMap.put("testKey", list);
       System.out.println(JSON.toJSONString(msgNodesMap));
       // 创建并启动多个线程，尝试移除不同的元素
       List<Thread> threads = new ArrayList<>();
       for (int i = 0; i < list.size() ; i++) {
           RemoveNodeThread thread = new RemoveNodeThread("testKey", String.valueOf(i));
           threads.add(thread);
           thread.start();
       }

       // 等待所有线程完成
       for (Thread thread : threads) {
           try {
               thread.join();
           } catch (InterruptedException e) {
               Thread.currentThread().interrupt();
               return;
           }
       }

       // 打印最终结果，检查是否所有元素都被成功移除
       System.out.println(JSON.toJSONString(msgNodesMap));
   }

    class RemoveNodeThread extends Thread {
        private final String key;
        private final String value;

        public RemoveNodeThread(String key, String value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public void run() {
            // 模拟一些工作延迟，以增加并发冲突的可能性
            try {
                Thread.sleep( 100L);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
            // 调用removeNode方法
            removeNode(key, value);
        }
    }

    @Test
    public void test54() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            list.add(String.valueOf(i));
        }
        //将list中所有1开头的替换为a
        list.replaceAll(s -> s.replaceFirst("1", "a"));
        System.out.println(list);
        System.out.println("https://" + "http://dfghjkl".substring(7));
    }
    @Test
    public void test55() {
        // 创建两个时间对象
        String time1 = "2022-08-01 12:00:00";
        String time2 = "2022-08-01 12:01:00";

        // 计算两个时间之间的间隔
        long interval = DateUtil.between(DateUtil.parse(time1), DateUtil.parse(time2), DateUnit.HOUR);

        System.out.println(interval);
        // 判断间隔是否大于1小时
        if (interval > 1) {
            System.out.println("两个时间间隔大于1小时");
        } else {
            System.out.println("两个时间间隔小于等于1小时");
        }
    }

    @Test
    public void test56() {
        /**
         *         0       1      2      3      4      5     6     7     8     9     10    11    12    13    14   15     16    17   18     19    20     21     22     23
         * 周一   false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false, false, false, false
         * 周二   false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false, false, false, false
         * 周三   false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false, false, false, false
         * 周四   false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false, false, false, false
         * 周五   false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false, false, false, false
         * 周六   false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false, false, false, false
         * 周日   false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false, false, false, false
         */
        //创建一个二维List,其中横坐标为天，纵坐标为周 存储以上数据
        List<List<Boolean>> list = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            list.add(new ArrayList<>());
        }
        list.get(0).addAll(Arrays.asList(false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false, false, false, false));
        list.get(1).addAll(Arrays.asList(false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false, false, false, false));
        list.get(2).addAll(Arrays.asList(false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false, false, false, false));
        list.get(3).addAll(Arrays.asList(false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false, false, false, false));
        list.get(4).addAll(Arrays.asList(false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false, false, false, false));
        list.get(5).addAll(Arrays.asList(false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false, false, false, false));
        list.get(6).addAll(Arrays.asList(false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false, false, false, false));


        Date currentTime = DateUtil.parse("2024-03-30 17:31:51");
        currentTime = DateUtil.parse("2024-03-25 17:31:51");
        Integer hour =  DateUtil.hour(currentTime, true);
        Week week = DateUtil.dayOfWeekEnum(currentTime);
        System.out.println(hour);
        System.out.println(week.getValue());
        //Date startTime = DateUtil.parse("2024-03-29 17:31:51");
        //判断currentTime的小时是否在list中
//        for (int i = 0; i < list.size(); i++) {
//            if (list.get(i).contains(currentTime.getHours())) {
//                System.out.println("当前时间的小时在范围内：" + currentTime.getHours());
//            }
//        }
    }

    @Test
    public void test57() {
        List<DayParting> dayPartings = new ArrayList<>();
        DayParting dayParting1 = new DayParting().setMonday(true).setFromHours(8).setToHours(20);
        DayParting dayParting2 = new DayParting().setTuesday(true).setFromHours(8).setToHours(20);
        DayParting dayParting3 = new DayParting().setWednesday(true).setFromHours(8).setToHours(20);
        DayParting dayParting4 = new DayParting().setThursday(true).setFromHours(8).setToHours(20);
        DayParting dayParting5 = new DayParting().setFriday(true).setFromHours(8).setToHours(12);
        DayParting dayParting55 = new DayParting().setFriday(true).setFromHours(14).setToHours(23);
        dayPartings.add(dayParting1);
        dayPartings.add(dayParting2);
        dayPartings.add(dayParting3);
        dayPartings.add(dayParting4);
        dayPartings.add(dayParting5);
        dayPartings.add(dayParting55);
        //date需要转成CST时间
        System.out.println(isInDayParting(dayPartings, DateUtil.parse("2024-03-29 03:31:51")));
    }

    /**
     * 判断当前时间是否命中所选的时段
     * @param dayPartingList 所选的时段
     * @param currentTime 当前时间
     * @return
     */
    private boolean isInDayParting(List<DayParting> dayPartingList, Date currentTime) {
        if (CollectionUtil.isEmpty(dayPartingList)) {
            //不选等于全选
            return true;
        }

        Week currentWeekDay = DateUtil.dayOfWeekEnum(currentTime);
        int currentHour = DateUtil.hour(currentTime, true);
        for (DayParting dayParting : dayPartingList) {
            if (isMatch(dayParting, currentHour, currentWeekDay)) {
                return true;
            }
        }
        return false;
    }

    private boolean isMatch(DayParting dayParting, int currentHour, Week currentWeekDay) {
        switch (currentWeekDay) {
            case MONDAY:
                return dayParting.isMonday() && isInTimeHours(currentHour, dayParting.getFromHours(), dayParting.getToHours());
            case TUESDAY:
                return dayParting.isTuesday() && isInTimeHours(currentHour, dayParting.getFromHours(), dayParting.getToHours());
            case WEDNESDAY:
                return dayParting.isWednesday() && isInTimeHours(currentHour, dayParting.getFromHours(), dayParting.getToHours());
            case THURSDAY:
                return dayParting.isThursday() && isInTimeHours(currentHour, dayParting.getFromHours(), dayParting.getToHours());
            case FRIDAY:
                return dayParting.isFriday() && isInTimeHours(currentHour, dayParting.getFromHours(), dayParting.getToHours());
            case SATURDAY:
                return dayParting.isSaturday() && isInTimeHours(currentHour, dayParting.getFromHours(), dayParting.getToHours());
            case SUNDAY:
                return dayParting.isSunday() && isInTimeHours(currentHour, dayParting.getFromHours(), dayParting.getToHours());
            default:
                // 逻辑上不应该到达这里，但作为保护代码仍保留
                return false;
        }
    }

    private static boolean isInTimeHours(int currentHour, int fromHours, int toHours) {
        return fromHours <= currentHour && currentHour <= toHours;
    }

    @Test
    public void test58() {
        String str = Arrays.toString(new byte[]{97, 115, 100, 102});
        System.out.println(str);
        System.out.println("http://".contains("http:\\"));
    }

    @Test
    public void test59() {
//        LocalDateTime date = LocalDateTime.now();
//        System.out.println(date.getMonthValue());
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth(), date.getHour(), date.getMinute(), date.getSecond());
//        System.out.println(calendar.getTime());
        System.out.println(Calendar.getInstance(TimeZone.getTimeZone("Asia/shanghai")).getTime());
        System.out.println(Calendar.getInstance(TimeZone.getTimeZone("+8:00")).getTime());
    }

    @Test
    public void test60() {
        List<String> list = new ArrayList<>();
        list.add("08:00");
        list.add("10:59");
        list.add("13:00");
        list.add("15:59");
        list.add("03:00");
        list.add("09:59");
        //list中单数的元素为开始时间，偶数元素为结束时间，要求按开始时间进行排序，排序后为：03:00 05:59 08:00 10:59 13:00 15:59
        // 提取所有的开始时间
        List<String> startTimes = new ArrayList<>();
        for (int i = 0; i < list.size(); i += 2) {
            startTimes.add(list.get(i));
        }

        // 对开始时间进行排序
        startTimes.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });

        /*// 根据排序后的开始时间重建时间区间列表
        List<String> sortedIntervals = new ArrayList<>();
        for (String startTime : startTimes) {
            int index = list.indexOf(startTime);
            sortedIntervals.add(startTime); // 添加开始时间
            sortedIntervals.add(list.get(index + 1)); // 添加对应的结束时间
        }*/
        // 根据排序后的开始时间重建时间区间列表，并格式化输出
        StringBuilder formattedIntervals = new StringBuilder();
        for (String startTime : startTimes) {
            int index = list.indexOf(startTime);
            String endTime = list.get(index + 1);
            formattedIntervals.append(startTime).append("~").append(endTime).append(",");
        }

        // 移除最后一个逗号，并打印结果
        if (formattedIntervals.length() > 0) {
            formattedIntervals.setLength(formattedIntervals.length() - 1); // 移除最后一个逗号
        }
        System.out.println(formattedIntervals.toString());
    }

    @Test
    public void test61() {
        User user0 = User.builder().id(1L).name("zhang1").build();
        User user1 = User.builder().id(2L).name("zhang2").build();
        User user2 = User.builder().id(3L).name("zhang3").build();
        User user3 = User.builder().id(4L).name("zhang4").build();
        User user4 = User.builder().id(4L).name("zhang5").build();

        List<Map<String, String>> mapList = new ArrayList<>();
        mapList.add(MapUtil.builder("id", "1").put("name", "zhang1").build());
        mapList.add(MapUtil.builder("id", "2").put("name", "zhang2").build());
        mapList.add(MapUtil.builder("id", "3").put("name", "zhang3").build());

        List<Long> list = new ArrayList<>();
        list.add(1L);
        list.add(2L);
        list.add(4L);
//        Map<Long, List<Map<String, String>>> maps = mapList.stream().collect(Collectors.groupingBy(i -> Long.parseLong(i.get("id"))));
        StringBuilder names = new StringBuilder();
        List<String> nameList = new ArrayList<>();
        //nameList为mapList中所有的name
        nameList = mapList.stream().map(i -> i.get("name")).collect(Collectors.toList());

        nameList = list.stream().map(i -> mapList.stream().filter(j -> j.get("id").equals(i.toString())).findFirst().map(j -> j.get("name")).orElse("")).collect(Collectors.toList());
        System.out.println(nameList);
        //names为list中所有id从mapList找到的id对应的name拼接
        for (Long id : list) {
            names.append(mapList.stream().filter(i -> i.get("id").equals(id.toString())).findFirst().map(i -> i.get("name")).orElse(""));
        }
        System.out.println(names);

//        String name = mapList.stream().filter(i -> i.get("id").equals("11")).findFirst().map(i -> i.get("name")).orElse("");
//        System.out.println(name);


        /*List<User> userList = new ArrayList<>(); // 存储所有User的List
        userList.add(user0);
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        userList.add(user4);

        String name = userList.stream().filter(i -> i.getName().equals("zhang1")).findFirst().map(User::getName).orElse(null);
//        map = userList.stream().collect(Collectors.groupingBy(User::getId, Collectors.mapping(User::getName, Collectors.toList())));

        System.out.println(name);*/
    }


    /*public static String encrypt(String aesKey, String data) {
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
    }*/

    @Test
    public void test62()  {
        String trackingUrl = "https://app.adjust.com/13af6dvq/abc/dfg?adgroup=cpa_mobile&creative=Axpo_iplay_{sub_id}&adgroup=cpa_mobile&creative=Axpo_iplay_{sub_id}&idfa=__IDFA__&gps_adid=__GAID__&install_callback=https%3A%2F%2Fsgp.digcto.com%2Fwinloss%2FforeignPostback%3Faction%3Dinstall%26deviceId3%3D__IDFA__%26deviceId5%3D__GAID__%26adid%3D__UNIQUE_ID__%26partner%3D__PARTNER__%26ua%3D__UA__%26bundleId%3D__BUNDLE_ID__%26event%3D9%26mark%3Dadjust";
//        String trackingUrl = "https://app.adjust.com/13af6dvq?adgroup=cpa_mobile&creative=Axpo_iplay_{sub_id}&idfa=__IDFA__&gps_adid=__GAID__&install_callback=https%3A%2F%2Fsgp.digcto.com%2Fwinloss%2FforeignPostback%3Faction%3Dinstall%26deviceId3%3D__IDFA__%26deviceId5%3D__GAID__%26adid%3D__UNIQUE_ID__%26partner%3D__PARTNER__%26ua%3D__UA__%26bundleId%3D__BUNDLE_ID__%26event%3D9%26mark%3Dadjust";
        URL url = null;
        try {

            url = new URL(trackingUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return;
        }
        String domain = url.getProtocol() + "://" + url.getHost() + url.getPath();

        System.out.println(domain);
    }

    @Test
    public void test63() {
        List<String> list = new ArrayList<>();
        list.add("08:00");
        list.add("10:59");
        list.add("13:00");
        //list中随机取一个值
        System.out.println(list.get(new Random().nextInt(list.size())));
        System.out.println(list.get(new Random().nextInt(list.size())));
        System.out.println(list.get(new Random().nextInt(list.size())));
        System.out.println(list.get(new Random().nextInt(list.size())));
        System.out.println(list.get(new Random().nextInt(list.size())));
        System.out.println(list.get(new Random().nextInt(list.size())));
    }

    @Test
    public void test64() throws InterruptedException {
        System.out.println(LocalDateTime.now().getSecond());
        Thread.sleep(1000L);
        System.out.println(LocalDateTime.now().getSecond());
        Thread.sleep(1000L);
        System.out.println(LocalDateTime.now().getSecond());
        Thread.sleep(1000L);
        System.out.println(LocalDateTime.now().getSecond());
        Thread.sleep(1000L);
        System.out.println(LocalDateTime.now().getSecond());
        Thread.sleep(1000L);
        System.out.println(LocalDateTime.now().getSecond());
        Thread.sleep(1000L);
        System.out.println(LocalDateTime.now().getSecond());
        Thread.sleep(1000L);
        System.out.println(LocalDateTime.now().getSecond());
        Thread.sleep(1000L);
        System.out.println(LocalDateTime.now().getSecond());
    }

    @Test
    public void test65() {
            User user0 = User.builder().id(1L).name("zhang1").build();
            User user1 = User.builder().id(2L).name("zhang2").build();
            User user2 = User.builder().id(null).name("zhang3").build();
            User user3 = User.builder().id(4L).name("zhang4").build();
            User user4 = User.builder().id(4L).name("zhang5").build();

            List<User> list = new ArrayList<>();
            list.add(user0);
            list.add(user1);
            list.add(user2);

            HashMap<String, Long> map = (HashMap<String, Long>) list.stream().collect(Collectors.toMap(User::getName, User::getId));
//            map.put("name", null);
//        map.put(null, null);
//        map.put(null, 12L);
            System.out.println(map);

    }
    @Test
    public void test66() {
        User user = User.builder().id(1L).name("zhang1").build();
        User user1 = User.builder().id(2L).name("zhang1").build();
        List<User> list = new ArrayList<>();
        list.add(user);
        list.add(user1);
        List<User> list1 = new ArrayList<>();
//        list1 = list.stream(i -> transUser(i)).collect(Collectors.toList());
        list1 = list.stream().map(this::transUser).collect(Collectors.toList());
        System.out.println(list1);
    }

    private User transUser(User user){
        user.setId(user.getId() + 1);
        return user;
    }

    @Test
    public void test67() {
        System.out.println(String.valueOf(0x400));
    }

    @Test
    public void test68() {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");

        Integer result = 3;
        Integer id1 = 4;
        Integer id2 = 5;
        // 用stream写法从list中找id1 id2 若存在 则result=id1或id2 若不存在则 result=3

        result = Integer.valueOf(list.stream().filter(i -> i.equals(id1.toString()) || i.equals(id2.toString())).findAny().orElse(String.valueOf(result)));

        System.out.println(result);
    }
    @Test
    public void test69() {
        //Md5 加密
        String md5 = DigestUtils.md5DigestAsHex("2b115b0abcf1d74bedac08fedead5180".getBytes());
        System.out.println(md5);
    }

    @Test
    public void test70() {
        StringBuilder sb = new StringBuilder();
        sb.append("qwerty<br>uiop<br>");
        String str = "qwerty<br>uiop<br>";
        str = StringUtils.removeEnd(str, "<br>");
//        String str = sb.toString().replaceAll("(?<!.\\*" + "<br>" + ").*" + "<br>", "");
        System.out.println(str);
    }

    @Test
    public void test71() {
        String[] array = Arrays.stream(CountryEnum.values()).map(CountryEnum::getName).toArray(String[]::new);
        System.out.println(JSON.toJSONString(array));
    }

    @Test
    public void test72() {
        System.out.println(ZoneId.systemDefault());
//        System.out.println(TimeZone.getDefault().toZoneId());

        ZonedDateTime zonedDateTime = ZonedDateTime.now();
//        System.out.println(zonedDateTime.getHour());

        ZoneId zoneId =  ZoneId.of("UTC");

        ZonedDateTime zonedDateTime1 = ZonedDateTime.now(zoneId);
        System.out.println(zonedDateTime1);
    }

    @Test
    public void test73() {
        LongAdder event = new LongAdder();
        System.out.println(event);
        AtomicLong atomicLong = new AtomicLong();
        atomicLong.addAndGet(1);
        System.out.println(atomicLong);
        atomicLong.addAndGet(-4);
        System.out.println(atomicLong);
    }

    @Test
    public void test74() {
        List<String> list = new ArrayList<>();

        list.add("1");
        list.add("2");
        list.add("3");
        //加到3后面
        list.add("4");
        list.add("5");
        int flag = 0;

        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).equals("4")) {
                flag = i;
                break;
            }
        }
        System.out.println(flag);
        list.add(flag, "aa");
        list.add(flag + 1, "bb");
        System.out.println(list);
        String a = "CodeValue";
        //首字母小写
        String b = a.substring(0, 1).toLowerCase() + a.substring(1);
    }

    @Test
    public void test75() {
        Integer a = 133;
        Integer b = 133;
        System.out.println(a == b);
    }

    @Test
    public void test76() {
        String[] data0 = StringUtils.splitByWholeSeparator("aa|aa||aa", "|");
        String[] data = StringUtils.splitPreserveAllTokens("aa|aa||aa", "|");
        String[] data1 = "aa|aa||aa".split("\\|");
        System.out.println(JSON.toJSONString(data0));
        System.out.println(JSON.toJSONString(data));
        System.out.println(JSON.toJSONString(data1));
    }
    @Test
    public void test77() {
        String content ="wertyuidfghjkldfgiudgizdsjzjfdgkjdgfuefudgjdjxdzkdfjdskfhjkdfghj";
        String content2 ="wertyuidfghjkldfgiudgizdsjzjfdgkjdgfuefudgjdjxdzkdfjdskfhjkdfghjkdfghjkrhj1234";
        //截取content前50位 不足50位取全部
        String content1 = content.substring(0, Math.min(content.length(), 50));
        System.out.println(content2.contains(content1));
    }

    @Test
    public void test78() {
        final ZonedDateTime updateTime = ZonedDateTime.now();
        // 转timestamp
        final long timestamp = updateTime.toEpochSecond();
        Timestamp timestamp1 = new Timestamp(timestamp * 1000);
        System.out.println(timestamp);
        System.out.println(timestamp1);

    }
    @Test
    public void test79() {
        List<Student> list = new ArrayList<>();
        list.add(new Student().setId(1).setName("wang1"));
        list.add(new Student().setId(2).setName("wang2"));
        list.add(new Student().setId(3).setName("wang3"));
        System.out.println(list);
        List<Student> list1 = new ArrayList<>(list);
        Iterator<Student> iterator = list1.iterator();
        while (iterator.hasNext()) {
            Student next = iterator.next();
            if(next.getId() == 1) {
                iterator.remove();
            }
        }
        list1.get(0).setName("zhang");
        System.out.println(list);
        System.out.println(list1);
    }
    @Test
    public void test80() {
        System.out.println("bsd12hjAsdfg".toLowerCase().contains("asd"));
    }
    @Test
    public void test81() throws UnsupportedEncodingException {
        String url = "https%3A%2F%2Fssp.dreamad.mobi%2Fapi%2Fup%2Fc%2Fdeeplink_open_ok%2Ft%2F1151%2Fr%2FS1151GWT20240619162916YVGH3533739%2Fo%2F1%2Ff%2F1015%2Fd%2F2b85d0a096e4c42a%2Fadp%2F%24%7BAUCTION_PRICE_DREAM%7D%2Fdc%2Fandroidid%2Fi%2F112.17.242.79%2Fa%2F3fc647211122e3457334f8c06d89365a%2Fp%2F%24%7BAUCTION_PRICE_DREAM_CH%7D%2Fbf%2F13.93%2Ffs%2F1";
        String url1 = URLDecoder.decode(url, "UTF-8");
        System.out.println(url1);
        System.out.println("https://ssp.dreamad.mobi/api/up/c/deeplink_open_ok/t/1151/r/S1151GWT20240619162916YVGH3533739/o/1/f/1015/d/2b85d0a096e4c42a/adp/${AUCTION_PRICE_DREAM}/dc/androidid/i/112.17.242.79/a/3fc647211122e3457334f8c06d89365a/p/${AUCTION_PRICE_DREAM_CH}/bf/13.93/fs/1");
    }

    @Test
    public void test82() {
        System.out.println(100 % 2 == 1);
        System.out.println(101 % 2 == 1);
        System.out.println(103 % 2 == 1);
        System.out.println(0 % 2 == 1);
    }

    @Test
    public void test83() {
        Deque<Long> values = new LinkedList<>();
        values.add(1L);
        values.add(2L);
        values.add(8L);
        System.out.println(values.peekLast());
    }
    @Test
    public void test84() {
//        TimeZone zone = TimeZone.getTimeZone(ZoneId.of("UTC"));
//        ZonedDateTime zonedDateTime = ZonedDateTime.now();
//        System.out.println(zonedDateTime.getZone());
        //2147483647
        //2146999999
//        System.out.println(Integer.MAX_VALUE);
        String str = "";
        List<Long> sids  = Arrays.stream(str.split(",")).map(Long::valueOf).collect(Collectors.toList());
//        List<Long> sids = Arrays.stream(str.split(",")).map(Long::valueOf).collect(Collectors.toList());
        System.out.println(sids);
    }

    @Test
    public void test85() {
//        Map<String, Student> map = MapUtil.of("aa", new Student().setId(1).setName("zhang1"));
//        Student s = map.values().stream().filter(i->i.getName().equals("zhang")).findFirst().orElse(new Student().setId(-1).setName("other"));
//        System.out.println(s);
        String str = "1234,3456";
        List<Integer> list = Arrays.stream(str.split(",")).map(Integer::valueOf).collect(Collectors.toList());
    }

    @Test
    public void test86() {
        Map<String, List<Student>> dspInstallAppsMarkMap = new ConcurrentHashMap<>(8, 0.9f, 1);
        List<Student> list = new ArrayList<>();
        list.add(new Student().setId(100).setName("a"));
        list.add(new Student().setId(-99).setName("a"));
        List<Student> wayInstallApps = new ArrayList<>();
        wayInstallApps.add(new Student().setId(-99).setName("其他"));
        wayInstallApps.add(new Student().setId(100).setName("qwer"));
        wayInstallApps.add(new Student().setId(101).setName("qwer1"));
//        dspInstallAppsMarkMap.put("SURGE", wayInstallApps.stream().filter(i -> list.stream().filter(j -> j.getId().equals(i.getId()))).collect(Collectors.toList()));
        dspInstallAppsMarkMap.put("SURGE", wayInstallApps.stream().filter(i -> list.stream().anyMatch(j -> j.getId().equals(i.getId()))).collect(Collectors.toList()));
        System.out.println(JSON.toJSONString(dspInstallAppsMarkMap));
    }

    @Test
    public void test87() {
        List<Student> list = new ArrayList<>();
        list.add(new Student().setId(100).setName("a"));
        list.add(new Student().setId(-99).setName("b"));
        list.add(new Student().setId(101).setName("c"));
        list.add(new Student().setId(103).setName("a"));
        //判断如果name有重复的
        boolean b = list.stream().collect(Collectors.groupingBy(Student::getName)).values().stream().anyMatch(i -> i.size() > 1);
        System.out.println(b);
        //取出name重复的
        List<Student> list1 = list.stream().collect(Collectors.groupingBy(Student::getName)).values().stream().filter(i -> i.size() > 1).flatMap(Collection::stream).collect(Collectors.toList());
        System.out.println(list1);
        Map<Integer, Student> map = list.stream().collect(Collectors.toMap(Student::getId, Function.identity(), (k1, k2) -> k1));


    }
    @Test
    public void test88() {
        List<Integer> list1 = new ArrayList<>();
        list1.add(1);
        list1.add(1);
        List<Integer> list2 = new ArrayList<>();
        list2.add(3);
//        list2.add(1);
        list2.add(2);
        //list1 list2取交集
        list1.retainAll(list2);
        System.out.println(list1);
        System.out.println(list1.contains(1));
    }

    @Test
    public void test89() {
//        System.out.println("上淘宝，超便宜，免拼单！".substring(0, 7) + "…");
        String str = "aaa_bbb_ccc_abc_sdf_fgh";
        //将str中的 aaa bbb ccc 替换为-999
        String s = str.replaceAll("aaa|bbb|ccc", "-999");
        System.out.println(s);
//        System.out.println("上淘宝，超便宜…".length());
    }

    @Test
    public void test90() throws JsonProcessingException {
        String str = "{" +
                "  \"request_id\":\"ade26ed0-cb0d-4251-b21d-08d6982db3fa\",\n" +
                "  \"id\":123456,\n" +
                "  \"name\":\"zhang\",\n" +
                "  \"remark\":[\"\"]\n" +
                "}";
        Student student = JSON.parseObject(str, Student.class);
        System.out.println(student);

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false);
        Student student2 = mapper.readValue(str, Student.class);
        System.out.println(student2);
    }

    @Test
    public void test91() {
        ZonedDateTime zonedDateTime1 = ZonedDateTime.now();
        long time = 1721620029705L;
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault());
        // 判断是否为同一天
        if (zonedDateTime1.toLocalDate().equals(zonedDateTime.toLocalDate())) {
            System.out.println("是同一天");
        } else {
            System.out.println("不是同一天");
        }
        System.out.println(zonedDateTime1.toLocalDate());
        System.out.println(zonedDateTime1);

    }

}
