package com.exception;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HandlerExceptionResolver extends DefaultHandlerExceptionResolver {
    private static final Logger log = LoggerFactory.getLogger(HandlerExceptionResolver.class);

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request,
                                              HttpServletResponse response, Object handler, Exception ex) {

        try {
            if (ex instanceof ServletRequestBindingException
                    || ex instanceof TypeMismatchException
                    || ex instanceof HttpMessageNotReadableException
                    || ex instanceof MethodArgumentNotValidException
                    || ex instanceof MissingServletRequestPartException
                    || ex instanceof BindException || ex instanceof JSONException) {
                return as400Error(ex, response);
            } else {
                return as500Error(ex, response);
            }
        } catch (Exception handlerException) {
            log.warn("Handling of [" + ex.getClass().getName() + "] resulted in Exception",
                    handlerException);
        }

        return null;
    }

    protected ModelAndView as400Error(Exception ex, HttpServletResponse response)
            throws IOException {

        String retStr = JSON.toJSONString(ex);
        log.warn(retStr);

        response.setContentType("application/json;charset=UTF-8");
        printError(response, retStr);

        return new ModelAndView();
    }

    protected ModelAndView asServiceError(ServiceException ex, HttpServletResponse response)
            throws IOException {

//        String retStr = JSON.toJSONString(ResponseBuilder.baseResponse(ex.getCode(), ex.getMessage()));
//        log.warn(retStr, ex);

        response.setContentType("application/json;charset=UTF-8");
//        printError(response, retStr);

        return new ModelAndView();
    }

    protected ModelAndView as500Error(Exception ex, HttpServletResponse response)
            throws IOException {

//        String retStr = JSON.toJSONString(ResponseBuilder.baseResponse(500, ex.getMessage()));
//        log.error(retStr, ex);

        response.setContentType("application/json;charset=UTF-8");
//        printError(response, retStr);

        return new ModelAndView();
    }

    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE;
    }

    /**
     * 有一些可能使用了OutputStream()，所以这个时候不能再使用Writer()
     * @param response　response
     * @param errorInfo errorInfo
     * @throws IOException
     */
    private void printError(HttpServletResponse response, String errorInfo) throws IOException{
        try {
            response.getOutputStream().print(errorInfo);
        }catch (IllegalStateException ex) {

            log.error(ex.getMessage());
            response.getWriter().print(errorInfo);
        }
    }
}
