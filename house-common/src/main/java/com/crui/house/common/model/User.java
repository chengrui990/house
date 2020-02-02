package com.crui.house.common.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * user
 * 
 * @author crui
 * @version 1.0.0 2020-01-04
 */
@Data
public class User implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 8864189274975542974L;


    /** 主键id */
    private Long id;

    /** 房产名称 */
    private String name;

    /** phone */
    private String phone;

    /** email */
    private String email;

    /** aboutme */
    private String aboutme;

    /** passwd */
    private String passwd;

    private String confirmPasswd;

    private String newPasswd;

    private String key;

    /** avatar */
    private String avatar;

    private MultipartFile avatarFile;


    /** 1-普通用户，2-房产经纪人 */
    private Integer type;

    /** createTime */
    private Date createTime;

    /** 1-启用，0-停用 */
    private Integer enable;

    /** 所属经纪机构 */
    private Integer agencyId;

}