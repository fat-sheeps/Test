//package org.example.config;
//
//
//import org.example.utils.MDCUtil;
//import org.slf4j.MDC;
//
//import java.util.Collection;
//import java.util.List;
//import java.util.concurrent.*;
//
///**
// * Executor线程池-实现全局traceId
// */
//public class MdcExecutor implements ScheduledExecutorService {
//
//
//    @Override
//    public ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
//        return null;
//    }
//
//    @Override
//    public <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit) {
//        return null;
//    }
//
//    @Override
//    public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
//
//        return this.scheduleAtFixedRate(MDCUtil.wrap(command, MDC.getCopyOfContextMap()),initialDelay, period,  unit);
//    }
//
//    @Override
//    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
//        return null;
//    }
//
//    @Override
//    public void shutdown() {
//        MDCUtil.clear();
//    }
//
//    @Override
//    public List<Runnable> shutdownNow() {
//        return null;
//    }
//
//    @Override
//    public boolean isShutdown() {
//        return false;
//    }
//
//    @Override
//    public boolean isTerminated() {
//        return false;
//    }
//
//    @Override
//    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
//        return false;
//    }
//
//    @Override
//    public <T> Future<T> submit(Callable<T> task) {
//        return null;
//    }
//
//    @Override
//    public <T> Future<T> submit(Runnable task, T result) {
//        return null;
//    }
//
//    @Override
//    public Future<?> submit(Runnable task) {
//        return null;
//    }
//
//    @Override
//    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
//        return null;
//    }
//
//    @Override
//    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException {
//        return null;
//    }
//
//    @Override
//    public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
//        return null;
//    }
//
//    @Override
//    public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
//        return null;
//    }
//
//    @Override
//    public void execute(Runnable command) {
//        this.execute(MDCUtil.wrap(command, MDC.getCopyOfContextMap()));
//    }
//}