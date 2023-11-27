package org.example.config;

import org.example.utils.MDCUtil;
import org.slf4j.MDC;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * 异步线程拦截器-实现全局traceId
 */
@Configuration
@EnableAsync  //开启异步操作
public class ThreadPoolTaskExecutorConfig implements AsyncConfigurer {

    @Bean("threadPoolTaskExc")
    public ThreadPoolTaskExecutor getAsyncExecutor() {
        // 对线程池进行包装，使之支持traceId透传
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor() {
            @Override
            public <T> Future<T> submit(Callable<T> task) {
                // 传入线程池之前先复制当前线程的MDC
                return super.submit(MDCUtil.wrap(task, MDC.getCopyOfContextMap()));
            }
            @Override
            public void execute(Runnable task) {
                super.execute(MDCUtil.wrap(task, MDC.getCopyOfContextMap()));
            }
        };
        executor.setCorePoolSize(5);//核心线程数
        executor.setMaxPoolSize(10);//最大线程数
        executor.setQueueCapacity(25);//线程队列
        executor.initialize();//线程初始化
        return executor;
    }
    public static <T> Callable<T> wrap(final Callable<T> callable, final Map<String, String> context) {
        return new Callable<T>() {
            @Override
            public T call() throws Exception {
                // 实际执行前导入对应请求的MDC副本
                if (context == null) {
                    MDC.clear();
                } else {
                    MDC.setContextMap(context);
                }
                if (MDCUtil.getTraceId() == null) {
                    MDCUtil.setTraceId();
                }
                try {
                    return callable.call();
                } finally {
                    MDC.clear();
                }
            }
        };
    }
}
