package com.crui.house.web.controller;

import com.crui.house.biz.service.*;
import com.crui.house.common.constants.CommonConstants;
import com.crui.house.common.constants.HouseUserType;
import com.crui.house.common.model.*;
import com.crui.house.common.page.PageData;
import com.crui.house.common.page.PageParams;
import com.crui.house.common.result.ResultMsg;
import com.crui.house.web.interceptor.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * VM Args:
 *
 * @author crui
 */
@Controller
public class HouseController {
    @Autowired
    private HouseService houseService;

    @Autowired
    private AgencyService agencyService;

    @Autowired
    private CommentService commentService;
    @Autowired
    private RecommandService recommandService;

    @Autowired
    private CityService cityService;

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

        List<House> hotHouses = recommandService.getHotHouse(3);
        modelMap.put("recomHouses", hotHouses);

        modelMap.put("ps", housePageData);
        modelMap.put("vo", query);

        return "/house/listing";
    }

    /**
     *  1.获取房屋详情
     *  2.获取经纪人信息
     * @param id
     * @param modelMap
     * @return
     */
    @RequestMapping("/house/detail")
    public String houseDetail(Long id, ModelMap modelMap){
        House house = houseService.queryOneHouse(id);
        HouseUser houseUser = houseService.getHouseUser(id);
        if (houseUser.getUserId()!=null && houseUser.getUserId()!=0){
            modelMap.put("agent", agencyService.getAgentDetail(house.getUserId()));
        }
        modelMap.put("house", house);
        List<House> hotHouses = recommandService.getHotHouse(3);
        modelMap.put("recomHouses", hotHouses);
        List<Comment> comments = commentService.getHouseComments(id);
        modelMap.put("commentList" , comments);
        //TODO Redis异常
        recommandService.increase(id);
        return "/house/detail";
    }

    /**
     *
     * @param userMsg
     * @return
     */
    @RequestMapping("/house/leaveMsg")
    public String leaveMsg(UserMsg userMsg){
        houseService.addUserMsg(userMsg);
        return "redirect:/house/detail?id=" + userMsg.getHouseId();
    }

    @RequestMapping("house/toAdd")
    public String toAdd(ModelMap modelMap){
        List<City> citys = cityService.getAllCitys();
        modelMap.put("citys",citys);
//        Community
        modelMap.put("communitys", houseService.getAllCommunitys());
        return "/house/add";
    }

    @RequestMapping("house/add")
    public String add(House house){
        User user = UserContext.getUser();
        house.setState(CommonConstants.HOUSE_STATE_UP);
        houseService.addHouse(house,user);
        return "redirect:/house/ownlist";
    }

    @RequestMapping("house/ownlist")
    public String ownlist(HttpServletRequest request,House house, Integer pageNum, Integer pageSize, ModelMap modelMap){
        User user = UserContext.getUser();
        User loginUser = (User) request.getSession().getAttribute("loginUser");
        house.setUserId(user.getId());
        house.setBookmarked(false);
        modelMap.put("ps", houseService.queryHouse(house, PageParams.build(pageSize,pageNum)));
        modelMap.put("pageType" , "own");

        return "/house/ownlist";
    }

    @ResponseBody
    @RequestMapping("house/rating")
    public ResultMsg rating(Double rating, Long id){
        houseService.updateRating(id, rating);
        return ResultMsg.successMsg("ok");
    }

    @ResponseBody
    @RequestMapping("house/bookmark")
    public ResultMsg bookmark(Long id){
        User user = UserContext.getUser();
        houseService.bindUser2House(id, user.getId(),true);
        return ResultMsg.successMsg("ok");
    }
    @ResponseBody
    @RequestMapping("house/unbookmark")
    public ResultMsg unbookmark(Long id){
        User user = UserContext.getUser();
        houseService.unbindUser2House(id, user.getId(), HouseUserType.BOOKMARK);
        return ResultMsg.successMsg("ok");
    }

    @RequestMapping("house/bookmarked")
    public String bookmarked(House house, Integer pageNum, Integer pageSize, ModelMap modelMap) {
        User user = UserContext.getUser();
        house.setBookmarked(true);
        house.setUserId(user.getId());
        modelMap.put("ps", houseService.queryHouse(house, PageParams.build(pageSize, pageNum)));
        modelMap.put("pageType", "book");
        return "/house/ownlist";
    }

    @RequestMapping("house/del")
    public String delsale(Long id, String pageType){
        User user = UserContext.getUser();
        houseService.unbindUser2House(id, user.getId(), pageType.equals("own") ? HouseUserType.SALE : HouseUserType.BOOKMARK);
        return "redirect:/house/ownlist";
    }
}
