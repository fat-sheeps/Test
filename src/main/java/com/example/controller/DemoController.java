package com.example.controller;

import com.alibaba.fastjson.JSON;
import com.example.CacheData;
import com.example.service.CommonService;
import com.example.service.DemoService;
import com.example.service.TaskService;
import com.example.utils.ExchangeRateUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

@RestController()
@RequestMapping("")
@Slf4j
public class DemoController {

    @Autowired
    @Qualifier("demoService1")
    private DemoService demoService1;

    @Autowired
    @Qualifier("demoService2")
    private DemoService demoService2;

//    @Autowired
//    private PasswordEncoder passwordEncoder;



    @PostMapping(value = "/demo")
    public Object demo(@RequestParam(required = false) Long time, @RequestParam(required = false) Long zonedDateTime)  {

        demoService1.handle("demoService1");
        demoService2.handle("demoService2");
        log.info("time:{}", time);
        log.info("zonedDateTime:{}", ZonedDateTime.ofInstant(Instant.ofEpochMilli(zonedDateTime), ZoneId.systemDefault()));
        return "ok";
    }

    @PostMapping(value = "/addList")
    public Object addList(@RequestParam(required = false) String value)  {
        CacheData.getList().add(value);
        return "ok";
    }

    /*@PostMapping(value = "/password")
    public Object password(@RequestParam(required = false) String value)  {
        String password = passwordEncoder.encode(value);
        String password2 = passwordEncoder.encode(value);
        String password3 = passwordEncoder.encode(value);
        System.out.println(password);
        System.out.println(password2);
        System.out.println(password3);
        return password;
    }*/

    @RequestMapping(value = "/imp",  method = {RequestMethod.POST, RequestMethod.GET})
    public Object imp(HttpServletRequest httpServletRequest, HttpServletResponse response,
                      @RequestParam(value = "id", required = false) String id,
                      @RequestParam(value = "name", required = false) String name,
                      @RequestBody(required = false)  Request request) throws InterruptedException {
        // 获取ua
        String ua = httpServletRequest.getHeader("User-Agent");
        if (request == null) {
            request = new Request();
        }
        log.info("Protocol:{}", httpServletRequest.getProtocol());
        request.setId(id);
        request.setName(name);
        log.info("req:{}", request);
        //客户端请求超时时间,1s 模拟499
        Thread.sleep(500);
        request.setUa(ua);
        /*if (requestParams != null) {
            request.setPid(requestParams.getId());
            request.setPname(requestParams.getName());
        }*/
        log.info("res:{}", ua);
        return request;
    }




    @Data
    public static class Request {
        private String id;
        private String name;

        private String pid;
        private String pname;

        private String ua;
    }


    @RequestMapping(value = "/a",  method = {RequestMethod.POST, RequestMethod.GET})
    public Object a(HttpServletRequest request, HttpServletResponse response,
                      @RequestParam(value = "id", required = false) String id) throws ServletException, IOException {
        // 设置响应头，防止缓存
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        // 将请求转发到地址 c
//        request.getRequestDispatcher("http://localhost:8888/b?id=" + id).forward(request, response);
        // 将请求转发到地址 c 使用HttpClient进行代理请求
        try {
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpGet httpGet = new HttpGet("http://localhost:8888/c?id=" + id);
            HttpResponse proxyResponse = httpClient.execute(httpGet);

            HttpEntity entity = proxyResponse.getEntity();
            if (entity!= null) {
                InputStream inputStream = entity.getContent();
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer))!= -1) {
                    response.getOutputStream().write(buffer, 0, bytesRead);
                }
                inputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error proxying request.");
        }

        return "hello a:" + id;
    }

    @RequestMapping(value = "/b",  method = {RequestMethod.POST, RequestMethod.GET})
    public Object b(HttpServletRequest request, HttpServletResponse response,
                    @RequestParam(value = "id", required = false) String id) throws ServletException, IOException {

        log.info("X-Forwarded-For:{}", request.getHeader("X-Forwarded-For"));
        log.info("X-Real-IP:{}", request.getHeader("X-Real-IP"));
        // 设置响应头，防止缓存
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("X-Forwarded-For", request.getHeader("X-Forwarded-For"));
        response.setHeader("X-Real-IP", request.getHeader("X-Real-IP"));
        response.setDateHeader("Expires", 0);

        // 将请求转发到地址 c
        //request.getRequestDispatcher("http://localhost:8888/c?id=" + id).forward(request, response);
        response.setStatus(HttpServletResponse.SC_FOUND);
        response.setHeader("Location", "http://localhost:8888/c?id=" + id);

        return "hello b:" + id;
    }

    @RequestMapping(value = "/c",  method = {RequestMethod.POST, RequestMethod.GET})
    public Object c(HttpServletRequest request, HttpServletResponse response,
                      @RequestParam(value = "id", required = false) String id) {
        log.info("X-Forwarded-For:{}", request.getHeader("X-Forwarded-For"));
        log.info("X-Real-IP:{}", request.getHeader("X-Real-IP"));
        return "hello c:" + id;
    }

    public static void main(String[] args) throws IOException {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet("http://localhost:8888/b?id=" + "1");
        HttpResponse proxyResponse = httpClient.execute(httpGet);

        HttpEntity entity = proxyResponse.getEntity();
        if (entity!= null) {
            System.out.println(proxyResponse.getStatusLine().getStatusCode());
            InputStream inputStream = entity.getContent();
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer))!= -1) {
                System.out.write(buffer, 0, bytesRead);
            }
            inputStream.close();
        }

    }

    @GetMapping(value = "/workers")
    public Object workers(@RequestParam(required = false) Long time, @RequestParam(required = false) Long zonedDateTime) throws InterruptedException {

        Thread.sleep(30000);
        return "ok";
    }


}
