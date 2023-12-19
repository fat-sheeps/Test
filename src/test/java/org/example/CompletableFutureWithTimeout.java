package org.example;

import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public class CompletableFutureWithTimeout {

    public static void main(String[] args) throws Exception {
        int timeout = 1000;
        long start = System.currentTimeMillis();
        CompletableFuture<String> future = new CompletableFuture<>();

        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.schedule(() -> {
            if (!future.isDone()) {
                future.completeExceptionally(new RuntimeException("Timeout！"));
            }
        }, timeout, TimeUnit.MILLISECONDS);

        // 模拟异步操作
        new Thread(() -> {
            int t = RandomUtil.randomInt(800, 1200);
            try {
                log.info(" t:{}", t);
                Thread.sleep(t);
                future.complete("执行完成");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        // 等待异步操作的结果，最多等待1秒钟
        try {
            String result = future.get(timeout, TimeUnit.MILLISECONDS);
            log.info("Result: " + result);
        } catch (Exception e) {
            log.info("Operation timed out");
        } finally {
            executor.shutdown();
        }
        // 打印成功的响应
        long end = System.currentTimeMillis();
        log.info("end time: " + (end - start));
    }
}

