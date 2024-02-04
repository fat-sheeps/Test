package com.example.service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RunService implements Runnable{

    public RunService(int i) {
        log.info("RunService running-------{}",i);
    }

    @Override
    public void run() {
        log.info("RunService run running-------");

    }
}
