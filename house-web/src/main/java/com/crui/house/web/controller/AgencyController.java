package com.crui.house.web.controller;

import com.crui.house.biz.service.AgencyService;
import com.crui.house.biz.service.HouseService;
import com.crui.house.common.model.House;
import com.crui.house.common.model.User;
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
public class AgencyController {
    @Autowired
    private AgencyService agencyService;
    @Autowired
    private HouseService houseService;
    @RequestMapping("/agency/agentList")
    public String agentList(Integer pageSize, Integer pageNum, ModelMap modelMap){
        PageData<User> agentList = agencyService.getAllAgent(PageParams.build(pageSize,pageNum));
        modelMap.put("ps", agentList);
        return "/user/agent/agentList";
    }

    @RequestMapping("/agency/agentDetail")
    public String agentDetail(Long id, ModelMap modelMap){
        User agentDetail = agencyService.getAgentDetail(id);
        House query = new House();
        query.setUserId(agentDetail.getId());
        query.setBookmarked(false);
        PageData<House> housePageData = houseService.queryHouse(query, new PageParams(3, 1));
        if (housePageData !=null){
            modelMap.put("bindHouses", housePageData.getList());
        }
        modelMap.put("agent", agentDetail);
        return "/user/agent/agentDetail";
    }
}
