package com.wms.filters;

import com.alibaba.fastjson.JSONObject;
import com.wms.entity.Result;
import com.wms.utils.JwtUtil;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Slf4j
//@WebFilter(urlPatterns = "/*")//拦截路径
public class LoginFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        log.info("拦截到一次请求，拦截之前的操作");
        //转换类型获取请求信息
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse =(HttpServletResponse) servletResponse;
        //获取url
        String url = httpRequest.getRequestURI();
        //如果是登录请求就放行
        if(url.contains("/login")){
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        //获取jwt
        String jwt = httpRequest.getHeader("token");
        if(!StringUtils.hasLength(jwt)){
            Result result = Result.error("login error");

            String strResult = JSONObject.toJSONString(result);

            httpResponse.getWriter().write(strResult);
            return;
        }

        try {
            JwtUtil.parseJWT(jwt);
        } catch (Exception e) {
            log.info("令牌解析失败");
            Result result = Result.error("login error");
            //因为不是在controller之内，需要手动将返回信息封装到json格式之中
            String strResult = JSONObject.toJSONString(result);
            //将结果写入返回体之中
            httpResponse.getWriter().write(strResult);
            throw new RuntimeException(e);
        }

        filterChain.doFilter(servletRequest,servletResponse);//放行

    }
}
