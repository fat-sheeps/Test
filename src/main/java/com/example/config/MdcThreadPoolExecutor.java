package com.example.config;


import com.example.utils.MDCUtil;
import org.slf4j.MDC;

import java.util.concurrent.*;

/**
 * 线程池-实现全局traceId
 */
public class MdcThreadPoolExecutor extends ThreadPoolExecutor {

    private static final MdcThreadPoolExecutor mdcThreadPoolExecutor = new MdcThreadPoolExecutor(225, 225, 30, TimeUnit.SECONDS, new LinkedBlockingQueue<>());

    public MdcThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public MdcThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    public MdcThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
    }

    public MdcThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    public static MdcThreadPoolExecutor getMdcThreadPoolExecutor() {
        return mdcThreadPoolExecutor;
    }

    @Override
    public void execute(Runnable command) {
        super.execute(MDCUtil.wrap(command, MDC.getCopyOfContextMap()));
    }
}