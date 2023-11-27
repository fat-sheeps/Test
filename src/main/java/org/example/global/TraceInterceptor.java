package org.example.global;

import org.apache.commons.lang3.StringUtils;
import org.example.utils.MDCUtil;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * web全局链路追踪拦截器-实现全局traceId
 */
public class TraceInterceptor implements AsyncHandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
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

