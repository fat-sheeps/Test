package org.example;


import lombok.extern.slf4j.Slf4j;
import org.example.domain.User;
import org.example.utils.HttpUtil;
import org.junit.jupiter.api.Test;

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
    public void test4() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<String> future = executor.submit(() -> {
            // 执行耗时操作，例如发送HTTP请求或数据库查询
            for (int i = 1; i < 10; i++) {
                log.info("i:{}s", i);
                Thread.sleep(1000L);
//                String res = HttpUtil.doGet("http://localhost:8888/server");
//                log.info(res);
            }
            //2s的时间走不到这 所以 !countDownLatch.await(2, TimeUnit.SECONDS) 为true
            countDownLatch.countDown();
            return "Task completed";
        });

        long start = System.currentTimeMillis();
        try {
            // 设置超时时间为3秒
            String result = future.get(3, TimeUnit.SECONDS);
            log.info(result);
        } catch (TimeoutException e) {
            log.info("Task took too long to complete.");
        } catch (InterruptedException | ExecutionException e) {
            log.error("An error occurred: " + e.getMessage());
        } finally {
            future.cancel(true); // 取消任务
            executor.shutdown(); // 关闭线程池

        }
        if (!countDownLatch.await(2, TimeUnit.SECONDS)) {
            System.out.println("计数器已经等待了2s countDown的值还没有成为0！");
        }
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
}
