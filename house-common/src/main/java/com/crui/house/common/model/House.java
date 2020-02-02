package com.crui.house.common.model;
import lombok.Data;

import java.util.Date;

/**
 * house
 * 
 * @author crui
 * @version 1.0.0 2020-01-10
 */
@Data
public class House implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 3075999629811656292L;

    /** 主键id */
    private Long id;

    /** 房产名称 */
    private String name;

    /** type */
    private Integer type;

    /** price */
    private Integer price;

    /** images */
    private String images;

    /** area */
    private Integer area;

    /** beds */
    private Integer beds;

    /** baths */
    private Integer baths;

    /** rating */
    private Double rating;

    /** remarks */
    private String remarks;

    /** properties */
    private String properties;

    /** floorPlan */
    private String floorPlan;

    /** tags */
    private String tags;

    /** createTime */
    private Date createTime;

    /** cityId */
    private Integer cityId;

    /** communityId */
    private Integer communityId;

    /** address */
    private String address;

    /** 1-上架，2-下架 */
    private Integer state;

}