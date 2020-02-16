package com.crui.house.biz.mapper;

import com.crui.house.common.model.City;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CityMapper {
    List<City> selectAllCity();
}
