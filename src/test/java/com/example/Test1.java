package com.example;


import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.example.domain.DspHystrixCommand;
import com.example.domain.User;
import com.example.enums.CountryEnum;
import com.example.exception.BizCode;
import com.example.exception.BizException;
import com.example.utils.AES;
import com.example.utils.HttpUtil;
import com.example.utils.SystemUtil;
import com.github.f4b6a3.ulid.Ulid;
import com.google.common.collect.*;
import com.netflix.hystrix.*;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.core.util.UuidUtil;
import org.apache.tomcat.util.codec.binary.Base64;
import com.example.utils.MDCUtil;
import org.junit.jupiter.api.Test;
import org.springframework.util.StopWatch;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.lang.instrument.Instrumentation;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryType;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.*;
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

        userList = userList.stream().filter(item->item.getAge() == 18).collect(Collectors.toList());
//        Map<String, List<User>> map = userList.stream().collect(Collectors.groupingBy(item -> item.getName() + item.getAge()));
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
        System.out.println(UuidUtil.getTimeBasedUuid());
        System.out.println(UuidUtil.getTimeBasedUuid());
        System.out.println(UuidUtil.getTimeBasedUuid());
        System.out.println(UuidUtil.getTimeBasedUuid());
        System.out.println(UuidUtil.getTimeBasedUuid());
        System.out.println(UuidUtil.getTimeBasedUuid());

        System.out.println(UUID.randomUUID());
        System.out.println(UUID.randomUUID());
        System.out.println(UUID.randomUUID());
        System.out.println(UUID.randomUUID());
        System.out.println(UUID.randomUUID());
        System.out.println(UUID.randomUUID());

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
        String token = "b0a6f6e991739303";
        Thread.sleep(1000L);
        long start = System.currentTimeMillis();
//        System.out.println(AES.encrypt("yRqaQ1pupJ29MxNobwt23w",token));
        for (int i = 0; i < 1000; i++) {
            AES.decryptTencentPrice("yRqaQ1pupJ29MxNobwt23w",token);
        }
        long end = System.currentTimeMillis();
        System.out.println("took " + (end - start) + " ms");

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
    }


}
