package com.example.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.domain.User;
import com.example.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Component
@Slf4j
public class CommonService {
    @Autowired
    private UserMapper userMapper;

    public Object queryAllUser() {
        log.info("queryAllUser begin:");
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().ge(User::getId, 1);
        List<User> users = userMapper.selectList(queryWrapper);
        log.info("queryAllUser users:{}", JSON.toJSONString(users));
        return users;
    }
    @Scheduled(cron = "58 0/1 * * * ?")
    public void print() {
        log.info("Scheduled!");
    }

//    @PostConstruct
    public void init() {
        System.out.println("tantan_request tantan_request.info");
        ExecutorService executor = Executors.newFixedThreadPool(5);
        List<String> futures = new ArrayList<>();
        // 执行耗时操作，例如发送HTTP请求或数据库查询
        for (int i = 0; i < 5; i++) {
            Future<String> future = executor.submit(() -> {

                SystemLogFactory.info("tantan_request","tantan_request.info");
                SystemLogFactory.info("tantan_response","tantan_response.info");
                SystemLogFactory.info("momo_request","momo_request.info");
                SystemLogFactory.info("momo_response","momo_response.info");
                return "Task completed";
            });
            String res;
            try {
                res = future.get(150, TimeUnit.MILLISECONDS);
            } catch (TimeoutException | InterruptedException | ExecutionException e) {
                //log.error("error:{}", e.getMessage());
                res = "timeout!";
            }
            futures.add(res);

        }

    }
}
