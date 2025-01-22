package com.example.global;

import com.example.utils.MDCUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * web全局链路追踪拦截器-实现全局traceId
 */
@Slf4j
public class TraceInterceptor implements AsyncHandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        //带参数的url
//        log.info("Received request URI: {} with parameters: {}", request.getRequestURI(), request.getParameterMap());
        // "traceId"
        String traceId = request.getHeader(MDCUtil.TRACE_ID);
        if (StringUtils.isBlank(traceId)) {
            traceId = MDCUtil.generateTraceId();
        }
        MDCUtil.setTraceId(traceId);
        return true;
    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                @Nullable Exception ex) throws Exception {
        MDCUtil.clear();
    }
}

