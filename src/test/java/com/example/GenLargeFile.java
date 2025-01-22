package com.example;

import cn.hutool.core.io.FileUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GenLargeFile {
    public static void main(String[] args) {
        //随机生成1236789个uuid 写入到文件中
        int count = 1236789;
        String file = "C:\\Users\\wayio\\Desktop\\uuid.txt";
        FileUtil.writeUtf8Lines(gen(count), file);

    }
    private static List<String> gen(int count) {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            result.add(String.valueOf(i+1));
        }
        return result;
    }
}
