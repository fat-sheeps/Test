//package org.example;
//
//import cn.hutool.core.util.RandomUtil;
//import lombok.extern.slf4j.Slf4j;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.CompletableFuture;
//import java.util.concurrent.ExecutionException;
//import java.util.concurrent.TimeUnit;
//@Slf4j
//public class MultiThreadHttpClient {
//
//    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        long start = System.currentTimeMillis();
//        int numberOfThreads = 5; // 设置线程数
//        List<CompletableFuture<String>> futures = new ArrayList<>();
//
//
//        for (int i = 0; i < numberOfThreads; i++) {
//            int j = i;
//            CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
//                return callHttp("http:" + j); // 替换为实际的接口地址
//            });
//
//            CompletableFuture<String> timedFuture = future
//                    .completeOnTimeout("timeout!", 150, TimeUnit.MILLISECONDS)
//                    .thenApply(response -> {
//                        // 处理超时
//                        //log.error("HTTP request timed out");
//                        // 或者其他标识，视需求而定
//                        return response;
//                    });
//
//            futures.add(timedFuture);
//        }
//
//        List<String> successfulResponses = new ArrayList<>();
//
//        for (CompletableFuture<String> future : futures) {
//            String response = future.get();
//            if (response != null) {
//                successfulResponses.add(response);
//            }
//        }
//
//        // 打印成功的响应
//        log.info("Successful Responses: " + successfulResponses);
//        long end = System.currentTimeMillis();
//        log.info("end time: " + (end - start));
//    }
//
//    private static String callHttp(String url) {
//        int t = RandomUtil.randomInt(10, 400);
//        try {
//            log.info("{} t:{}", url, t);
//            Thread.sleep(t);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return "Response-" + url + " time:" + t;
//    }
//
//}
