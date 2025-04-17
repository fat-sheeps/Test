package com.example;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.domain.TbUser;
import com.example.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void test1() {
        Map<String, Map<String, Object>> map = userMapper.queryAllScore();
        for (Map.Entry<String, Map<String, Object>> entry : map.entrySet()) {
            System.out.println(entry.getKey());
            System.out.println(JSON.toJSONString(entry.getValue()));
        }
    }
    @Test
    public void test2() {
        Wrapper<TbUser> queryMapper = new QueryWrapper<TbUser>();
        List<TbUser> list = userMapper.selectList(queryMapper);
        System.out.println(list);
    }
}
