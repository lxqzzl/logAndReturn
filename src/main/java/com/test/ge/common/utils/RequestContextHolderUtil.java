package com.test.ge.common.utils;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 获取请求响应工具类
 *
 * @author lxq
 */

public class RequestContextHolderUtil {

    /**
     * 获取请求
     * @return HttpServletRequest
     */
    public static HttpServletRequest getRequest() {
        return getRequestAttributes().getRequest();
    }

    /**
     * 获取响应
     * @return HttpServletResponse
     */
    public static HttpServletResponse getResponse() {
        return getRequestAttributes().getResponse();
    }

    /**
     * 获取session
     * @return HttpSession
     */
    public static HttpSession getSession() {
        return getRequest().getSession();
    }

    /**
     * 获取请求的信息
     * @return ServletRequestAttributes
     */
    public static ServletRequestAttributes getRequestAttributes() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
    }

    /**
     * 获取域对象
     * @return ServletContext
     */
    public static ServletContext getServletContext() {
        return ContextLoader.getCurrentWebApplicationContext().getServletContext();
    }

}
