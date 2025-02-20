package com.pn.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 专栏信息表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "pn_column_info")
public class PnColumnInfo extends BaseModel {
    private static final long serialVersionUID = 6962775475993095863L;
    /**
     * 专栏名
     */
    @TableField(value = "`column_name`")
    private String columnName;

    /**
     * 作者id
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 专栏简述
     */
    @TableField(value = "introduction")
    private String introduction;

    /**
     * 专栏封面
     */
    @TableField(value = "cover")
    private String cover;

    /**
     * 状态: 0-审核中，1-连载，2-完结
     */
    @TableField(value = "`state`")
    private Integer state;

    /**
     * 上线时间
     */
    @TableField(value = "publish_time")
    private Date publishTime;
    /**
     * 排序
     */
    @TableField(value = "`section`")
    private Integer section;

    /**
     * 专栏预计的更新的文章数
     */
    @TableField(value = "nums")
    private Integer nums;

    /**
     * 专栏类型 0-免费 1-登录阅读 2-限时免费
     */
    @TableField(value = "`type`")
    private Integer type;

    /**
     * 限时免费开始时间
     */
    @TableField(value = "free_start_time")
    private Date freeStartTime;

    /**
     * 限时免费结束时间
     */
    @TableField(value = "free_end_time")
    private Date freeEndTime;
}