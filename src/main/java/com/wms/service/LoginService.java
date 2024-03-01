package com.wms.service;

import com.wms.entity.Result;
import com.wms.entity.User;

public interface LoginService {

    Result login(User user);
}
