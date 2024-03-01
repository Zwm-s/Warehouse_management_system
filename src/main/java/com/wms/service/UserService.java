package com.wms.service;

import com.wms.entity.PageBean;
import com.wms.entity.User;

import java.util.List;

/*
* UserService接口
* */
public interface UserService {



    /*
    * 查询所有用户
    * */
    List<User> selectAll();

    /*
    * 模糊查询用户
    * */
    List<User> selectAll(User user);

    /*
    * 新增用户
    * */
    void add(User user);

    /*
    * 更新用户
    * */
    void mod(User user);

    /*
    * 根据id删除员工
    * */
    void delete(Integer id);

    /*
    * 分页模糊查询用户
    * */
    PageBean listpage(Integer page, Integer pageSize, String name, Integer sex,Integer age);

    /*
    * 根据id查询用户
    * */
    User findById(Integer id);

    /*
    * 根据number查询用户
    * */
    User findByNum(String number);

    /*
    * 根据名字查找用户
    * */
    User findByName(String name);
}
