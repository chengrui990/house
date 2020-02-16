package com.crui.house.biz.mapper;

import com.crui.house.common.model.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CommentMapper {

     List<Comment> selectHouseComments(@Param("houseId") Long id);
}
