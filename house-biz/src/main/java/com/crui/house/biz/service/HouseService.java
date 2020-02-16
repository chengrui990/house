package com.crui.house.biz.service;

import com.crui.house.biz.mapper.CommunityMapper;
import com.crui.house.biz.mapper.HouseMapper;
import com.crui.house.common.constants.HouseUserType;
import com.crui.house.common.model.*;
import com.crui.house.common.page.PageData;
import com.crui.house.common.page.PageParams;
import com.crui.house.common.utils.BeanHelper;
import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * VM Args:
 *
 * @author crui
 */
@Service
public class HouseService {
    @Value("${file.prefix}")
    private String imgPrefix;

    @Autowired
    private HouseMapper houseMapper;
    @Autowired
    private AgencyService agencyService;
    @Autowired
    private MailService mailService;
    @Autowired
    private FileService fileService;
    @Autowired
    private CommunityMapper communityMapper;

    public PageData<House> queryHouse(House query, PageParams pageParams) {
        List<House> houses = Lists.newArrayList();
        if (!Strings.isNullOrEmpty(query.getName())){
            Community community = new Community();
            community.setName(query.getName());
            List<Community> communities = houseMapper.selectCommunity(community);
            if (!communities.isEmpty()){
                query.setCommunityId(communities.get(0).getId());
            }
        }
        houses = queryAndSetImg(query, pageParams);
        Long count = houseMapper.selectPageCount(query);
        return PageData.buildPage(houses, count,pageParams.getPageSize(), pageParams.getPageNum());
    }

    public List<House> queryAndSetImg(House query, PageParams pageParams) {
        List<House> houses = houseMapper.selectPageHouses(query, pageParams);
        houses.forEach(house -> {
            house.setFirstImg(imgPrefix +house.getFirstImg());
            house.setImageList(house.getImageList().stream().map(img -> imgPrefix + img).collect(Collectors.toList()));
            house.setFloorPlanList(house.getFloorPlanList().stream().map(pic -> imgPrefix +pic).collect(Collectors.toList()));

        });
        return houses;
    }

    public House queryOneHouse(Long id) {
        House query = new House();
        query.setId(id);
        List<House> houses = queryAndSetImg(query, PageParams.build(1,1));
        if (!houses.isEmpty()){
            House house = houses.get(0);
//            house.setFirstImg(imgPrefix +house.getFirstImg());
//            house.setImageList(house.getImageList().stream().map(img -> imgPrefix + img).collect(Collectors.toList()));
//            house.setFloorPlanList(house.getFloorPlanList().stream().map(pic -> imgPrefix +pic).collect(Collectors.toList()));
            return house;
        }
        return null;
    }

    public void addUserMsg(UserMsg userMsg) {
        BeanHelper.onInsert(userMsg);
        houseMapper.insertUserMsg(userMsg);
        User user = agencyService.getAgentDetail(userMsg.getAgentId());
        mailService.sendMail("来自用户" + userMsg.getUserName()+ "的留言", userMsg.getMsg(), user.getEmail());
    }

    public HouseUser getHouseUser(Long id) {
        HouseUser houseUser = houseMapper.selectHouseUser(null,id,null);
        return houseUser;
    }

    public List<Community> getAllCommunitys() {
        Community community = new Community();

        return communityMapper.selectCommunitys(community);
    }

    /**
     * 1.添加房产图片
     * 2.添加户型图片
     * 3.插入房产信息
     * 4.绑定用户到房产信息
     * @param house
     * @param user
     * @return
     */
    public int addHouse(House house, User user) {
        if (CollectionUtils.isNotEmpty(house.getHouseFiles())){
            String images = Joiner.on(",").join(fileService.getImgPath(house.getHouseFiles()));
            house.setImages(images);
        }
        if (CollectionUtils.isNotEmpty(house.getFloorPlanFiles())){
            String floorPlans = Joiner.on(",").join(fileService.getImgPath(house.getFloorPlanFiles()));
            house.setFloorPlan(floorPlans);
        }
        BeanHelper.onInsert(house);
        houseMapper.insert(house);

        bindUser2House(house.getId(), user.getId(), false);
        return 0;
    }

    public void bindUser2House(Long houseId, Long userId, boolean isCollect) {
        HouseUser existHouseUser = houseMapper.selectHouseUser(userId, houseId, isCollect ? HouseUserType.BOOKMARK.value : HouseUserType.SALE.value);
        if (existHouseUser!=null){
            return;
        }
        HouseUser houseUser = new HouseUser();
        houseUser.setHouseId(houseId);
        houseUser.setUserId(userId);
        houseUser.setType(isCollect ? HouseUserType.BOOKMARK.value : HouseUserType.SALE.value);
        BeanHelper.setDefaultProp(houseUser, HouseUser.class);
        BeanHelper.onInsert(houseUser);
        houseMapper.insertHouseUser(houseUser);
    }

    public void updateRating(Long id, Double rating) {
        House house = queryOneHouse(id);
        Double oldRating = house.getRating();
        Double newRating = oldRating.equals(0D)? rating : Math.min((oldRating+rating)/2, 5);
        House updateHouse = new House();
        updateHouse.setId(id);
        updateHouse.setRating(newRating);
        BeanHelper.onUpdate(updateHouse);
        houseMapper.updateHouse(updateHouse);
    }

    public void unbindUser2House(Long id, Long userId, HouseUserType bookmark) {
        houseMapper.deleteHouseUser(id, userId,bookmark.value);
    }
}
