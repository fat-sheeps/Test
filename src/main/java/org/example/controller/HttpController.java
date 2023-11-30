package org.example.controller;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.extern.slf4j.Slf4j;
import org.example.config.MdcThreadPoolExecutor;
import org.example.service.CommonService;
import org.example.service.RunService;
import org.example.service.TaskService;
import org.example.service.TranService;
import org.example.utils.IPUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;


@RestController()
@RequestMapping("")
@Slf4j
public class HttpController {

    @Autowired
    private TaskService taskService;

    private ExecutorService executorService;

    @Autowired
    private CommonService commonService;


    private Cache<String, String> cache = CacheBuilder.newBuilder().expireAfterWrite(10, TimeUnit.SECONDS).build();

    //方法入参和返回参数都必须与被服务降级的方法一致
    public Map<String, Object> paymentOkFallbackHandler(long time) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("code", 200);
        resultMap.put("message", "paymentOk超时，服务降级了");
        resultMap.put("time", time);
        return resultMap;
    }

//    @HystrixCommand(fallbackMethod = "paymentOkFallbackHandler", commandKey= "server", commandProperties = {
//        //value表示当前线程的超时时间为3s
//        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")})
    @RequestMapping(value = "/server")
    public Object server(@RequestParam long time) throws InterruptedException {
        log.info("Thread.currentThread().getName():{}", Thread.currentThread().getName());
        long start = System.currentTimeMillis();
        log.info("-------------------executorService start: {}", start);
        String ip = IPUtil.getIp();
        String port = IPUtil.getPort();
        String str = "Server IP :" + ip + " port: " + port;
        log.info("-------------------str: {}", str);
//        executorService = Executors.newSingleThreadExecutor();
//        executorService.submit(new RunService(1));
        MdcThreadPoolExecutor threadPoolExecutor = new MdcThreadPoolExecutor(225, 225, 30, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
        threadPoolExecutor.submit(new RunService(1));
        taskService.async(ip);
        TranService.getInstance().doProcess("do tran!");
        Thread.sleep(time);
        long end = System.currentTimeMillis();
        log.info("-------------------executorService timeout: {}", end - start);
        return commonService.queryAllUser();
    }

    @RequestMapping(value = "/hello")
    public String hello() throws InterruptedException {
//        long start = System.currentTimeMillis();
//        taskService.getMsgMap();
//        long end = System.currentTimeMillis();
//        log.info("time:{}", end - start);
        cache.put("1","11");
        System.out.println((cache.getIfPresent("1")));
        Thread.sleep(5100L);
        System.out.println((cache.getIfPresent("1")));
        cache.put("2","22");
        Thread.sleep(5100L);
        System.out.println((cache.getIfPresent("1")));
        System.out.println((cache.getIfPresent("2")));
        Thread.sleep(5100L);
        return cache.getIfPresent("2");

    }



}
