package com.crui.house.web.controller;

import com.crui.house.biz.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * VM Args:
 *
 * @author crui
 */
@Controller
public class HouseController {
    @Autowired
    private HouseService houseService;

    /**
     * 1.支持分页
     * 2.支持小区搜索，类型搜索
     * 3.支持排序
     * 4.支持图片展示、价格、标题、地址等信息
     * @return
     */
    @RequestMapping("/house/list")
    public String houseList(){

        return "";
    }
}
