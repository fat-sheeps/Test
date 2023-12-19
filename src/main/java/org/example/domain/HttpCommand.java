package org.example.domain;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
@Slf4j
public class HttpCommand extends HystrixCommand<String> {
    private final String url;
    private final int t;

    protected HttpCommand(String url, int t) {
        super(Setter
                .withGroupKey(HystrixCommandGroupKey.Factory.asKey("HttpGroup"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("HttpCommand"))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        .withExecutionTimeoutInMilliseconds(1000)));

        this.url = url;
        this.t = t;
    }

    @Override
    protected String run() throws Exception {
        // 这里执行实际的HTTP请求，返回响应结果
        // 你可以使用HttpClient、HttpURLConnection等方式执行HTTP请求
        // 这里简化为休眠来模拟不同响应时间
        Thread.sleep(t);
        return "Response from " + url + ":" + t;
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        int n = 50; // 假设有50个HTTP接口
        ExecutorService executorService = Executors.newFixedThreadPool(n);
        List<Future<String>> futures = new ArrayList<>();

        List<String> successfulResponses = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            int t = ThreadLocalRandom.current().nextInt(100, 3000);
            String url = "http:" + i + " t:" + t;
            if (t > 1000) {
                log.error("url:{}", url);
            } else {
                log.info("url:{}", url);
            }
            HttpCommand httpCommand = new HttpCommand(url, t);
            Future<String> future = executorService.submit(() -> {
                try {
                    return httpCommand.execute();
                } catch (Exception e) {
                    // HystrixCommand执行异常，可能是超时或其他错误
                    return null;
                }
            });
            futures.add(future);
        }

        for (Future<String> future : futures) {
            try {
                String result = future.get(1000, TimeUnit.MILLISECONDS);
                if (result != null) {
                    successfulResponses.add(result);
                }
            } catch (TimeoutException e) {
                // 超时异常，可以选择取消任务
                future.cancel(true);
            }
        }

        executorService.shutdown();
        System.out.println("Successful responses within 150 milliseconds: " + successfulResponses);
    }
}
