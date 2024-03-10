package com.wms.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wms.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/*
* 存储Redis工具类
* */
@Slf4j
@Component
public class StrRedisTemplateUtil {
    //导入StringRedisTemplate
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    //用于从对象到json的转换
    private static final ObjectMapper mapper = new ObjectMapper();

    //过期时间单位
    private static final TimeUnit timeUnit=TimeUnit.HOURS;

    public void saveValue(String key, String value, Integer timeout){
        stringRedisTemplate.opsForValue().set(key, value);
        try {
            if(timeout>0){
                stringRedisTemplate.expire(key,(long) timeout,timeUnit);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void saveUser(String key, User user,Integer timeout)  {

        //转换为json字符串
        String userJson = null;
        try {
            userJson = mapper.writeValueAsString(user);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        //存储到Redis中
        stringRedisTemplate.opsForValue().set(key, userJson);
        try {
            if(timeout>0){
                stringRedisTemplate.expire(key,(long) timeout,timeUnit);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public User getUser(String key) throws JsonProcessingException {
        String userJson= stringRedisTemplate.opsForValue().get(key);
        return mapper.readValue(userJson, User.class);
    }

    public String getValue(String key){
        return stringRedisTemplate.opsForValue().get(key);
    }

    public void deleteValue(String key){
        stringRedisTemplate.delete(key);
    }

}
