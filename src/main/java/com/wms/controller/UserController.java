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

@Slf4j
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
        log.info("进行分页查询，参数：{} {} {} {} {}", page, pageSize, name, sex,age);
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

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        log.info("删除user id:{}",id);
        userService.delete(id);
        return Result.success();
    }
}
