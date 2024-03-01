package com.wms.mapper;

import com.wms.entity.WareHouse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface WareHouseMapper {


    void add(WareHouse wareHouse);

    void delete(Integer id);

    void mod(WareHouse wareHouse);

    List<WareHouse> selectSome(WareHouse wareHouse);
}
