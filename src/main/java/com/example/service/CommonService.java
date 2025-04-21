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
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * 更新用户积分
     * MVCC可保证多个线程同时执行 数据不会重复
     * @param seconds
     * @throws InterruptedException
     */
    @Transactional
    public void updateScore(int seconds, int step) throws InterruptedException {
        log.info("updateScore begin:");
        userMapper.updateScore(1L, step);
        Thread.sleep(seconds * 1000L);
        TbUser user = userMapper.selectById(1);
        List<Integer> ids = new ArrayList<>();
        for (int i = 0; i < step; i++) {
            ids.add(user.getScore() - i);
        }
        log.info("updateScore end score:{}", user.getScore());
        //TODO ids 可放到一个全局队列中，一旦用完，则触发当前方法重新生成step个新id放到队列中供后续使用
        log.info("current ids:{}", ids);
    }
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
