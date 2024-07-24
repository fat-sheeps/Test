package com.example.utils;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class QPSLimiter {
    private volatile int maxQPS;
    private final AtomicInteger tokens;
    private long lastRefillTimestamp;

    public QPSLimiter(int maxQPS) {
        this.maxQPS = maxQPS;
        this.tokens = new AtomicInteger(maxQPS);
        this.lastRefillTimestamp = System.nanoTime();
    }

    public boolean tryAcquire() {
        refillTokens();
        return tokens.getAndDecrement() > 0;
    }

    public void setMaxQPS(int maxQPS) {
        this.maxQPS = maxQPS;
        this.tokens.set(maxQPS);
    }

    private void refillTokens() {
        long now = System.nanoTime();
        long timeSinceLastRefill = now - lastRefillTimestamp;
        long tokensToAdd = TimeUnit.NANOSECONDS.toSeconds(timeSinceLastRefill) * maxQPS;
        if (tokensToAdd > 0) {
            tokens.addAndGet((int) tokensToAdd);
            lastRefillTimestamp = now;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        QPSLimiter limiter = new QPSLimiter(5);
        int m = 0;
        int n = 0;
        for (int i = 0; i < 100; i++) {
            boolean result = limiter.tryAcquire();
            if (result) {
                // 模拟业务处理
                Thread.sleep(1000);
                m ++;
            } else {
                n ++;
            }

        }
        System.out.println("总共耗时：" + (System.currentTimeMillis() - start) + "毫秒");
        System.out.println("通过数：" + m);
        System.out.println("限制数：" + n);
    }
}
