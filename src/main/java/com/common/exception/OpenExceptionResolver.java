package com.common.exception;

import com.alibaba.fastjson.JSON;
import com.common.bean.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 */
public class OpenExceptionResolver implements HandlerExceptionResolver {

    private static final Logger LOGGER = LoggerFactory.getLogger(OpenExceptionResolver.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        BusinessException BusinessException = getException(ex);
        LOGGER.error(String.format("HandlerExceptionResolver catch the exception -> %s", BusinessException.getErrorMsg()), ex);
        Result result = new Result(BusinessException.getErrorCode(), BusinessException.getErrorMsg(), Result.Status.ERROR);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        try {
            response.getWriter().println(JSON.toJSONString(result));
        } catch (IOException e) {
            LOGGER.error("Response write exception", e);
        }
        return new ModelAndView();
    }

    private BusinessException getException(Throwable ex) {
        BusinessException BusinessException = null;
        if (ex instanceof IllegalArgumentException || ex instanceof BindException) {
            BusinessException = new BusinessException(OpenErrorCode.PARAM_INVALID.getErrorCode(), OpenErrorCode.PARAM_INVALID.getErrorMsg(), ex.getMessage());
        } else if (ex instanceof BusinessException) {
            BusinessException = (BusinessException) ex;
        } else {
            BusinessException = new BusinessException(OpenErrorCode.SYSTEM_ERROR.getErrorCode(), OpenErrorCode.SYSTEM_ERROR.getErrorMsg());
        }
        return BusinessException;
    }

}
