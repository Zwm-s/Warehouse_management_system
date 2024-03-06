package com.wms.service;

import com.wms.entity.Item;
import com.wms.entity.PageBean;

public interface ItemService {
    /*
    * 增加物品
    * */
    void add(Item item);

    /*
    * 删除物品
    * */
    void delete(Integer id);

    /*
    * 根据whId删除物品
    * */
    void deleteByWhId(Integer whId);

    /*
    * 修改物品
    * */
    void mod(Item item);

    /*
    * 分页查询
    * */
    PageBean listpage(Integer page, Integer pageSize, String name,Integer whId);

    /*
    * 存储itemImage
    * */
    void saveImage(String imageUrl,Integer id);
}
