package com.example.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * url工具类
 */
public class UrlUtil {
    public static class UrlEntity {
        /**
         * 原url 矫正后（带参数，但无?）
         */
        private String url = "";
        /**
         * url地址
         */
        private String baseUrl;
        /**
         * url参数
         */
        private LinkedHashMap<String, String> params = new LinkedHashMap<>();

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getBaseUrl() {
            return baseUrl;
        }

        public void setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
        }

        public LinkedHashMap<String, String> getParams() {
            return params;
        }

        public void setParams(LinkedHashMap<String, String> params) {
            this.params = params;
        }


    }

    /**
     * 解析url
     *
     * @param url
     * @return
     */
    public static UrlEntity parse(String url) {
        UrlEntity entity = new UrlEntity();
        if (url == null || url.isEmpty()) {
            return entity;
        }
        url = url.trim();
        if (url.isEmpty()) {
            return entity;
        }
        entity.setUrl(url);
        if (url.contains("?")) {
            //正常的url带参数
            String[] urlParts = url.split("\\?");
            entity.setBaseUrl(urlParts[0]);
            //无参数
            if (urlParts.length == 1) {
                return entity;
            }
            //有参数
            getParams(urlParts[1], entity);

            return entity;
        }
        if (url.contains("&")) {
            //非正常url，但是有&
            String[] urlParts = url.split("&");
            List<String> urlPartList = new ArrayList<>(Arrays.asList(urlParts));
            //urlParts移除第0个元素，因为第0个元素是域名
            entity.setUrl(url.replaceFirst("&", "?"));
            entity.setBaseUrl(urlParts[0]);
            urlPartList.remove(urlParts[0]);
            String urlPartsStr = String.join("&", urlPartList);
            getParams(urlPartsStr, entity);
            return entity;
        }
        return entity;

    }

    private static void getParams(String urlPartsStr, UrlEntity entity) {
        Pattern pattern = Pattern.compile("([^=]+)=([^&]*)");
        Matcher matcher = pattern.matcher(urlPartsStr);

        while (matcher.find()) {
            String key = matcher.group(1);
            //key去掉?和&
            key = key.replaceFirst("\\?|\\&", "");
            String value = matcher.group(2);
            entity.getParams().put(key, value);
        }
        /*String[] params = urlPartsStr.split("&");
        for (String param : params) {
            String[] keyValue = param.split("=");
            if (keyValue.length > 1) {
                entity.getParams().put(keyValue[0], keyValue[1]);
            }
        }*/
    }

    private static void buildUrl(UrlEntity entity) {
        StringBuilder urlPartsStr = new StringBuilder();
        for (Map.Entry<String, String> entry : entity.getParams().entrySet()) {
            urlPartsStr.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        urlPartsStr = new StringBuilder(urlPartsStr.substring(0, urlPartsStr.length() - 1));
        entity.setUrl(entity.getBaseUrl() + "?" + urlPartsStr);
    }

    /**
     * 获取url指定参数值
     *
     * @param url
     * @param key 参数名
     * @return
     */
    public static String getParamsByKey(String url, String key) {
        UrlEntity entity = parse(url);
        if (entity.getParams() == null || entity.getParams().isEmpty()) {
            return null;
        }
        return entity.getParams().get(key);
    }

    public static String urlDecode(String url) {
        String decodedString = "";
        if (url == null || url.isEmpty()) {
            return decodedString;
        }
        try {
            decodedString = URLDecoder.decode(url, "UTF-8"); // 使用UTF-8字符集进行解码
        } catch (UnsupportedEncodingException e) {
            // UTF-8是Java平台的标准字符集，所以这里通常不会抛出UnsupportedEncodingException
            e.printStackTrace();
        }
        return decodedString;
    }

    public static String urlEncode(String url) {
        String encodeString = "";
        if (url == null || url.isEmpty()) {
            return encodeString;
        }
        try {
            encodeString = URLEncoder.encode(url, "UTF-8"); // 使用UTF-8字符集进行解码
        } catch (UnsupportedEncodingException e) {
            // UTF-8是Java平台的标准字符集，所以这里通常不会抛出UnsupportedEncodingException
            e.printStackTrace();
        }
        return encodeString;
    }

    public static void main(String[] args) {
        String url1  = "http%3A%2F%2Fres.winas0.online%2Fwinloss%2FforeignPostback%3Faction%3Dinstall%26deviceId3%3D__IDFA__%26deviceId5%3D__GAID__%26adid%3D__UNIQUE_ID__%26partner%3D__PARTNER__%26ua%3D__UA__%26bundleId%3D__BUNDLE_ID__%26event%3D9%26mark%3Dadjust";
        System.out.println(urlDecode(url1));
//        String url = "https://app.adjust.com/13af6dvq?adgroup=cpa_mobile&creative=Axpo_iplay_{sub_id}&idfa=__IDFA__&gps_adid=__GAID__&install_callback=https%3A%2F%2Fsgp.digcto.com%2Fwinloss%2FforeignPostback%3Faction%3Dinstall%26deviceId3%3D__IDFA__%26deviceId5%3D__GAID__%26adid%3D__UNIQUE_ID__%26partner%3D__PARTNER__%26ua%3D__UA__%26bundleId%3D__BUNDLE_ID__%26event%3D9%26mark%3Dadjust";
        String url = "https://app.adjust.com/13af6dvq&idfa=__IDFA__&gps_adid=__GAID__&install_callback=https%3A%2F%2Fsgp.digcto.com%2Fwinloss%2FforeignPostback%3Faction%3Dinstall%26deviceId3%3D__IDFA__%26deviceId5%3D__GAID__%26adid%3D__UNIQUE_ID__%26partner%3D__PARTNER__%26ua%3D__UA__%26bundleId%3D__BUNDLE_ID__%26event%3D9%26mark%3Dadjust";
//        String url = "https://app.adjust.com/13af6dvq&idfa=__IDFA__&gps_adid=__GAID__&install_callback=https%3A%2F%2Fsgp.digcto.com%2Fwinloss%2FforeignPostback%3Faction%3Dinstall%26deviceId3%3D__IDFA__%26deviceId5%3D__GAID__%26adid%3D__UNIQUE_ID__%26partner%3D__PARTNER__%26ua%3D__UA__%26bundleId%3D__BUNDLE_ID__%26event%3D9%26mark%3Dadjust";
//        String url = "https://app.adjust.com/13af6dvq/ahsjah/asa&a=b=c&idfa=__IDFA__&gps_adid=__GAID__&install_callback=https%3A%2F%2Fsgp.digcto.com%2Fwinloss%2FforeignPostback%3Faction%3Dinstall%26deviceId3%3D__IDFA__%26deviceId5%3D__GAID__%26adid%3D__UNIQUE_ID__%26partner%3D__PARTNER__%26ua%3D__UA__%26bundleId%3D__BUNDLE_ID__%26event%3D9%26mark%3Dadjust";
        UrlEntity entity = UrlUtil.parse(url);

        System.out.println(entity.getUrl());
        System.out.println(entity.getBaseUrl());

        System.out.println(entity.getParams().keySet());
        System.out.println(entity.getParams().values());

        UrlUtil.buildUrl(entity);
        System.out.println(entity.getUrl());
        System.out.println(UrlUtil.urlDecode(entity.getParams().get("install_callback")));

        //install_callback reattribution_callback conversion_callback session_callback event_callback_ rejected_install rejected_reattribution
        String subUrl = UrlUtil.urlDecode(entity.getParams().get("install_callback"));
        System.out.println(UrlUtil.urlEncode(subUrl));
        UrlEntity subEntity = UrlUtil.parse(subUrl);
        System.out.println(subEntity.getParams().get("action"));
        System.out.println(subEntity.getParams().get("event"));
    }


}
