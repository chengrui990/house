package com.crui.house.common.model;
import lombok.Data;

import java.util.Date;

/**
 * comment
 * 
 * @author crui
 * @version 1.0.0 2020-01-10
 */
@Data
public class Comment implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 4694949364639843860L;

    /** 主键id */
    private Long id;

    /** 评论内容 */
    private String content;

    /** 房屋id */
    private Long houseId;

    /** 发布时间戳 */
    private Date createTime;

    /** 博客id */
    private Integer blogId;

    /** 1-房产评论，2-博客评论 */
    private Integer type;

    /** userId */
    private Long userId;

    private String avatar;
    private String userName;

}