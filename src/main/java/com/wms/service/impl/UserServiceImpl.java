package com.wms.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wms.entity.PageBean;
import com.wms.entity.User;
import com.wms.mapper.UserMapper;
import com.wms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/*
* UserService实现类
* */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> selectAll() {
        return userMapper.selectAll();
    }

    @Override
    public List<User> selectAll(User user) {
        return userMapper.selectSome(user);
    }

    @Override
    public void add(User user) {
        userMapper.add(user);
    }

    @Override
    public void mod(User user) {
        userMapper.mod(user);
    }

    @Override
    public void delete(Integer id) {
        userMapper.delete(id);
    }

    @Override
    public PageBean listpage(Integer page, Integer pageSize, String name, Integer sex,Integer age) {
        PageHelper.startPage(page,pageSize);
        User user = new User();
        user.setName(name);
        user.setSex(sex);
        user.setAge(age);
        List<User> userList = userMapper.selectSome(user);
        Page<User> p = (Page<User>) userList;
        return new PageBean(p.getTotal(),p.getResult());
    }

    @Override
    public User findById(Integer id) {
       return userMapper.findBydId(id);
    }

    @Override
    public User findByNum(String number) {
        return userMapper.findBydNum(number);
    }

    @Override
    public User findByName(String name) {
        return userMapper.findBydName(name);
    }

    @Override
    public String findImageById(Integer id) {
        return userMapper.findImage(id);
    }

    @Override
    public void saveImageById(Integer id,String image) {
        String str_image =userMapper.findImage(id);
        if(Objects.isNull(str_image)){
            userMapper.saveImage(id,image);
        }else{
            userMapper.updateImage(id,image);
        }

    }
}
