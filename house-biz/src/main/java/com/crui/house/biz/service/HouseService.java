package com.crui.house.biz.service;

import com.crui.house.biz.mapper.HouseMapper;
import com.crui.house.common.model.Community;
import com.crui.house.common.model.House;
import com.crui.house.common.page.PageData;
import com.crui.house.common.page.PageParams;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
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

    private List<House> queryAndSetImg(House query, PageParams pageParams) {
        List<House> houses = houseMapper.selectPageHouses(query, pageParams);
        houses.forEach(house -> {
            house.setFirstImg(imgPrefix +house.getFirstImg());
            house.setImagesList(house.getImagesList().stream().map(img -> imgPrefix + img).collect(Collectors.toList()));
            house.setFloorPlanList(house.getFloorPlanList().stream().map(pic -> imgPrefix +pic).collect(Collectors.toList()));

        });
        return houses;
    }
}
