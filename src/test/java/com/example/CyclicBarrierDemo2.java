package com.example;

import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
public class CyclicBarrierDemo2 {
    public static void main(String[] args) throws BrokenBarrierException, InterruptedException, TimeoutException {
        log.info("start");
        // 等待两个线程同步
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5);
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        // 运行两个子线程，当两个子线程的step1都执行完毕后才会执行step2
        // 当两个子线程的step2都执行完毕后才会执行step3
        for(int i = 0; i < 5; i++) {
            // 添加两个线程到线程池
            int j = i;
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    try{
                        log.info(step(" step-" + j, cyclicBarrier));
                        //log.info(step(" step2", cyclicBarrier));

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
        }
        // 关闭线程池
        Thread.sleep(1000L);
        log.info(" finished!");
        executorService.shutdown();
    }

    public static String step(String step,CyclicBarrier cyclicBarrier) throws InterruptedException, BrokenBarrierException {
        //模拟业务耗时
        int t = RandomUtil.randomInt(1000,2000);
        log.info("{}-t:{}",step, t);
        Thread.sleep(t);
        cyclicBarrier.await();
        return step + " run finish!";
    }
}

