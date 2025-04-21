package com.example;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.domain.TbUser;
import com.example.mapper.UserMapper;
import com.example.service.CommonService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@SpringBootTest
@Slf4j
public class UserServiceTest {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CommonService commonService;

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

    @Test
    public void test3() throws InterruptedException {
        // 创建两个线程
        Thread thread1 = new Thread(() -> {
            try {
                commonService.updateScore(0, 5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread thread2 = new Thread(() -> {
            try {
                commonService.updateScore(0, 5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread1.start();
        thread2.start();
        //thread1 thread2都执行完成后，再执行下面的代码
        thread1.join();
        thread2.join();
        TbUser user = userMapper.selectById(1);
        log.info("updateScore end final score:{}", user.getScore());
    }
}
