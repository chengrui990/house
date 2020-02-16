package com.crui.house.common.model;

import lombok.Data;

/**
 * city
 * 
 * @author crui
 * @version 1.0.0 2020-02-03
 */
@Data
public class City implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -3651453189161816887L;

    /** id */
    private Integer id;

    /** 城市名称 */
    private String cityName;

    /** 城市编码 */
    private String cityCode;

}