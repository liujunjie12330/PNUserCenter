package com.pn.common.base;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Field;

import java.io.Serializable;
import java.util.Date;

/**
 * @author javadadi
 */
@Data
public class BaseModelVo implements Serializable {
    private static final long serialVersionUID = -3567343274900087024L;
    @Field(index = false)
    private Date createAt;
    @Field(index = false)
    private Date updateAt;
    @Field(index = false)
    private Long crateUserId;
    @Field(index = false)
    private String crateUsername;
    @Field(index = false)
    private Long updateUserId;
    @Field(index = false)
    private String updateUsername;
}
