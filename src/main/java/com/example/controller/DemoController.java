package com.example.controller;

import com.example.service.CommonService;
import com.example.service.DemoService;
import com.example.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.concurrent.ExecutorService;


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



    @RequestMapping(value = "/demo")
    public Object demo(@RequestParam(required = false) Long time, @RequestParam(required = false) Long zonedDateTime)  {

        demoService1.handle("demoService1");
        demoService2.handle("demoService2");
        log.info("time:{}", time);
        log.info("zonedDateTime:{}", ZonedDateTime.ofInstant(Instant.ofEpochMilli(zonedDateTime), ZoneId.systemDefault()));
        return "ok";
    }



}
