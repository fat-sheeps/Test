package com.example.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service("demoService1")
@Slf4j
public class DemoService1 implements DemoService {

    @Override
    public void handle(String param) {
        // 实现逻辑
        log.info("DemoService1 处理请求");
    }
}
