package com.crui.house.biz.service;

import com.crui.house.biz.mapper.UserMapper;
import com.crui.house.common.model.User;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * VM Args:
 *
 * @author crui
 */
@Service
public class MailService {
    @Value("${domain.name}")
    private String domainName;

    private final Cache<String, String> registerCache = CacheBuilder.newBuilder().maximumSize(100).expireAfterAccess(15, TimeUnit.MINUTES).removalListener(new RemovalListener<String, String>() {
        @Override
        public void onRemoval(RemovalNotification<String, String> removalNotification) {
            userMapper.delete(removalNotification.getValue());
        }
    }).build();
    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    private UserMapper userMapper;

    public void sendMail(String title, String url, String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(email);
        message.setText(url);
        mailSender.send(message);
    }

    @Async//标识为异步操作，还需要在启动类中添加 @EnableAsync 注解
    public void registerNotify(String email){
        String randomKey = RandomStringUtils.randomAlphabetic(10);
        registerCache.put(randomKey,email);
        String url = "http://"+ domainName + "/accounts/verify?key=" + randomKey;
        sendMail("TestTitle", url, email);
    }

    public boolean enable(String key) {
        String email = registerCache.getIfPresent(key);
        if (StringUtils.isBlank(email)){
            return false;
        }
        User updateUser = new User();
        updateUser.setEmail(email);
        updateUser.setEnable(1);
        userMapper.update(updateUser);
//        registerCache.invalidate(key);
        return true;
    }
}
