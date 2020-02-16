package com.crui.house.common.model;

import lombok.Data;

/**
 * agency
 * 
 * @author crui
 * @version 1.0.0 2020-01-10
 */
@Data
public class Agency implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -1984531695615962437L;
    /** id */
    private Integer id;

    /** 经纪机构名称 */
    private String name;

    /** 地址 */
    private String address;

    /** 手机 */
    private String phone;

    /** 电子邮件 */
    private String email;

    /** 描述 */
    private String aboutUs;

    /** 电话 */
    private String mobile;

    /** 网站 */
    private String webSite;
}