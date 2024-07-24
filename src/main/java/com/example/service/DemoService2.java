package com.example.service;

import org.springframework.stereotype.Service;

@Service("demoService2")
public class DemoService2 implements DemoService {

    @Override
    public void handle(String param) {
        // 实现逻辑
        System.out.println("DemoService2 处理请求");
    }
}
