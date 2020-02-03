package com.crui.house.common.model;

import lombok.Data;

/**
 * community
 * 
 * @author crui
 * @version 1.0.0 2020-01-10
 */
@Data
public class Community implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 6724737400563530147L;

    /** 主键id */
    private Integer id;

    /** 城市编码 */
    private String cityCode;

    /** 城市名称 */
    private String cityName;

    /** 小区名称 */
    private String name;


}