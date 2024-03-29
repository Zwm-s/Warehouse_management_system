package com.wms.controller;

import com.wms.entity.Item;
import com.wms.entity.PageBean;
import com.wms.entity.Result;
import com.wms.service.ItemService;
import com.wms.utils.AliossUtil;
import com.wms.utils.LocalStorageUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    /*
    * 云端存储
    @Autowired
    AliossUtil aliossUtil;
    */

    @Autowired
    private LocalStorageUtil localStorageUtil;

    @PostMapping("/add")
    public Result add(@RequestParam("itemImage") MultipartFile itemImage,
                      @RequestParam("whId") Integer whId,
                      @RequestParam("name") String name,
                      @RequestParam("instruction") String instruction,
                      HttpServletRequest request
    ) throws IOException {
        Item item =new Item();
        item.setWhId(whId);
        item.setName(name);
        item.setInstruction(instruction);
        log.info("新增Item:{}",item);
        itemService.add(item,itemImage,request);
        return Result.success();
    }

    @DeleteMapping("/delete")
    public Result delete(Integer id){
        log.info("删除Item:{}",id);
        itemService.delete(id);
        return Result.success();
    }

    /*
    * 测试删除云端文件
    @DeleteMapping("/deletewh")
    public Result deletewh(Integer id){
        log.info("删除Item:{}",id);
        itemService.deleteByWhId(id);
        return Result.success();
    }*/

    @PostMapping("/mod")
    public Result mod(@RequestParam("itemImage") MultipartFile itemImage,
                      @RequestParam("whId") Integer whId,
                      @RequestParam("name") String name,
                      @RequestParam("instruction") String instruction,
                      @RequestParam("id") Integer id,
                      HttpServletRequest request
    ) throws IOException {
        Item item =new Item();
        item.setId(id);
        item.setWhId(whId);
        item.setName(name);
        item.setInstruction(instruction);
        log.info("更新Item:{}",item);
        itemService.mod(item,itemImage,request);
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

    @PostMapping("/uploadImage")
    public Result uploadIMage(@RequestParam("image") MultipartFile itemImage,
                              @RequestParam("id") Integer id,
                              HttpServletRequest request) throws IOException {
        //云端存储
        // String imageUrl=  aliossUtil.upload(itemImage);
        //本地存储
        String imageUrl=localStorageUtil.upload(itemImage,request);
        log.info("上传item图片：{},{}",imageUrl,id);
        itemService.saveImage(imageUrl,id);
        return Result.success(imageUrl);
    }
}
