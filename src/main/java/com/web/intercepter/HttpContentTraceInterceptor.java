package com.web.intercepter;

import com.util.web.HttpPackageUtil;
import com.util.web.WriteLogUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class HttpContentTraceInterceptor implements HandlerInterceptor {

    private final Logger httpLogger = LoggerFactory.getLogger("com.httpDetailLog");

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        if (httpLogger.isDebugEnabled() && handler instanceof HandlerMethod) {
            // record the entire Http Request including header/parameter/body.
            this.printHttpHeader(request);
        }

        return true;
    }
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    private void printHttpHeader(HttpServletRequest request) {
        StringBuilder data = new StringBuilder();
        data.append("\n");
        data.append(HttpPackageUtil.assembleHttpData(request));
        WriteLogUtils.addLog(data.toString(), httpLogger);
    }
}
