package org.example.domain;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import lombok.extern.slf4j.Slf4j;

import java.net.SocketTimeoutException;

@Slf4j
public class AdexHystrixCommand extends HystrixCommand<Boolean> {
    private final String commandKey;
    private final Integer timeOut;



    public AdexHystrixCommand(String commandKey, Integer timeOut) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("groupKey"))
                .andCommandKey(HystrixCommandKey.Factory.asKey(commandKey))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        .withExecutionTimeoutInMilliseconds(timeOut))); // 这里配置熔断等的信息
        this.commandKey = commandKey;
        this.timeOut = timeOut;
    }

    @Override
    protected Boolean run() throws InterruptedException, SocketTimeoutException {
        log.info("commandKey:{}", commandKey);
        log.info("timeOut:{}", timeOut);
        Thread.sleep(4000L);
        log.info("run finished!");
        return true;
    }

    public static void main(String[] args) {
        try {
            Boolean s = new AdexHystrixCommand("test",5000).execute();
            System.out.println(s);
        } catch (HystrixRuntimeException e) {
            log.error("HystrixRuntimeException:{}",e.getMessage());
        } catch (Exception e) {
            log.error("Exception:",e);
        }

    }
}
