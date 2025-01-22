package com.example;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class BudgetController {
    //假设一个接口按调用次数收费，每次花费1元。用户购买这个接口每天付费10000元。理论上每天只能调用10000次。如何将用户的预算做到匀速消耗，即将用户成功调用的接口均匀的分散在每天的24小时中。需注意的是，用户请求的qps不稳定，可能白天qps比较大，夜间qps比较小。不能将预算在白天消耗完。写一个java的算法

    //当日可调用次数
    private final static int totalSpend = 10000;
    //当天剩余分钟数,分成分钟窗格
    private final static long minute = ChronoUnit.MINUTES.between(LocalDateTime.now(), LocalDateTime.now().plusDays(1).withHour(0).withMinute(0).withSecond(0));
    //当天已经消耗的次数
    private static int dailySpend = 0;
    //当前分钟消耗的次数
    private final static MinuteValue curMinuteSpend = new MinuteValue();

    private static boolean canInvoke() {

        //如果到下一分钟，重置当前分钟消耗次数
        if (LocalDateTime.now().getMinute() == curMinuteSpend.getMinute().getMinute()) {
            curMinuteSpend.setValue(curMinuteSpend.getValue() + 1);
        } else {
            curMinuteSpend.setMinute(LocalDateTime.now());
            curMinuteSpend.setValue(0);
        }
//        log.info("当前分钟数：{},当前分钟消耗次数：{}", curMinuteSpend.getMinute(), curMinuteSpend.getValue());
        //每分钟可调用次数
        int perMinute = (totalSpend - dailySpend) / (int) minute;
        if (curMinuteSpend.getValue() >= perMinute) {
            return false;
        }
        return true;
    }
    public static void main(String[] args) throws InterruptedException {

        //当日调用总次数
        long num = ChronoUnit.SECONDS.between(LocalDateTime.now(), LocalDateTime.now().plusDays(1).withHour(0).withMinute(0).withSecond(0));
        System.out.println(num);
        for (int i = 0; i < num; i++) {
            Thread.sleep(1000L);
            if (canInvoke()) {
                dailySpend ++;
                // 这里可以放置实际调用接口的逻辑代码，此处只是简单打印表示调用成功
                log.info("接口调用成功，第 " + (i + 1) + " 次调用");
            } else {
                log.info("受限，第" + (i + 1) + " 次调用");
            }
        }


    }





}
