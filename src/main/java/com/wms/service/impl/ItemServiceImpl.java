package com.wms.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wms.entity.Item;
import com.wms.entity.PageBean;
import com.wms.entity.User;
import com.wms.mapper.ItemMapper;
import com.wms.service.ItemService;
import com.wms.utils.AliossUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    ItemMapper itemMapper;

    @Autowired
    AliossUtil aliossUtil;

    @Override
    public void add(Item item) {
        itemMapper.add(item);
    }

    @Override
    public void delete(Integer id) {
        Item item = itemMapper.selectById(id);

        String image =item.getImage();
        String fileName = image.substring(image.lastIndexOf("/") + 1);
        aliossUtil.deleteFile(fileName);

        itemMapper.delete(id);
    }

    @Override
    public void deleteByWhId(Integer whId) {
        Item item =new Item();
        item.setWhId(whId);
        List<Item> itemList= itemMapper.selectSome(item);
        for (Item temp_item:itemList
             ) {
            String image =temp_item.getImage();
            String fileName = image.substring(image.lastIndexOf("/") + 1);
            aliossUtil.deleteFile(fileName);
        }
        itemMapper.deleteByWhId(whId);
    }

    @Override
    public void mod(Item item) {
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
