package com.crui.house.web.controller;

import com.crui.house.biz.service.RecommandService;
import com.crui.house.common.model.House;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * VM Args:
 *
 * @author crui
 */
@Controller
public class HomePageController {
    @Autowired
    private RecommandService recommandService;

    @RequestMapping("index")
    public String index(ModelMap modelMap){
        List<House> houses = recommandService.getLastest();
        modelMap.put("recomHouses", houses);
        return "homepage/index";
    }

    @RequestMapping("")
    public String home(ModelMap modelMap){
        return "redirect:/index";
    }
}
