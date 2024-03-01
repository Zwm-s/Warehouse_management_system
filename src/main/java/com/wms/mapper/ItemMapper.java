package com.wms.mapper;


import com.wms.entity.Item;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/*
 * Item的mapper接口
 * */
@Mapper
public interface ItemMapper {

    @Insert("insert into item(id, name, image, wh_id, instruction) VALUES (#{id},#{name},#{image},#{whId},#{instruction})")
    void add(Item item);


    void delete(Integer id);

    void mod(Item item);

    List<Item> selectSome(Item item);
}
