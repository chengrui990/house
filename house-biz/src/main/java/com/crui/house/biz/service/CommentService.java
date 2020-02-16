package com.crui.house.biz.service;

import com.crui.house.biz.mapper.CommentMapper;
import com.crui.house.biz.mapper.UserMapper;
import com.crui.house.common.model.Comment;
import com.crui.house.common.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * VM Args:
 *
 * @author crui
 */
@Service
public class CommentService {
    @Value("${file.prefix}")
    private String imgPrefix;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private UserMapper userMapper;

    public List<Comment> getHouseComments(Long id) {

        List<Comment> comments = commentMapper.selectHouseComments(id);
        comments.forEach(comment -> {
            User user = userMapper.selectUserById(comment.getUserId());
            comment.setUserName(user.getName());
            comment.setAvatar(imgPrefix + user.getAvatar());
        });
        return comments;
    }
}
