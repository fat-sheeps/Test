package org.example.service;

import com.google.common.collect.Maps;
import org.apache.logging.log4j.Logger;
import org.example.logs.MomoRequestLog;
import org.example.logs.MomoResponseLog;
import org.example.logs.TantanRequestLog;
import org.example.logs.TantanResponseLog;

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
