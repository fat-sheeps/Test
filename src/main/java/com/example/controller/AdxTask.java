package com.example.controller;

import cn.hutool.core.util.RandomUtil;
import com.example.domain.AdxCommon;
import com.example.domain.DspHystrixCommand;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AdxTask implements Runnable {

    @Override
    public void run() {
        int timeout = 1000;
        long start = System.currentTimeMillis();
        int t = RandomUtil.randomInt(0, 2000);
        try {
            //log.info("---------------开始-----------------{}", j);
            DspHystrixCommand command = new DspHystrixCommand("group", "command" + timeout, timeout, t);
//            DspHystrixCommand command = DspHystrixCommand.getDspHystrixCommand("group", "command" + timeout, timeout, t);
            String res = command.execute();
            long end = System.currentTimeMillis();
            log.info("-Completed-timeout:" + timeout + ",执行time:" + (end - start) + "业务耗时：" + res + ",t:" + t);

        } catch (HystrixRuntimeException e) {
            //log.error(e.getMessage());
            long end = System.currentTimeMillis();
            log.error("-Hystrix-timeout:" + timeout + ",执行time:" + (end - start) + ",t:" + t);
            if ((end- start) < 50) {
                log.error("num:{} time < 50 e：{}", AdxCommon.num.addAndGet(1),e.getMessage());
            }
//            if (!e.getMessage().contains("timed-out")) {
//                log.error("e：",e);
//            }
        }
    }
}
