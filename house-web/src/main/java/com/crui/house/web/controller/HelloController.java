package com.crui.house.web.controller;

import com.crui.house.biz.service.UserService;
import com.crui.house.common.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * VM Args:
 *
 * @author crui
 */
@Controller
public class HelloController {
    @Autowired
    private UserService userService;

    @RequestMapping("hello")
    public String hello(ModelMap modelMap) {
        User user = userService.getUserById();
        if (user!= null){
            throw new IllegalArgumentException("asdadas");
        }
        modelMap.put("user", user);
        return "hello";
    }

}
