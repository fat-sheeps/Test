package org.example.config;


import org.example.utils.MDCUtil;
import org.slf4j.MDC;

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