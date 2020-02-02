package com.crui.house.web;

import com.crui.house.biz.service.UserService;
import com.crui.house.common.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * VM Args:
 *
 * @author crui
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@RunWith(SpringRunner.class)
public class AuthTest {
    @Autowired
    private UserService userService;

    @Test
    public void testAuth(){
        User auth = userService.auth("990536551@qq.com", "1234567");
        assert auth !=null;
    }

}
