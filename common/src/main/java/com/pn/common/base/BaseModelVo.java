package com.pn.common.base;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author javadadi
 */
@Data
public class BaseModelVo implements Serializable {
    private static final long serialVersionUID = -3567343274900087024L;
    private Date createAt;
    private Date updateAt;
    private Long crateUserId;
    private String crateUsername;
    private Long updateUserId;
    private String updateUsername;
}
