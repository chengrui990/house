package com.crui.house.web.controller;

import com.crui.house.common.model.User;
import com.crui.house.common.result.ResultMsg;
import org.apache.commons.lang3.StringUtils;

/**
 * VM Args:
 *
 * @author crui
 */
public class UserHelper {
    public static ResultMsg validate(User account){
        if (StringUtils.isBlank(account.getEmail())){
            return ResultMsg.errorMsg("Email 为空");
        }
        if (StringUtils.isBlank(account.getName())){
            return ResultMsg.errorMsg("名字 为空");
        }
        if (StringUtils.isBlank(account.getPasswd())){
            return ResultMsg.errorMsg("密码 为空");
        }
        if (account.getPasswd().length() < 6){
            return ResultMsg.errorMsg("密码长度小于6位");
        }
        if (StringUtils.isBlank(account.getConfirmPasswd())){
            return ResultMsg.errorMsg("确认密码 为空");
        }
        if (account.getConfirmPasswd().length() < 6){
            return ResultMsg.errorMsg("确认密码长度小于6位");
        }
        if (!account.getPasswd().equals(account.getConfirmPasswd())){
            return ResultMsg.errorMsg("密码和确认密码不一致");
        }

        return ResultMsg.successMsg("");
    }
}
