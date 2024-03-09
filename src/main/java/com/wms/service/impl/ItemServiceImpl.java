package com.wms.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wms.entity.Item;
import com.wms.entity.PageBean;
import com.wms.entity.User;
import com.wms.mapper.ItemMapper;
import com.wms.service.ItemService;
import com.wms.utils.AliossUtil;
import com.wms.utils.LocalStorageUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    ItemMapper itemMapper;

    /*
    * 云端存储
    @Autowired
    AliossUtil aliossUtil;
    */

    @Autowired
    LocalStorageUtil localStorageUtil;

    @Override
    public void add(Item item, MultipartFile itemImage,HttpServletRequest request)  {
        //云端存储
        // String itemUrl = aliossUtil.upload(itemImage);
        String itemUrl =localStorageUtil.upload(itemImage,request);
        item.setImage(itemUrl);
        itemMapper.add(item);
    }

    @Override
    public void delete(Integer id)  {
        Item item = itemMapper.selectById(id);

        String image =item.getImage();
        //云端存储
        //String fileName = image.substring(image.lastIndexOf("/") + 1);
        //aliossUtil.deleteFile(fileName);
        //本地存储
        localStorageUtil.delete(image);

        itemMapper.delete(id);
    }

    @Override
    public void deleteByWhId(Integer whId)  {
        Item item =new Item();
        item.setWhId(whId);
        List<Item> itemList= itemMapper.selectSome(item);
        for (Item temp_item:itemList
             ) {
            String image =temp_item.getImage();
            //云端存储
            //String fileName = image.substring(image.lastIndexOf("/") + 1);
            //aliossUtil.deleteFile(fileName);
            //本地存储
            localStorageUtil.delete(image);
        }
        itemMapper.deleteByWhId(whId);
    }

    @Override
    public void mod(Item item, MultipartFile itemImage, HttpServletRequest request)  {
        //删除原来image
        Item index_item = itemMapper.selectById(item.getId());
        if(index_item.getImage()!=null) {
            String image = index_item.getImage();
            //云端存储
            //String fileName = image.substring(image.lastIndexOf("/") + 1);
            //aliossUtil.deleteFile(fileName);
            //本地存储
            localStorageUtil.delete(image);
        }
        //增加上传图片
        //云端
        // String itemUrl = aliossUtil.upload(itemImage);
        String itemUrl =localStorageUtil.upload(itemImage,request);
        item.setImage(itemUrl);
        //修改物品
        itemMapper.mod(item);
    }

    @Override
    public PageBean listpage(Integer page, Integer pageSize, String name,Integer whId) {
        PageHelper.startPage(page,pageSize);
        Item item = new Item();
        item.setName(name);
        item.setWhId(whId);
        List<Item> itemList = itemMapper.selectSome(item);
        Page<Item> p = (Page<Item>) itemList;
        return new PageBean(p.getTotal(),p.getResult());
    }

    @Override
    public void saveImage(String imageUrl,Integer id) {
        Item item=new Item();
        item.setImage(imageUrl);
        item.setId(id);
        itemMapper.mod(item);
    }
}
