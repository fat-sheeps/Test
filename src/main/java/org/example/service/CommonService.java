package org.example.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.example.domain.User;
import org.example.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class CommonService {
    @Autowired
    private UserMapper userMapper;

    public Object queryAllUser() {
        log.info("queryAllUser begin:");
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        List<User> users = userMapper.selectList(queryWrapper);
        log.info("queryAllUser users:{}", JSON.toJSONString(users));
        return users;
    }
    @Scheduled(cron = "0 * * * * ?")
    public void print() {
        log.info("Scheduled!");
    }
}
