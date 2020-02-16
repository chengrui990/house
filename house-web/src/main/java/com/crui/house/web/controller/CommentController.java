package com.crui.house.web.controller;

import com.crui.house.biz.service.CommentService;
import com.crui.house.common.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * VM Args:
 *
 * @author crui
 */
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

//    @RequestMapping("/comment/leaveComment")
//    public String leaveComment(HttpServletRequest request,Comment comment){
//        String from = String.valueOf(request.getRequestURL());
//        System.out.println("from = " + from);
//        return "redirect:" + from;
//    }

}
