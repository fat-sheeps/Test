package com.example.global;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Scheduler;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
public class NobidCollectCache {
    private final static Cache<String, List<String>> cache = Caffeine.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(1, TimeUnit.MINUTES)
            .scheduler(Scheduler.systemScheduler())
            .removalListener((key, value, cause) -> {
                log.info("达到1分钟过期时间：key:{} value:{}, cause:{}",  key, value, cause.name());
            })
            .build();
     /*private final static Cache<String, List<String>> cache = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(1, TimeUnit.MINUTES)


            removalListener(notification -> {
                log.info("达到1分钟过期时间：key:{} value:{}, notification:{}",  notification.getKey(), notification.getValue(), notification);
            })
            .build();*/

    public static void put(String key, String value) {
        if (value == null) {
            return;
        }
        List<String> list = cache.getIfPresent(key);
        if (list == null) {
            list = new ArrayList<>();
        }
        list.add(value);
        cache.put(key, list);
        log.info("开始put key:{} value:{}", key, value);
    }
    public static List<String> get(String key) {
        return cache.getIfPresent(key);
    }

    public static boolean getSize() {
        return cache.asMap().isEmpty();
    }
}
