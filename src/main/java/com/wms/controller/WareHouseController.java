package com.wms.controller;

import com.wms.entity.Item;
import com.wms.entity.PageBean;
import com.wms.entity.Result;
import com.wms.entity.WareHouse;
import com.wms.service.WareHouseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/wh")
public class WareHouseController {

    @Autowired
    WareHouseService wareHouseService;

    @PostMapping("/add")
    public Result add(@RequestBody WareHouse wareHouse){
        log.info("新增warehouse{}",wareHouse);
        wareHouseService.add(wareHouse);
        return Result.success();
    }

    @DeleteMapping("/delete")
    public Result delete(Integer id){
        log.info("删除WareHouse:{}",id);
        wareHouseService.delete(id);
        return Result.success();
    }

    @PostMapping("/mod")
    public Result mod(@RequestBody WareHouse wareHouse){
        log.info("更新wareHouse:{}",wareHouse);
        wareHouseService.mod(wareHouse);
        return Result.success();
    }

    @GetMapping("/listp")
    public Result listpage(@RequestParam(defaultValue = "1") Integer page,
                           @RequestParam(defaultValue = "20") Integer pageSize,
                           String name,Integer userId) {
        //@RequestParam设置默认参数值
        log.info("进行分页查询，参数：{} {} {} {}", page, pageSize, name,userId);
        PageBean pageBean = wareHouseService.listpage(page,pageSize,name,userId);
        return Result.success(pageBean);
    }

}
