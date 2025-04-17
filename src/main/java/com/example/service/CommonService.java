package com.example.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.domain.TbUser;
import com.example.domain.UserEvent;
import com.example.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Component
@Slf4j
public class CommonService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    public Object queryAllUser() {
        log.info("queryAllUser begin:");
        QueryWrapper<TbUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().ge(TbUser::getAge, 0);
        List<TbUser> users = userMapper.selectList(queryWrapper);
        log.info("queryAllUser users:{}", JSON.toJSONString(users));
        for (TbUser user : users) {
            applicationEventPublisher.publishEvent(new UserEvent(this, user.getId() + ""));
        }
        log.info("queryAllUser end1");
        return users;
    }

    public Object queryForPage() {
        log.info("queryForPage begin:");
        QueryWrapper<TbUser> queryWrapper = new QueryWrapper<>();
        Page<TbUser> page = new Page<>(1, 3);
        IPage<TbUser> users = userMapper.selectPage(page, queryWrapper);
        return users;
    }
    //@Scheduled(cron = "58 0/1 * * * ?")
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
