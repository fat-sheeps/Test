package com.example.service;

import com.example.logs.MomoRequestLog;
import com.example.logs.MomoResponseLog;
import com.example.logs.TantanRequestLog;
import com.example.logs.TantanResponseLog;
import com.google.common.collect.Maps;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class SystemLogFactory {
    private static Map<String, Logger> logMap = Maps.newHashMap();

    private static SystemLogFactory instance = null;

    private SystemLogFactory() {
        new MomoRequestLog();
        new MomoResponseLog();
        new TantanRequestLog();
        new TantanResponseLog();
    }

    public static void put(String key, Logger logger) {
        logMap.put(key, logger);
    }

    public static void info(String key, String msg) {
        if (instance == null) {
            instance = new SystemLogFactory();
        }
        //log
        logMap.get(key).info(msg);
    }




}
