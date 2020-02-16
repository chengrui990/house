package com.crui.house.biz.service;

import com.crui.house.biz.mapper.CityMapper;
import com.crui.house.common.model.City;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * VM Args:
 *
 * @author crui
 */
@Service
public class CityService {
    @Autowired
    private CityMapper cityMapper;
    public List<City> getAllCitys(){
        return cityMapper.selectAllCity();
    }
}
