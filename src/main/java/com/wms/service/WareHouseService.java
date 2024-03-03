package com.wms.service;

import com.wms.entity.PageBean;
import com.wms.entity.WareHouse;

public interface WareHouseService {

    /*
    * 增加仓库
    * */
    void add(WareHouse wareHouse);

    /*
     * 删除仓库仓库
     * */
    void delete(Integer id);

    /*
     * 修改仓库
     * */
    void mod(WareHouse wareHouse);

    /*
     * 分页查询仓库
     * */
    PageBean listpage(Integer page, Integer pageSize, String name, Integer userId);

    /*
     * 根据Id查找仓库
     * */
    WareHouse getById(Integer whId);
}
