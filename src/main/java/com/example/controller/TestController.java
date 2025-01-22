package com.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
@RestController()
@RequestMapping("")
@Slf4j
public class TestController {

    private final Map<String, String> countMap = new HashMap<>();

//    一、 请编写代码
//    补全两个问题的代码编写，且找出不合理的地方进行代码优化(要考虑到代码健壮性、可读性，高并发场景的安全性及执行效率)，合适的地方加上注释，请随意发挥。
//    问题1：ip的值从请求过来只会有如上两种情况(&ip=或者&ip=__IP__)，将queryStr中ip的值都赋值为固定IP(110.1.3.4)，并打印queryStr。需考虑此接口请求量级较大
//    问题2：定义变量m2Chn赋值为m2的值加上固定字符串'_chn'(考虑m2若是null，当为空字符串）；
    /**
     *
     * @return 三种值从Map获取计数值后，加起来是否超过规定值。
     * @param fcNum 规定值
     *
     * http://xxx/imp/test?action=imp&ip=&m2=imei&m5=idfa1";
     * http://xxx/imp/test?action=imp&ip=__IP__&m2=&m5=idfa2";
     *
     */
    @RequestMapping("/imp/test")
    public boolean impTest(HttpServletRequest request, @RequestParam int fcNum) {
        String queryStr = request.getQueryString();

        int totalCount = 0;

        String m2 = request.getParameter("m2");
        String m5 = request.getParameter("m5");
        boolean isOverCount = false;

//        try {
//            int m2Count = Integer.parseInt(countMap.get("m2_" + m2));
//            countMap.put("m2_" + m5, String.valueOf(m2Count + 1));
//            totalCount += m2Count;;
//            isOverCount = totalCount >= fcNum;
//
//            if (!isOverCount) {
//                int m2ChnCount = Integer.parseInt(countMap.get("m2Chn_" + m2Chn));
//                countMap.put("m2Chn_" + m5, String.valueOf(m2ChnCount + 1));
//                totalCount += m2ChnCount;
//                isOverCount = totalCount >= fcNum;
//            }
//
//            if (!isOverCount) {
//                int m5Count = Integer.parseInt(countMap.get("m5_" + m5));
//                countMap.put("m5_" + m5, String.valueOf(m5Count + 1));
//                totalCount += m5Count;
//                isOverCount = totalCount >= fcNum;
//            }
//
//        } catch (Exception e) {
//            log.error("error get fc count: " + e);
//        }

        return isOverCount;
    }


}
