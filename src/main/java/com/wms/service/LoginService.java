package com.wms.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wms.entity.Result;
import com.wms.entity.User;

import java.net.http.HttpRequest;

public interface LoginService {

    /*
    * 登录
    * */
    Result login(User user);

    /*
    * 登出
    * */
    void logout(Integer id);

    /*
    * 注册用户
    * */
    boolean register(User user);
}
