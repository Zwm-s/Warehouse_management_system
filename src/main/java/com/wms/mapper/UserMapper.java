package com.wms.mapper;

import com.wms.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/*
* User的mapper接口
* */
@Mapper
public interface UserMapper {

    @Select("select *from user")
    List<User> selectAll();

    @Insert("insert into user(number, name, password, age, sex, phone, role_id) VALUES (#{number}, #{name}, #{password}, #{age}, #{sex}, #{phone},#{roleId})")
    void add(User user);

    void mod(User user);

    void delete(Integer id);

    List<User> selectSome(User user);

    @Select("select *from user where id=#{id}")
    User findBydId(Integer id);

    @Select("select *from user where number=#{number}")
    User findBydN(String number);

    @Select("select *from user where name=#{name}")
    User findBydName(String name);
}
