package com.crui.house.common.model;
import lombok.Data;

import java.util.Date;

/**
 * house_user
 * 
 * @author crui
 * @version 1.0.0 2020-01-10
 */
@Data
public class HouseUser implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 4334891998538197202L;

    /** 主键id */
    private Long id;

    /** 房屋id */
    private Long houseId;

    /** 用户id */
    private Long userId;

    /** 1-售卖，2-收藏 */
    private Integer type;

    /** createTime */
    private Date createTime;
}