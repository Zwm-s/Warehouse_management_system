package com.wms.service.impl;

import com.wms.entity.Result;
import com.wms.entity.User;
import com.wms.mapper.UserMapper;
import com.wms.service.LoginService;
import com.wms.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public Result login(User user) {

        //验证
        User loginuser= userMapper.findBydNum(user.getNumber());

        if(Objects.isNull(loginuser)){
            return Result.error("用户名或者密码错误");
        }

        //构建jwt
        Map<String, Object> claims = new HashMap<>();
        claims.put("number", loginuser.getNumber());
        claims.put("password", loginuser.getName());

        String jwt = JwtUtil.generateJwt(claims);

        //封装jwt
        HashMap<String,String> map =new HashMap<>();
        map.put("token",jwt);
        map.put("name",loginuser.getName());

        return Result.success(map);
    }
}
