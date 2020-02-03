package com.crui.house.web.controller;

import com.crui.house.biz.service.HouseService;
import com.crui.house.common.model.House;
import com.crui.house.common.page.PageData;
import com.crui.house.common.page.PageParams;
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
public class HouseController {
    @Autowired
    private HouseService houseService;

    /**
     * 1.支持分页
     * 2.支持小区搜索，类型搜索
     * 3.支持排序
     * 4.支持图片展示、价格、标题、地址等信息
     *
     * @return
     */
    @RequestMapping("/house/list")
    public String houseList(Integer pageSize, Integer pageNum, House query, ModelMap modelMap) {
        PageData<House> housePageData = houseService.queryHouse(query, PageParams.build(pageSize, pageNum));
        System.out.println("总记录："+housePageData.getPagination().getTotalCount());
        modelMap.put("ps", housePageData);
        modelMap.put("vo", query);
        return "/house/listing";
    }
}
