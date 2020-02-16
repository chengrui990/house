package com.crui.house.biz.mapper;

import com.crui.house.common.model.Community;
import com.crui.house.common.model.House;
import com.crui.house.common.model.HouseUser;
import com.crui.house.common.model.UserMsg;
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

    int insertUserMsg(UserMsg userMsg);

    HouseUser selectHouseUser(@Param("id") Long id);

    int insert(House house);

    HouseUser selectHouseUser(@Param("userId") Long userId,@Param("houseId") Long houseId,@Param("type") Integer type);

    int insertHouseUser(HouseUser houseUser);

    int updateHouse(House updateHouse);

    int deleteHouseUser(@Param("id") Long id,@Param("userId") Long userId,@Param("type") Integer value);
}
