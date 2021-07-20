package com.liucj.as.api.config;

import com.liucj.as.api.hiconfig.CacheManager;
import com.liucj.as.api.service.HiConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Component
public class CommonInterceptor implements HandlerInterceptor {
    @Autowired
    private HiConfigService configService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (CacheManager.getInstance().needRefreshConfig) {
            configService.getAllConfig();
        }
        //不能拦截这次请求
        return true;
    }
}
