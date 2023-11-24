//package org.example.config;
//
//import org.example.utils.MDCUtil;
//import org.example.utils.ThreadMdcUtil;
//import org.slf4j.MDC;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.annotation.AsyncConfigurer;
//import org.springframework.scheduling.annotation.EnableAsync;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
//
//import java.util.Map;
//import java.util.concurrent.Callable;
//import java.util.concurrent.Future;
//import java.util.concurrent.ThreadPoolExecutor;
//
//@Configuration
//@EnableAsync  //开启异步操作
//public class ThreadPoolExecutorConfig implements AsyncConfigurer {
//
//    //@Bean("SpExecutor")
//    public ThreadPoolExecutor getAsyncExecutor() {
//        // 对线程池进行包装，使之支持traceId透传
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor() {
//            @Override
//            public <T> Future<T> submit(Callable<T> task) {
//                // 传入线程池之前先复制当前线程的MDC
//                return super.submit(ThreadMdcUtil.wrap(task, MDC.getCopyOfContextMap()));
//            }
//            @Override
//            public void execute(Runnable task) {
//                super.execute(ThreadMdcUtil.wrap(task, MDC.getCopyOfContextMap()));
//            }
//        };
//        executor.setCorePoolSize(5);//核心线程数
//        executor.setMaxPoolSize(10);//最大线程数
//        executor.setQueueCapacity(25);//线程队列
//        executor.initialize();//线程初始化
//        return executor;
//    }
//    public static <T> Callable<T> wrap(final Callable<T> callable, final Map<String, String> context) {
//        return new Callable<T>() {
//            @Override
//            public T call() throws Exception {
//                // 实际执行前导入对应请求的MDC副本
//                if (context == null) {
//                    MDC.clear();
//                } else {
//                    MDC.setContextMap(context);
//                }
//                if (MDCUtil.getTraceId() == null) {
//                    MDCUtil.setTraceId(MDCUtil.generateTraceId());
//                }
//                try {
//                    return callable.call();
//                } finally {
//                    MDC.clear();
//                }
//            }
//        };
//    }
//}
