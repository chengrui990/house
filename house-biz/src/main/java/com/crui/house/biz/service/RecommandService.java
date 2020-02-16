package com.crui.house.biz.service;

import com.crui.house.common.model.House;
import com.crui.house.common.page.PageParams;
import com.google.common.collect.Ordering;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * VM Args:
 *
 * @author crui
 */
@Service
public class RecommandService {
    private static final String HOT_HOUSE_KEY = "hot_house";
    @Autowired
    private HouseService houseService;

    public void increase(Long id){
        Jedis jedis = new Jedis("win10-notebook",16379);
        jedis.auth("111111");
        jedis.zincrby(HOT_HOUSE_KEY, 1.0D, id + "");
        jedis.zremrangeByRank(HOT_HOUSE_KEY,10,-1);
        jedis.close();
    }

    public List<Long> getHot(){
        Jedis jedis = new Jedis("win10-notebook",16379);
        jedis.auth("111111");
        Set<String> idSet = jedis.zrevrange(HOT_HOUSE_KEY, 0, -1);
        jedis.close();
        System.out.println("热门："+idSet);
        return idSet.stream().map(Long::parseLong).collect(Collectors.toList());
    }

    public List<House> getHotHouse(Integer size){
        House query = new House();

        //TODO Redis连接异常
        List<Long> ids = getHot();
        if (ids==null || ids.size()==0){
            return new ArrayList<>();
        }
        ids = ids.subList(0, Math.min(ids.size(), size));
        query.setIds(ids);
        List<Long> orderList = ids;
        List<House> houses = houseService.queryAndSetImg(query, PageParams.build(size, 1));
        Ordering<House> houseOrdering = Ordering.natural().onResultOf(house -> orderList.indexOf(house.getId()));
        return houseOrdering.sortedCopy(houses);
    }

    public List<House> getLastest() {
        House query = new House();
        query.setSort("create_time");
        return houseService.queryAndSetImg(query, PageParams.build(8,1));

    }
}
