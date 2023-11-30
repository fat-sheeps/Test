package org.example.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;

import java.io.IOException;

@Slf4j
public class HttpClientUtil {


    private static final PoolingHttpClientConnectionManager poolingHttpClientConnectionManagerDsp = new PoolingHttpClientConnectionManager();
    private static final PoolingHttpClientConnectionManager poolingHttpClientConnectionManagerInner = new PoolingHttpClientConnectionManager();
    static {
        poolingHttpClientConnectionManagerDsp.setMaxTotal(3000);
        poolingHttpClientConnectionManagerDsp.setDefaultMaxPerRoute(1000);
    }
    private static final ConnectionKeepAliveStrategy connectionKeepAliveStrategy = (httpResponse, httpcontext) -> {
        HeaderElementIterator headerElementIterator = new BasicHeaderElementIterator(httpResponse.headerIterator(HTTP.CONN_KEEP_ALIVE));
        while (headerElementIterator.hasNext()) {
            HeaderElement headerElement = headerElementIterator.nextElement();
            String name = headerElement.getName();
            String value = headerElement.getValue();
            if ("timeout".equalsIgnoreCase(name)) {
                return Long.parseLong(value) * 1000;
            }
        }

        return 60 * 1000;
    };
    private static final RequestConfig.Builder requestConfigBuilder = RequestConfig.custom()
            .setConnectionRequestTimeout(5)
            .setConnectTimeout(5000);
            //定义了通过网络与服务器建立注接的超时时间，tpl1ent包中通过一个异步线程去创建与服务器的S0Kt连接，这就是该50Kt连接的超时时间。单位:毫秒当连接HTP服务器或者等待比.setConnectTimeout(5000)定义了从ConnectionManager管理的连接池中取出连接的超时时间。单位: 毫秒。出错会抛出onnectionPoolTimeoutException.setConnectionRequestTimeout(5)./定义了Socket读数据的超时时间，即从服务器获取响应数据需要等待的时间。单位: 毫秒。当读取或者接收SOCket超时会抛出SoCketTime0utExcepti0nsetSocketTimeout(60000):
    private static final RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectionRequestTimeout(5)
                    .setConnectTimeout(5000)
                    .setSocketTimeout(5000)
                    .build();
    private final static HttpClient httpClientDsp = HttpClients.custom()
            .setConnectionManager(poolingHttpClientConnectionManagerDsp)
            .setKeepAliveStrategy(connectionKeepAliveStrategy).build();

    public static void main(String[] args) throws IOException {
        //String result = doPost("http://localhost:8888/server", null);
//        String result2 = doPost("http://localhost:8888/server", null);
//        String result3 = doPost("http://localhost:8888/server", null);
        HttpPost httpPost = new HttpPost("http://localhost:8888/server");
        httpPost.setConfig(requestConfigBuilder.setSocketTimeout(5000).build());
        httpPost.setHeader("Content-Type", "application/json;charset=utf-8");
        httpPost.setEntity(new ByteArrayEntity(new byte[1]));
        ResponseHandler<?> responseHandler = (ResponseHandler<Object>) httpResponse -> {
            //log.info(httpResponse.getEntity().getContent());
            return (httpResponse.getEntity().getContent());
        };
        Object result = httpClientDsp.execute(httpPost, responseHandler);
        System.out.println(result);
    }

}
