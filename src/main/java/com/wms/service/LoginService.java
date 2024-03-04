package com.wms.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wms.entity.Result;
import com.wms.entity.User;

import java.net.http.HttpRequest;

public interface LoginService {

    Result login(User user) throws JsonProcessingException;

    void logout(Integer id);
}
