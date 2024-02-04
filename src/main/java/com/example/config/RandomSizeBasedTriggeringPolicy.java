package com.example.config;

import cn.hutool.core.util.RandomUtil;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.rolling.*;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;

/**
 * 自定义RandomSizeBasedTriggeringPolicy实现日志文件大小随机
 */
@Plugin(
        name = "RandomSizeBasedTriggeringPolicy",
        category = "Core",
        printObject = true
)
public class RandomSizeBasedTriggeringPolicy extends AbstractTriggeringPolicy {

    private static final long MAX_FILE_SIZE = 10485760L;
    private final long maxFileSize;
    private RollingFileManager manager;

    protected RandomSizeBasedTriggeringPolicy() {
        this.maxFileSize = MAX_FILE_SIZE;
    }

    protected RandomSizeBasedTriggeringPolicy(final long maxFileSize) {
        this.maxFileSize = maxFileSize;
    }

    protected RandomSizeBasedTriggeringPolicy(final long maxFileSize, String randomFactorSize) {
        this.maxFileSize = maxFileSize;// 随机因子 0~randomFactorMB 会在原size的基础上随机增加0~randomFactor大小，增加粒度为MB
    }


    public void initialize(final RollingFileManager aManager) {
        this.manager = aManager;
    }

    public boolean isTriggeringEvent(final LogEvent event) {
        boolean triggered = this.manager.getFileSize() > this.maxFileSize;
        if (triggered) {
            this.manager.getPatternProcessor().updateTime();
        }

        return triggered;
    }

    public String toString() {
        return "RandomSizeBasedTriggeringPolicy(size=" + this.maxFileSize + ')';
    }

    @PluginFactory
    public static RandomSizeBasedTriggeringPolicy createPolicy(@PluginAttribute("size") final String size,
                                                               @PluginAttribute("randomFactorSize") final String randomFactorSize) {
        long randomFactor = FileSize.parse(randomFactorSize, MAX_FILE_SIZE);
        long randomFactorMB = randomFactor / 1024 / 1024;
        long randomSizeMB = RandomUtil.randomLong(0, randomFactorMB);
        long randomSize = randomSizeMB * 1024 * 1024;
        long maxSize = size == null ? MAX_FILE_SIZE : FileSize.parse(size, MAX_FILE_SIZE);
        return new RandomSizeBasedTriggeringPolicy(maxSize + randomSize);
    }

    public static void main(String[] args) {
        String size = "1M";
        String randomFactorSize = "5M";
        long randomFactor = FileSize.parse(randomFactorSize, MAX_FILE_SIZE);
        long randomFactorMB = randomFactor / 1024 / 1024;
        System.out.println(randomFactorMB);
        long randomSizeMB = RandomUtil.randomLong(0, randomFactorMB);
        System.out.println(randomSizeMB);
        long randomSize = randomSizeMB * 1024 * 1024;
        long maxSize = FileSize.parse(size, MAX_FILE_SIZE);
        System.out.println((maxSize + randomSize) / 1024 / 1024);
    }


}
