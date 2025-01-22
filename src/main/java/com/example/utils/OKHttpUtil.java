package com.example.utils;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Slf4j
public class OKHttpUtil {

    public static void main(String[] args) {
        int timeout = 6000;

//        String url = "http://localhost:8888/imp?id=111&name=zhang";
//        String res = exec(url, timeout);

        String url = "http://localhost:8888/imp?id=1";
        String json = "{\"id\":\"111\",\"pid\":\"222\",\"pname\":\"wang\"}";
        String res = post(url, json, timeout);
        System.out.println(res);
    }

    public static String exec(String url, int timeout) {
        String res = null;
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(timeout, TimeUnit.MILLISECONDS)
                .build();

        Request request = new Request.Builder()
                .url(url)
                //增加参数
                .addHeader("Content-Type", "application/json")
                .build();

        try {
            Response response = client.newCall(request).execute();
            log.info("Received status code: {}", response.code());
            if (response.body() != null) {
                res =  response.body().string();
                log.info("Received res: {}", res);

            }
            response.close();
        } catch (IOException e) {
            log.error("请求失败: " + e.getMessage(), e);
        } finally {
            client.dispatcher().executorService().shutdown(); // 关闭 OkHttpClient 的线程池
        }
        return res;
    }

    public static final MediaType JSON  = MediaType.get("application/json; charset=utf-8");


    public static String post(String url, String json, int timeout) {
        log.info("Sending request to: {} req:{}", url, json);
        String res = null;
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(timeout, TimeUnit.MILLISECONDS)
                .build();
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (response.body() != null) {
                res =  response.body().string();
                log.info("Received res: {}", res);
            }
            return res;
        } catch (IOException e) {
            log.error("请求失败: " + e.getMessage(), e);
        }
    return res;
    }
}

