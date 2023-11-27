//package org.example.controller;
//
//import io.undertow.server.HttpHandler;
//import io.undertow.server.HttpServerExchange;
//import lombok.extern.slf4j.Slf4j;
//import org.example.path.HandlerPath;
//import org.example.config.MdcThreadPoolExecutor;
//import org.example.service.CommonService;
//import org.example.service.RunService;
//import org.example.service.TaskService;
//import org.example.utils.IPUtil;
//import org.example.utils.MDCUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.concurrent.LinkedBlockingQueue;
//import java.util.concurrent.TimeUnit;
//
//@Component
//@HandlerPath(path = "/under")
//@Slf4j
//public class UndertowController implements HttpHandler {
//
//    @Autowired
//    private TaskService taskService;
//
//    @Autowired
//    private CommonService commonService;
//
//    @Override
//    public void handleRequest(HttpServerExchange httpServerExchange) {
//        if (httpServerExchange.isInIoThread()) {
//            log.info("httpServerExchange.isInIoThread()");
//            httpServerExchange.dispatch(this);
//            return;
//        }
//        String traceId = MDCUtil.generateTraceId();
//        MDCUtil.setTraceId(traceId);
//        log.info("traceId:{}", traceId);
//        httpServerExchange.startBlocking();
//        log.info("Thread.currentThread().getName():{}", Thread.currentThread().getName());
//        long start = System.currentTimeMillis();
//        log.info("-------------------executorService start: {}", start);
//        String ip = IPUtil.getIp();
//        String port = IPUtil.getPort();
//        String str = "Server IP :" + ip + " port: " + port;
//        log.info("-------------------str: {}", str);
////        executorService = Executors.newSingleThreadExecutor();
////        executorService.submit(new RunService(1));
//        MdcThreadPoolExecutor threadPoolExecutor = new MdcThreadPoolExecutor(225, 225, 30, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
//        threadPoolExecutor.submit(new RunService(1));
//        taskService.async(ip);
//        long end = System.currentTimeMillis();
//        Object res = commonService.queryAllUser();
//        log.info("-------------------executorService end: {}", end);
//        log.info("-------------------executorService timeout: {}", end - start);
//        log.info("res:{}", res.toString());
//    }
//}
