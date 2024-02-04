package com.example.utils;

import lombok.extern.slf4j.Slf4j;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryType;

@Slf4j
public class SystemUtil {

    public static String getMemoryInfo() {
        StringBuilder str = new StringBuilder();
        //获取堆内存信息
        long maxMemory = Runtime.getRuntime().maxMemory();
        long totalMemory = Runtime.getRuntime().totalMemory();
        str.append("-Xmx(最大可用内存): ").append(maxMemory / 1024 / 1024).append("MB");
        str.append("\r\n");
        str.append("-Xms(已获得内存): ").append(totalMemory / 1024 / 1024).append("MB");
        str.append("\r\n");
        str.append("================================================================================================================\r\n");
        return str.toString();
    }

    public static String padRightWithSpaces(String str, int totalLength) {
        int spacesToAdd = totalLength - str.length();
        if (spacesToAdd <= 0) {
            return str;
        }
        StringBuilder sb = new StringBuilder(str);
        for (int i = 0; i < spacesToAdd; i++) {
            sb.append(' ');
        }
        return sb.toString();
    }

    public static String getMemoryInfoDetail() {
        StringBuilder str = new StringBuilder();
        for (MemoryPoolMXBean memoryPool : ManagementFactory.getMemoryPoolMXBeans()) {
            if (memoryPool.getType() == MemoryType.HEAP) {
                str.append("堆内内存：").append(SystemUtil.padRightWithSpaces(memoryPool.getName(), 20));
                str.append("初始大小：").append(memoryPool.getUsage().getInit() / (1024 * 1024)).append("MB");
                str.append("\t已使用大小：").append(memoryPool.getUsage().getUsed() / (1024 * 1024)).append("MB");
                str.append("\t最大大小：").append(memoryPool.getUsage().getMax() / (1024 * 1024)).append("MB");
                str.append("\t已提交大小：").append(memoryPool.getUsage().getCommitted() / (1024 * 1024)).append("MB");
                str.append("\t使用率：").append(memoryPool.getUsage().getUsed() * 100d / memoryPool.getUsage().getMax()).append("%");
                str.append("\r\n");
                str.append("=================================================================================================================================\r\n");
            }
        }
        return str.toString();
    }
}
