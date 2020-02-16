package com.crui.house.web.controller;

import com.crui.house.biz.service.UserService;
import com.crui.house.common.constants.CommonConstants;
import com.crui.house.common.model.User;
import com.crui.house.common.result.ResultMsg;
import com.crui.house.common.utils.HashUtils;
import com.crui.house.web.interceptor.UserContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * VM Args:
 *
 * @author crui
 */
@Controller
public class UserController {
    @Autowired
    private UserService userService;

//    @RequestMapping("getUsers")
//    public List<User> getUsers(){
//        return userService.getUsers();
//    }
    /**==============用户注册功能================**/
    /**
     * 注册提交：1.注册验证  2.发送邮件  3.验证失败重定向到注册页面
     * 注册页获取：根据account对象为依据判断是否注册页获取请求
     * @param account
     * @param modelMap
     * @return
     */
    @RequestMapping("accounts/register")
    public String accountRegister(User account, ModelMap modelMap){
        if (account == null || account.getName() == null){//注册页获取请求
            return "/user/accounts/register";
        }
        //用户验证
        ResultMsg resultMsg = UserHelper.validate(account);
        //TODO 判断用户邮箱是否已经成功注册过，一个邮箱只能注册一次
        if (resultMsg.isSuccess() && userService.addAccount(account)){
            modelMap.put("email",account.getEmail());
            return "/user/accounts/registerSubmit";
        }else {
            return "redirect:user/accounts/register?" + resultMsg.asUrlParams();
        }
    }

    @RequestMapping("accounts/verify")
    public String verify(String key){
        boolean result = userService.enable(key);
        if (result){
            return "redirect:/index?" + ResultMsg.successMsg("激活成功").asUrlParams();
        }else {
            return "redirect:user/accounts/register?" + ResultMsg.errorMsg("激活失败，庆确认链接是否过期");
        }
    }

    /**============用户登录功能============**/

    @RequestMapping("accounts/signin")
    public String singin(HttpServletRequest request){
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String target = request.getParameter("target");
        if (username==null || password==null){
            request.setAttribute("target", target);
            System.out.println("1");
            return "user/accounts/signin";

        }
        User user = userService.auth(username,password);
        if (user == null){
            System.out.println("2");
            return "redirect:/accounts/signin?" + "target=" + target + "&username=" + username + "&" +ResultMsg.errorMsg("用户名或密码错误").asUrlParams();
        }else {
            HttpSession session = request.getSession(true);
            session.setAttribute(CommonConstants.USER_ATTRIBUTE,user);
            session.setAttribute(CommonConstants.PLAIN_USER_ATTRIBUTE, user);
            System.out.println("3");
            UserContext.setUser(user);
            return StringUtils.isNotBlank(target)? "redirect:" + target : "redirect:/index";
        }
    }

    @RequestMapping("accounts/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession(true);
        session.invalidate();
        return "redirect:/index";
    }

    /** ===============个人信息页==============*/
    @RequestMapping("accounts/profile")
    public String profile(HttpServletRequest req,User updateUser, ModelMap modelMap){
        if (updateUser.getEmail() == null){
            System.out.println("1");
            return "/user/accounts/profile";
        }
        userService.updateUser(updateUser,updateUser.getEmail());
        User queryUser = new User();
        queryUser.setEmail(updateUser.getEmail());
        List<User> userByQuery = userService.getUserByQuery(queryUser);

        req.getSession(true).setAttribute(CommonConstants.USER_ATTRIBUTE, userByQuery.get(0));

        System.out.println("2");
        return "redirect:/accounts/profile?" + ResultMsg.successMsg("更新成功").asUrlParams();
    }
    @RequestMapping("accounts/changePassword")
    public String changePassword(String email, String password,String newPassword, String confirmPassword,ModelMap modelMap){
        User user = userService.auth(email,password);
        if (user == null || !confirmPassword.equals(newPassword)){
            return "redirect:/accounts/profile?" + ResultMsg.errorMsg("密码错误").asUrlParams();
        }
        User updateUser = new User();
        updateUser.setPasswd(HashUtils.encryPasswoed(newPassword));
        userService.updateUser(updateUser,email);
        return "redirect:/accounts/profile?" + ResultMsg.successMsg("更新成功").asUrlParams();
    }
}
