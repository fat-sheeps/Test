package com.example;

import com.google.common.collect.Lists;
import com.lambdaworks.redis.api.async.RedisAsyncCommands;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
public class LargeFileProcessor {

    private static final int PRE_THREAD_SIZE = 10000; // 每个线程操作的数据量
    private static final int THREAD_COUNT = 10; // 每次创建的线程数
    private static final int BATCH_SIZE = PRE_THREAD_SIZE * THREAD_COUNT; // 每批次读取的数据量
    private static final String FILE_PATH = "C:\\Users\\wayio\\Desktop\\uuid.txt"; // 文件路径

    /**
     * 处理批量数据123W条数据
     * 分13批次处理
     * 每批处理10W条，每批10个线程
     * 每个线程处理1W
     */
    public static void main(String[] args) {
        log.info("开始执行。。。");
        long start = System.currentTimeMillis();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
            List<String> lineList = new ArrayList<>(BATCH_SIZE);
            RedisAsyncCommands<String, String> async = null;

            String line;
            int totalLines = 0;
            int batchIndex = 0;

            while ((line = br.readLine()) != null) {
                lineList.add(line);
                totalLines ++;
                if (lineList.size() == BATCH_SIZE) {
                    processBatch(batchIndex ++, executor, lineList, async);
                    lineList = new ArrayList<>(BATCH_SIZE);
                }
            }
            // 处理剩余的数据
            if (totalLines > 0 && !lineList.isEmpty()) {
                processBatch(batchIndex ++, executor, lineList, async);
            }

            executor.shutdown();
            while (!executor.isTerminated()) {
                // 等待所有线程完成
                log.info("executor will wait 10S");
                executor.awaitTermination(10, TimeUnit.SECONDS);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        long end = System.currentTimeMillis();
        log.info("总耗时：{}", end - start);
    }

    /**
     * 处理批量数据
     * 一批处理10W，10个线程，每个线程处理1W
     */
    private static void processBatch(int batchIndex, ExecutorService executor, List<String> lines, RedisAsyncCommands<String, String> async) {
        int totalLines = lines.size();
        for (int i = 0; i < THREAD_COUNT; i++) {
            int start = i * PRE_THREAD_SIZE;
            int end = Math.min((i + 1) * PRE_THREAD_SIZE, totalLines);
            if (start >= totalLines) {
                break; // 如果起始位置已经超过了总行数，直接退出循环
            }
            List<String> subList = lines.subList(start, end);
            List<String> redisFutures = Lists.newArrayList();
            for (String line : subList) {
                // 将数据写入Redis，并获取RedisFuture对象
                //redisFutures.add(async.srem("my_set_", line, "dmpId"));
                redisFutures.add(line);
            }
            executor.submit(new RedisWriter(redisFutures, batchIndex, start, end));
        }
    }

    static class RedisWriter implements Runnable {
        private final List<String> redisFutures;
        private final int batchIndex;
        private final int startIndex;
        private final int endIndex;

        public RedisWriter(List<String> redisFutures, int batchIndex, int startIndex, int endIndex) {
            this.redisFutures = redisFutures;
            this.batchIndex = batchIndex;
            this.startIndex = startIndex;
            this.endIndex = endIndex;
        }

        @Override
        public void run() {
            try {

                //LettuceFutures.awaitAll(10, TimeUnit.SECONDS, redisFutures.toArray(new RedisFuture[0]));
                setToRedis(batchIndex, redisFutures, startIndex, endIndex);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private static void setToRedis(int batchIndex, List<String> redisFutures, int startIndex, int endIndex)  {
        long start = System.currentTimeMillis();
        try {
            Thread.sleep((long) (0.06 * (endIndex - startIndex)));
            log.info("batch:{},当前线程：{}~{},结束执行{}条数据,耗时：{},line-0:{}",batchIndex + 1, batchIndex * BATCH_SIZE + startIndex, batchIndex * BATCH_SIZE + endIndex, redisFutures.size(), System.currentTimeMillis() - start, redisFutures.get(0));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
