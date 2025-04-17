package com.example.controller;

import com.example.global.NobidCollectCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("cache")
@Slf4j
public class CacheController {




    @PostMapping(value = "/put")
    public Object put(@RequestParam() String key, @RequestParam() String value)  {
        log.info("key:{}, value:{}", key, value);
        NobidCollectCache.put(key, value);
        return "ok";
    }

    @PostMapping(value = "/get")
    public Object get(@RequestParam() String key)  {
        log.info("key:{}", key);
        return NobidCollectCache.get(key);
    }

    @PostMapping(value = "/getSize")
    public Object getSize()  {
        return NobidCollectCache.getSize();
    }





}
