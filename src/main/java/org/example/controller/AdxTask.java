package org.example.controller;

import com.netflix.hystrix.exception.HystrixRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.example.domain.AdexHystrixCommand;

@Slf4j
public class AdxTask implements Runnable {

    @Override
    public void run() {
        int timeout = 1000;
        long start = System.currentTimeMillis();
        try {
            //log.info("---------------开始-----------------{}", j);
            AdexHystrixCommand command = new AdexHystrixCommand("group", "command" + timeout, timeout);
            String res = command.execute();
            long end = System.currentTimeMillis();
            log.info("-completed-限制timeout:" + timeout + ",执行time:" + (end - start) + "业务耗时：" + res);

        } catch (HystrixRuntimeException e) {
            //log.error(e.getMessage());
            long end = System.currentTimeMillis();
            log.info("-Hystrix-限制timeout:" + timeout + ",执行time:" + (end - start));
        }
    }
}
