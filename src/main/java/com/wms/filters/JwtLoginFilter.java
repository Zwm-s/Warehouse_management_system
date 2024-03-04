package com.wms.filters;

import com.alibaba.fastjson.JSONObject;
import com.wms.entity.Result;
import com.wms.entity.User;
import com.wms.mapper.UserMapper;
import com.wms.utils.JwtUtil;
import com.wms.utils.StrRedisTemplateUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


/*
* 使用过滤器实现简单jwt登录验证
* */
@Slf4j
@Component
public class JwtLoginFilter extends OncePerRequestFilter {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StrRedisTemplateUtil strRedisTemplateUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //获取jwt
        String jwt = request.getHeader("Token");

        if(jwt==null){
            filterChain.doFilter(request, response);
            return;
        }

        log.info("拦截到一次请求");
        System.out.println("url:" + request.getRequestURI());
        System.out.println("method:" + request.getMethod());

        Map<String, Object> claims;
        try {
            claims = JwtUtil.parseJWT(jwt);
        } catch (Exception e) {
            log.info("令牌解析失败");
            Result result = Result.error("login error");
            //因为不是在controller之内，需要手动将返回信息封装到json格式之中
            String strResult = JSONObject.toJSONString(result);
            //将结果写入返回体之中
            response.getWriter().write(strResult);
            throw new RuntimeException(e);
        }

        //从Redis查找
        User user =  strRedisTemplateUtil.getUser((String) claims.get("number"));
        if(Objects.isNull(user)){
            Result result = Result.error("用户未登录");
            //因为不是在controller之内，需要手动将返回信息封装到json格式之中
            String strResult = JSONObject.toJSONString(result);
            //将结果写入返回体之中
            response.getWriter().write(strResult);
            throw new RuntimeException("用户未登录");
        }


        /*
        * 将User的信息提交到上下文中以通过其它过滤器验证
        * */

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user, null, null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);


        filterChain.doFilter(request, response);//放行
    }
}
