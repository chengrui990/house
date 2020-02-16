package com.crui.house.biz.mapper;

import com.crui.house.common.model.User;
import com.crui.house.common.page.PageParams;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AgencyMapper {
    List<User> selectAgent(@Param("user")User user, @Param("pageParams")PageParams pageParams);

    Long selectAgentCount(@Param("user") User user);
}
