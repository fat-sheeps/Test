package com.example.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service("demoService2")
@Slf4j
public class DemoService2 implements DemoService {

    @Override
    public void handle(String param) {
        // 实现逻辑
        log.info("DemoService2 处理请求");
    }
}
