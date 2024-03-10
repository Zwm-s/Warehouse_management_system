package com.wms.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wms.entity.Result;
import com.wms.entity.User;
import com.wms.service.LoginService;
import com.wms.service.UserService;
import com.wms.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.loader.ResultLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@RestController
@CrossOrigin
public class LoginController {

    @Autowired
    LoginService loginService;

    @Autowired
    UserService userService;

    /*
     * 登录控制层
     * */
    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        log.info("用户登录：{}",user);
        return loginService.login(user);
    }

    @GetMapping("/logout")
    public Result logout(Integer id){
        log.info("用户退出:{}",id);
        loginService.logout(id);
        return Result.success();
    }

    @PostMapping("/register")
    public Result register(@RequestBody User user){

        log.info("用户注册：{}",user);
        if(loginService.register(user)){
            return Result.success();
        }else{
            return Result.error("注册失败，用户已存在");
        }
    }
}
