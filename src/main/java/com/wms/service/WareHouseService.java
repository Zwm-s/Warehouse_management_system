package com.wms.service;

import com.wms.entity.PageBean;
import com.wms.entity.WareHouse;

public interface WareHouseService {
    void add(WareHouse wareHouse);

    void delete(Integer id);

    void mod(WareHouse wareHouse);

    PageBean listpage(Integer page, Integer pageSize, String name, Integer userId);
}
