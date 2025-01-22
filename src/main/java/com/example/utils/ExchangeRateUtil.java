package com.example.utils;


import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class ExchangeRateUtil {
    private static final String API_KEY = "2d01a353bf9333c02404d54907e421b1";
    private static final String apiUrl = "http://op.juhe.cn/onebox/exchange/currency";;
    private static final double defaultRate = 7.1;;
    public static void main(String[] args) throws Exception {
        double rate = getExchangeRate("USD", "CNY");
        System.out.println(rate);
    }
    public static double getExchangeRate(String from, String to) {
        ExchangeResult res = null;
        try {
            res = queryExchangeRate(from, to);
        } catch (Exception e) {
            log.error("queryExchangeRate error: ", e);
        }
        if (res == null) {
            return defaultRate;
        }
        if (CollectionUtil.isEmpty(res.getResult())) {
            return defaultRate;
        }
        return res.getResult().stream().filter(i -> i.getCurrencyF().equals(from)).findFirst().map(ExchangeRateResult::getExchange).orElse(defaultRate);
    }
    public static ExchangeResult queryExchangeRate(String from, String to) throws Exception {


        HashMap<String, String> map = new HashMap<>();
        map.put("version", "2");
        map.put("key", API_KEY);
        map.put("from", from);
        map.put("to", to);

        URL url = new URL(String.format(apiUrl + "?" + params(map)));
        log.info("queryExchangeRate url: {}", url);
        BufferedReader in = new BufferedReader(new InputStreamReader((url.openConnection()).getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        log.info("queryExchangeRate res :{}", response);
        if (response.length() > 0) {
            return JSON.parseObject(response.toString(), ExchangeResult.class);
        }
        return null;
    }

    @Data
    public static class ExchangeResult {
        private String reason;
        private List<ExchangeRateResult> result;
        private int error_code;
    }
    @Data
    public static class ExchangeRateResult {
        private String currencyF;
        private String currencyF_Name;
        private String currencyT;
        private String currencyT_Name;
        private String currencyFD;
        private double exchange;
        private String result;
        private LocalDateTime updateTime;
    }

        public static String params(Map<String, String> map) {
        return map.entrySet().stream()
                .map(entry -> {
                    try {
                        return entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                        return entry.getKey() + "=" + entry.getValue();
                    }
                })
                .collect(Collectors.joining("&"));
    }
}