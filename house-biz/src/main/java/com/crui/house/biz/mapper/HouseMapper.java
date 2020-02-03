package com.crui.house.biz.mapper;

import com.crui.house.common.model.Community;
import com.crui.house.common.model.House;
import com.crui.house.common.page.PageParams;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * VM Args:
 *
 * @author crui
 */
@Mapper
@Repository
public interface HouseMapper {

    public List<House> selectPageHouses(@Param("house") House house, @Param("pageParams") PageParams pageParams);
    public Long selectPageCount(@Param("house") House house);

    List<Community> selectCommunity(Community community);
}
