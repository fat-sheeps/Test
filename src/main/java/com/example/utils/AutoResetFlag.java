package com.example.utils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class AutoResetFlag {
    private volatile boolean flag = false;
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private AtomicReference<Runnable> scheduledTask = new AtomicReference<>();

    // 手动设置为true，并启动定时重置任务
    public synchronized void setTrue(int seconds) {
        flag = true;
        // 取消之前的未完成任务（避免多次调用导致多个任务）
        if (scheduledTask.get() != null) {
            scheduler.schedule(scheduledTask.get(), 0, TimeUnit.SECONDS); // 立即取消
        }
        // 新建重置任务
        Runnable resetTask = () -> flag = false;
        scheduledTask.set(resetTask);
        scheduler.schedule(resetTask, seconds, TimeUnit.SECONDS);
    }

    // 获取当前状态
    public boolean getFlag() {
        return flag;
    }

    // 关闭线程池（程序退出时调用）
    public void shutdown() {
        scheduler.shutdown();
    }

    @Override
    public String toString() {
        return "{" +
                "flag=" + flag +
                '}';
    }
}
