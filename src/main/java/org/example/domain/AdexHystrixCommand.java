package org.example.domain;

import cn.hutool.core.util.RandomUtil;
import com.netflix.hystrix.*;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import lombok.extern.slf4j.Slf4j;

import java.net.SocketTimeoutException;

@Slf4j
public class AdexHystrixCommand extends HystrixCommand<String> {
    private final String groupKey;
    private final String commandKey;
    private final Integer timeOut;



    public AdexHystrixCommand(String groupKey, String commandKey, Integer timeOut) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(groupKey))
                .andCommandKey(HystrixCommandKey.Factory.asKey(commandKey))
                //线程池属性配置
                //.andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter().withCoreSize(225).withMaxQueueSize(225))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        .withExecutionTimeoutInMilliseconds(timeOut))); // 这里配置熔断等的信息
        this.groupKey = groupKey;
        this.commandKey = commandKey;
        this.timeOut = timeOut;
    }

    @Override
    protected String run() throws InterruptedException, SocketTimeoutException {
//        log.info("run begin");
//        log.info("groupKey:{}", groupKey);
//        log.info("commandKey:{}", commandKey);
        int t = RandomUtil.randomInt(0, 2000);
//        int t = 200;
//        log.info("业务耗时:{}",t);
        Thread.sleep(t);
//        log.info("run finished!");
        return t + "";
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        long end = 0;
        try {
            String s = new AdexHystrixCommand("test",null,3000).execute();
            end = System.currentTimeMillis();
        } catch (HystrixRuntimeException e) {
            end = System.currentTimeMillis();
            log.error("HystrixRuntimeException:{}",e.getMessage());
        } catch (Exception e) {
            end = System.currentTimeMillis();
            log.error("Exception:",e);
        } finally {
            //end = System.currentTimeMillis();
            log.info("timeout:{}", end - start);
        }


    }
}
