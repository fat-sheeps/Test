package org.example.config;

import lombok.extern.slf4j.Slf4j;
import org.example.global.TraceInterceptor;
import org.example.utils.IPUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.annotation.PostConstruct;

@Configuration
@Slf4j
public class WebHandlerConfig extends WebMvcConfigurationSupport {

    @Autowired
    private Environment environment;


    /**
     * traceId 拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TraceInterceptor())
                .addPathPatterns("/**").excludePathPatterns("/error/page/**");
    }
    /* 请求下游http接口时塞入traceId
    public void httpInterceptors(){
        // 以下省略其他相关配置
        RestTemplate restTemplate = new RestTemplate();
        // 使用拦截器包装http header
        restTemplate.setInterceptors(new ArrayList<ClientHttpRequestInterceptor>() {
            {
                add((request, body, execution) -> {
                    String traceId = MDCUtil.getTraceId();
                    if (StringUtils.isNotEmpty(traceId)) {
                        request.getHeaders().add(MDCUtil.TRACE_ID, traceId);
                    }
                    return execution.execute(request, body);
                });
            }
        });

        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        // 注意此处需开启缓存，否则会报getBodyInternal方法“getBody not supported”错误
        factory.setBufferRequestBody(true);
        restTemplate.setRequestFactory(factory);
    }*/


    @PostConstruct
    public void init() {
        IPUtil.setPort(environment.getProperty("server.port"));
        log.error("init!");
    }
}