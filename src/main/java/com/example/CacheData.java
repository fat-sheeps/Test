package com.example;

import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONArray;
import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryType;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class CacheData {
    private static final String PATH = "/datadrive/cache/list.txt";
    @Getter
    private final static List<String> list = new ArrayList<>();

    @PostConstruct
    public static void readFile() {
        //如果文件不存在
        if (!FileUtil.exist(PATH)) {
            log.info("文件不存在:{}", PATH);
            return;
        }
        //读取文件
        try {
            byte[] bytes = FileUtil.readBytes(PATH);
            if (bytes == null) {
                return;
            }
            String text = new String(bytes, StandardCharsets.UTF_8);
            if (!text.isEmpty()) {
                JSON.parseArray(text).forEach(item -> {
                    list.add(item.toString());
                });
            }
            writeFile("");
        } catch (Exception e) {
            log.error("读取文件失败", e);
        }
    }
    //写文件
    public static void writeFile(String text) {
        File file = new File(PATH);
        try {
            //写文件
            FileUtil.writeBytes(text.getBytes(StandardCharsets.UTF_8), file);
            log.info("写文件成功");
        } catch (Exception e) {
            log.error("写文件失败", e);
        }
    }

}
