package com.wms.controller;

import com.wms.entity.PageBean;
import com.wms.entity.Result;
import com.wms.entity.User;
import com.wms.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Insert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public Result List(){
        log.info("查询所有user");
        List<User> userList = userService.selectAll();
        return Result.success(userList);
    }

    @GetMapping("/findById")
    public Result findById(Integer id){
        log.info("根据id查询用户");
        User user = userService.findById(id);
        Optional<User> optionalUser = Optional.ofNullable(user);
        return optionalUser.isPresent()?Result.error("账号已存在"):Result.success();
    }

    @GetMapping("/findByNum")
    public Result findByNum(String number){
        log.info("根据账户查询用户");
        User user = userService.findByNum(number);
        Optional<User> optionalUser = Optional.ofNullable(user);
        return optionalUser.isPresent()?Result.error("账号已存在"):Result.success();
    }

    @GetMapping("/findByName")
    public Result findByName(String name){
        log.info("根据名称查询用户");
        User user = userService.findByName(name);
        return Result.success(user);
    }

    @PostMapping("/listn")
    public Result Listn(@RequestBody User user){
        log.info("模糊查询user:{}",user);
        List<User> userList = userService.selectAll(user);
        return Result.success(userList);
    }

    @GetMapping("/listp")
    public Result listpage(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "20") Integer pageSize,
                       String name, Integer sex,Integer age) {
        //@RequestParam设置默认参数值
        log.info("进行用户分页查询，参数：{} {} {} {} {}", page, pageSize, name, sex,age);
        PageBean pageBean = userService.listpage(page,pageSize,name,sex,age);
        return Result.success(pageBean);
    }

    @PostMapping("/add")
    public Result add(@RequestBody User user){
        log.info("新增user");
        userService.add(user);
        return Result.success();
    }

    @PostMapping("/mod")
    public Result mod(@RequestBody User user){
        log.info("修改员工: {}",user);
        userService.mod(user);
        return Result.success();
    }

    @DeleteMapping("/delete")
    public Result delete(Integer id){
        log.info("删除user id:{}",id);
        userService.delete(id);
        return Result.success();
    }

    @GetMapping("/findImageById")
    public Result findImageById(Integer id){
        log.info("更具用户id寻找头像：{}",id);
       String image= userService.findImageById(id);
        return Result.success(image);
    }

    @GetMapping("/saveImage")
    public Result saveImageById(Integer id,String image){
        log.info("根据用户id寻找头像：{}{}",id,image);
        userService.saveImageById(id,image);
        return Result.success();
    }

}
