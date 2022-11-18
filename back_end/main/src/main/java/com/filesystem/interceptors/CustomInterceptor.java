package com.filesystem.interceptors;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class CustomInterceptor implements HandlerInterceptor {

    private static final Logger LOG = LogManager.getLogger(CustomInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        LOG.info("================= REQUEST ==============");
        LOG.info("request time -> [{}]", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss.SSS"));
        request.getHeaderNames().asIterator().forEachRemaining((headerName) -> {
            LOG.info("request -> header: [{}] value -> [{}]", headerName, request.getHeader(headerName));
        });
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            @Nullable ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
            @Nullable Exception ex) throws Exception {
        response.getHeaderNames().forEach(headerName -> {
            LOG.info("response -> header: [{}] value -> [{}]", headerName, response.getHeader(headerName));
        });
        LOG.info("request end time -> [{}]", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss.SSS"));
        LOG.info("================= REQUEST END ==============");
    }
}
