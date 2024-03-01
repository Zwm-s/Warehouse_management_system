package com.wms.controller;

import com.wms.entity.Result;
import com.wms.entity.User;
import com.wms.service.LoginService;
import com.wms.service.UserService;
import com.wms.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.loader.ResultLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@RestController
@CrossOrigin
public class LoginController {

    @Autowired
    LoginService loginService;

    /*
     * 登录控制层
     * */
    @PostMapping("/login")
    public Result login(@RequestBody User user){
        log.info("用户登录：{}",user);
        return loginService.login(user);
    }

    @PostMapping("/logout")
    public Result logout(){
        return Result.success();
    }
}
