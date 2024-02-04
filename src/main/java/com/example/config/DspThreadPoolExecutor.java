package com.example.config;


import java.util.concurrent.*;

/**
 * 线程池-实现全局traceId
 */
public class DspThreadPoolExecutor {

    private static final ThreadPoolExecutor dspThreadPoolExecutor = new ThreadPoolExecutor(225, 500, 30, TimeUnit.SECONDS, new LinkedBlockingQueue<>());


    public static ThreadPoolExecutor getDspThreadPoolExecutor() {
        return dspThreadPoolExecutor;
    }

}