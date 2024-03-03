package com.wms.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wms.entity.Item;
import com.wms.entity.PageBean;
import com.wms.entity.WareHouse;
import com.wms.mapper.WareHouseMapper;
import com.wms.service.WareHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WareHouseServiceImpl implements WareHouseService {

    @Autowired
    WareHouseMapper wareHouseMapper;

    @Override
    public void add(WareHouse wareHouse) {
        wareHouseMapper.add(wareHouse);
    }

    @Override
    public void delete(Integer id) {
        wareHouseMapper.delete(id);
    }

    @Override
    public void mod(WareHouse wareHouse) {
        wareHouseMapper.mod(wareHouse);
    }

    @Override
    public PageBean listpage(Integer page, Integer pageSize, String name, Integer userId) {
        PageHelper.startPage(page,pageSize);
        WareHouse wareHouse = new WareHouse();
        wareHouse.setName(name);
        wareHouse.setUserId(userId);
        List<WareHouse> wareHouselist = wareHouseMapper.selectSome(wareHouse);
        Page<WareHouse> p = (Page<WareHouse>) wareHouselist;
        return new PageBean(p.getTotal(),p.getResult());
    }

    @Override
    public WareHouse getById(Integer whId) {
        return wareHouseMapper.selectById(whId);
    }
}
