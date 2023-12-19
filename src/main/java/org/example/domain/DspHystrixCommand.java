package org.example.domain;

import com.netflix.hystrix.*;
import lombok.extern.slf4j.Slf4j;

import java.net.SocketTimeoutException;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class DspHystrixCommand extends HystrixCommand<String> {
    private final String groupKey;

    private final String commandKey;

    private final Integer timeOut;

    private final Integer t;


    public final static ConcurrentHashMap<String, DspHystrixCommand> dspHystrixCommandMap = new ConcurrentHashMap<>();

    public DspHystrixCommand(String groupKey, String commandKey, Integer timeOut) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(groupKey))
                .andCommandKey(HystrixCommandKey.Factory.asKey(commandKey))
                //线程池属性配置
                .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter().withCoreSize(225).withMaxQueueSize(225))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        .withExecutionTimeoutInMilliseconds(timeOut))); // 这里配置熔断等的信息
        this.groupKey = groupKey;
        this.commandKey = commandKey;
        this.timeOut = timeOut;
        this.t = null;

    }

    public DspHystrixCommand(String groupKey, String commandKey, Integer timeOut, int t) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(groupKey))
                .andCommandKey(HystrixCommandKey.Factory.asKey(commandKey))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        .withCircuitBreakerRequestVolumeThreshold(10000000)// 太低的话short-circuited and no fallback available.
                        .withCircuitBreakerErrorThresholdPercentage(99)// 太低的话short-circuited and no fallback available.
                        .withExecutionTimeoutInMilliseconds(timeOut)
                )
                .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter()
                        .withCoreSize(225)
                        //线程池属性配置 225-460qps 执行耗时1s
                        .withMaximumSize(225)//控制 could not be queued for execution and no fallback available.
                        .withMaxQueueSize(225)//控制 could not be queued for execution and no fallback available.
                        .withQueueSizeRejectionThreshold(550)
                ));
        this.groupKey = groupKey;
        this.commandKey = commandKey;
        this.timeOut = timeOut;
        this.t = t;
    }
    @Override
    protected String run() throws InterruptedException, SocketTimeoutException {
        log.info("run{}",t);
        Thread.sleep(t);
        log.info("run finished!{}",t);
        return t + "";
    }

    /**
     * 是否存在
     */
    /*private static DspHystrixCommand findDspHystrixCommand(String groupKey, String commandKey, Integer timeOut) {
        for (Map.Entry<String, DspHystrixCommand> entry : dspHystrixCommandMap.entrySet()) {
            if (entry.getKey().equals(groupKey + commandKey + timeOut)) {
                return entry.getValue();
            }
        }
        return null;
    }*/



    /*public static void main(String[] args) {
        long start = System.currentTimeMillis();
        long end = 0;
        try {
            String s = DspHystrixCommand.getDspHystrixCommand("test",null,3000, 1000).execute();
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


    }*/
}
