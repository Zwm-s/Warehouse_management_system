package com.wms.service;

import com.wms.entity.Item;
import com.wms.entity.PageBean;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;

public interface ItemService {
    /*
    * 增加物品
    * */
    void add(Item item, MultipartFile itemImage,HttpServletRequest request) ;

    /*
    * 删除物品
    * */
    void delete(Integer id) ;

    /*
    * 根据whId删除物品
    * */
    void deleteByWhId(Integer whId) ;

    /*
    * 修改物品
    * */
    void mod(Item item, MultipartFile itemImage, HttpServletRequest request) ;

    /*
    * 分页查询
    * */
    PageBean listpage(Integer page, Integer pageSize, String name,Integer whId);

    /*
    * 存储itemImage
    * */
    void saveImage(String imageUrl,Integer id);
}
