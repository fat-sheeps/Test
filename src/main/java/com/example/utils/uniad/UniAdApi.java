package com.example.utils.uniad;

import com.example.utils.ObjectUtil;
import com.google.gson.Gson;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * UniAdApi工具类
 */
public class UniAdApi {

    public static void main(String[] args) throws Exception {
        String res = createApp();
        System.out.println(res);

    }

    private static String createApp() throws Exception {
        // 假定endpointUrl为从合作方获取的实际API地址
        String endpointUrl = "https://baidu.com";
        String channel_id = "合作方ID";
        String secret = "合作方密钥";
        Long currentTime = System.currentTimeMillis(); // 获取当前Unix时间戳

        AppCreateRequest createAppRequest = new AppCreateRequest();
        createAppRequest.setApp_name("应用名称");
        createAppRequest.setPackage_name("com.example.myapp");
        createAppRequest.setDcloud_app_id("");
        createAppRequest.setChannel_id(channel_id);
        createAppRequest.setTime(currentTime);
        createAppRequest.setOs(1);
        //TODO

        // 将createAppRequest转为HashMap
        Map<String, Object> map = ObjectUtil.toMap(createAppRequest);
        String sign = CommonParams.generateSign(map, secret);
        createAppRequest.setSign(sign);
        // 将请求体转化为JSON字符串
        String jsonBody = new Gson().toJson(createAppRequest);

        String res = sendRequest(endpointUrl, jsonBody);
        return res;
    }

    public static String sendRequest(String endpointUrl, String jsonBody) throws Exception {
        System.out.println("sendRequest response req : " + jsonBody);
        URL url = new URL(endpointUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json; utf-8");
        connection.setDoOutput(true);

        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonBody.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }
        System.out.println("sendRequest response code : " + connection.getResponseCode());
        connection.disconnect();

        return connection.getResponseMessage();
    }


}
