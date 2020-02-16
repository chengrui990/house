package com.crui.house.common.model;

import lombok.Data;

import java.util.Date;

/**
 * VM Args:
 *
 * @author crui
 */

@Data
public class UserMsg {
    private Long id;
    private String msg;
    private Long userId;
    private Date createTime;
    private Long agentId;
    private Long houseId;
    private String email;

    private String userName;
}
