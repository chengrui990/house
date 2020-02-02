package com.crui.house.biz.service;

import com.crui.house.biz.mapper.UserMapper;
import com.crui.house.common.model.User;
import com.crui.house.common.utils.BeanHelper;
import com.crui.house.common.utils.HashUtils;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


/**
 * VM Args:
 *
 * @author crui
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private FileService fileService;
    @Autowired
    private MailService mailService;

    @Value("${file.prefix}")
    private String imgPrefix;


    public List<User> getUsers() {
        return userMapper.selectUsers();
    }

    public User getUserById() {
        return userMapper.selectUserById((long) 25);
    }

    /**
     * 1.插入数据库，非激活； 密码加盐md5；保存头像到本地
     * 2.生成key，绑定email
     * 3.发送邮件给用户
     * @param account
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean addAccount(User account) {
        account.setPasswd(HashUtils.encryPasswoed(account.getPasswd()));
        List<String> imgList = fileService.getImgPath(Lists.newArrayList(account.getAvatarFile()));
        if (!imgList.isEmpty()){
            account.setAvatar(imgList.get(0));
        }
        BeanHelper.setDefaultProp(account,User.class);
        BeanHelper.onInsert(account);
        account.setEnable(0);
        int insertResult = userMapper.insert(account);
        mailService.registerNotify(account.getEmail());

        return true;
    }


    public boolean enable(String key) {
        return mailService.enable(key);
    }

    public User auth(String username, String password) {
        User user = new User();
        user.setEmail(username);
        user.setPasswd(HashUtils.encryPasswoed(password));
        user.setEnable(1);
        List<User> users = getUserByQuery(user);
        if (!users.isEmpty()){
            return users.get(0);
        }
        return null;
    }

    public List<User> getUserByQuery(User user) {
        List<User> users = userMapper.selectUsersByQuery(user);
        users.forEach(user1 -> {
            user1.setAvatar(imgPrefix + user1.getAvatar());
        });
        return users;
    }

    public void updateUser(User updateUser, String email) {
        updateUser.setEmail(email);
        BeanHelper.onUpdate(updateUser);
        userMapper.update(updateUser);
    }
}
