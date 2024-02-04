package com.example.utils;

import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.UnknownHostException;
@Slf4j
public class IPUtil {

    private static String port;

    /**
     * 获取本机ip
     * @return 127.0.0.1
     */
    public static String getIp() {
        String ip = "127.0.0.1";
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.error("UnknownHostException:", e);
        }
        return ip;
    }

    /**
     * 获取本服务端口号
     * @return 8888
     */
    public static String getPort() {
       return port;
    }

    public static void setPort(String port) {
        IPUtil.port = port;
    }

    /**
     * 获取机器id
     * @return 127.0.0.1:8888
     */
    public static String getEngineId() {
        return getIp() + ":" + getPort();
    }
}
