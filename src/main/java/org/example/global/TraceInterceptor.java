package org.example.global;

import org.example.utils.MDCUtil;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局链路追踪拦截器
 */
public class TraceInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // "traceId"
        String traceId = request.getHeader(MDCUtil.TRACE_ID);
        if (StringUtils.isEmpty(traceId)) {
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

