package com.wms.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wms.entity.Result;
import com.wms.entity.User;
import com.wms.mapper.UserMapper;
import com.wms.service.LoginService;
import com.wms.utils.JwtUtil;
import com.wms.utils.StrRedisTemplateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StrRedisTemplateUtil strRedisTemplateUtil;

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

        //存储到Redis内
        strRedisTemplateUtil.saveUser(loginuser.getNumber(),loginuser,2);

        //封装返回结果
        HashMap<String,String> map =new HashMap<>();
        map.put("token",jwt);
        map.put("name",loginuser.getName());
        map.put("id",loginuser.getId().toString());

        return Result.success(map);
    }

    @Override
    public void logout(Integer id) {
        User user = userMapper.findBydId(id);
        String user_number = user.getNumber();
        strRedisTemplateUtil.deleteValue(user_number);
    }

    @Override
    public boolean register(User user) {
        User user1= userMapper.findBydNum(user.getNumber());
        if(user1==null){
            userMapper.add(user);
            User user2= userMapper.findBydNum(user.getNumber());
            userMapper.saveImage(user2.getId(),"QCF");
            return true;
        }else{
            return false;
        }
    }


}
