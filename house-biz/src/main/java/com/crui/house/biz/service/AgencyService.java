package com.crui.house.biz.service;

import com.crui.house.biz.mapper.AgencyMapper;
import com.crui.house.common.model.User;
import com.crui.house.common.page.PageData;
import com.crui.house.common.page.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * VM Args:
 *
 * @author crui
 */
@Service
public class AgencyService {
    @Value("${file.prefix}")
    private String imgPrefix;
    @Autowired
    private AgencyMapper agencyMapper;

    public User getAgentDetail(Long userId) {
        User user = new User();
        user.setId(userId);
        user.setType(2);
        List<User> users = agencyMapper.selectAgent(user, PageParams.build(1, 1));
        setImg(users);
        if (!users.isEmpty()){
            return users.get(0);
        }
        return null;
    }

    private void setImg(List<User> users) {
        users.forEach(user -> {
            user.setAvatar(imgPrefix + user.getAvatar());
        });
    }

    public PageData<User> getAllAgent(PageParams pageParams) {
        List<User> agents = agencyMapper.selectAgent(new User(), pageParams);
        setImg(agents);
        Long count = agencyMapper.selectAgentCount(new User());
        return PageData.buildPage(agents,count,pageParams.getPageSize(),pageParams.getPageNum());
    }
}
