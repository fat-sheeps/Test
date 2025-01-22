package com.example.config;

import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class CorsFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 可以在这里进行初始化操作，如读取配置
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        // 或者指定特定的域名
        // httpServletResponse.setHeader("Access-Control-Allow-Origin", "http://example.com");

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // 可以在这里进行资源清理操作
    }
}