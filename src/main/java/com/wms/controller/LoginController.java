package com.wms.controller;

import com.wms.entity.Result;
import com.wms.entity.User;
import com.wms.service.UserService;
import com.wms.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    UserService userService;

    @PostMapping
    public Result login(@RequestBody User user){
        log.info("用户登录：{}",user);
        User e= userService.findByN(user.getNumber());
        if(e!=null&& Objects.equals(e.getPassword(), user.getPassword())){
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", e.getId());
            claims.put("name", e.getName());
            String jwt = JwtUtil.generateJwt(claims);

            HashMap<String,String> map =new HashMap<>();
            map.put("token",jwt);
            map.put("name",e.getName());
            return Result.success(map);
        }
        return Result.error("login error");
    }
}
