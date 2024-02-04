package com.example;


import com.example.enums.CountryEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

@Slf4j
public class TestJUC {


    /**
     * countDownLatch
     * 计数器，定义数量为6的计数器，6个线程分别去countDown，直到countDown为0时，主线程再回继续往下走（await之后）
     */
    @Test
    public void test_countDownLatch() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(6);
        //开启6个线程分别去灭6国 并发执行
        ExecutorService executorService = Executors.newFixedThreadPool(6);
        for (int i = 1; i <= 6; i++) {
            int j = i;
            executorService.submit(() -> {
                try{
                    Thread.sleep(1000L);
                    log.info(CountryEnum.getCountryEnum(j).getName() + ":{} 国被灭", j);
                    countDownLatch.countDown();
                }catch (Exception e){
                    e.printStackTrace();
                }
            });
        }
        //await()会阻塞到countDownLatch.countDown()为0 才会往下走
        countDownLatch.await();
        log.info(CountryEnum.QIN.getName() + "统一");
        executorService.shutdown();
    }
    /**
     * cyclicBarrier
     * 回环屏障，它可以使一组线程全部达到一个状态后再全部同时执行，然后重置自身状态又可用于下一次的状态同步
     */
    @Test
    public void test_cyclicBarrier() throws BrokenBarrierException, InterruptedException, TimeoutException {
        // 等待两个线程同步
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        // 运行两个子线程，当两个子线程的step1都执行完毕后才会执行step2
        // 当两个子线程的step2都执行完毕后才会执行step3
        for(int i = 0; i < 2; i++) {
            // 添加两个线程到线程池
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    try{
                        log.info(step(" step1"));
                        cyclicBarrier.await();
                        log.info(step(" step2"));
                        cyclicBarrier.await();
                        log.info(step(" step3"));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
        }
        //Thread.sleep(10000L);
        cyclicBarrier.await(10, TimeUnit.SECONDS);
        // 关闭线程池
        log.info("finished!");
        //executorService.shutdown();
    }

    public String step(String step) throws InterruptedException {
        Thread.sleep(2000L);
        log.info("a");
        return step + " run finish!";
    }

    @Test
    public void test_Semaphore() throws InterruptedException {
        final int num = 30;
        // 初始信号量为0
        Semaphore semaphore = new Semaphore(0);
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        for (int i = 0; i < num; i++){
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread() + " over");
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    // 信号量+1
                    semaphore.release();
                }
            });
        }

        // 当信号量达到2时才停止阻塞 等待子线程执行完毕，返回
        semaphore.acquire(2);
        // 调用acquire的线程会一直阻塞，信号量计数变为2才会返回
        // 如果构造semaphore时传递的参数为N并在M个线程中调用了该信号量的release方法，那么在调用acquire使M个线程同步时传递的参数应该是M+N。
        System.out.println("all child thread over!");
        // 关闭线程池
        executorService.shutdown();
    }

















}
