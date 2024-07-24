package com.example.service;

import org.springframework.stereotype.Service;

@Service("demoService1")
public class DemoService1 implements DemoService {

    @Override
    public void handle(String param) {
        // 实现逻辑
        System.out.println("DemoService1 处理请求");
    }
}
