package com.wms.controller;

import com.wms.entity.Item;
import com.wms.entity.PageBean;
import com.wms.entity.Result;
import com.wms.service.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @PostMapping("/add")
    public Result add(@RequestBody Item item){
        log.info("新增Item:{}",item);
        itemService.add(item);
        return Result.success();
    }

    @DeleteMapping("/delete")
    public Result delete(Integer id){
        log.info("删除Item:{}",id);
        itemService.delete(id);
        return Result.success();
    }

    @PostMapping("/mod")
    public Result mod(@RequestBody Item item){
        log.info("更新Item:{}",item);
        itemService.mod(item);
        return Result.success();
    }

    @GetMapping("/listp")
    public Result listpage(@RequestParam(defaultValue = "1") Integer page,
                           @RequestParam(defaultValue = "40") Integer pageSize,
                           String name,Integer whId) {
        //@RequestParam设置默认参数值
        log.info("进行物品分页查询，参数：{} {} {} {}", page, pageSize, name,whId);
        PageBean pageBean = itemService.listpage(page,pageSize,name,whId);
        return Result.success(pageBean);
    }
}
