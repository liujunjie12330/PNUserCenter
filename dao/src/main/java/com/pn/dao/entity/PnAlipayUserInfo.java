package com.pn.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 支付宝用户信息表
 */
@Data
@EqualsAndHashCode(callSuper=true)
@TableName(value = "pn_alipay_user_info")
public class PnAlipayUserInfo extends BaseModel {
    private static final long serialVersionUID = -1278355175133502363L;
    /**
     * 支付用户ID
     */
    @TableField(value = "alipay_uuid")
    private String alipayUuid;

    /**
     * 用户中心id
     */
    @TableField(value = "pn_user_id")
    private Long pnUserId;

    /**
     * 用户名
     */
    @TableField(value = "user_name")
    private String userName;

    /**
     * 昵称
     */
    @TableField(value = "nick_name")
    private String nickName;

    /**
     * 显示名称
     */
    @TableField(value = "display_name")
    private String displayName;

    /**
     * 邮箱
     */
    @TableField(value = "email")
    private String email;

    /**
     * 手机号
     */
    @TableField(value = "mobile")
    private String mobile;

    /**
     * 性别 (m: 男, f: 女)
     */
    @TableField(value = "gender")
    private String gender;

    /**
     * 出生日期
     */
    @TableField(value = "person_birthday")
    private String personBirthday;

    /**
     * 省份
     */
    @TableField(value = "province")
    private String province;

    /**
     * 城市
     */
    @TableField(value = "city")
    private String city;

    /**
     * 国家代码
     */
    @TableField(value = "country_code")
    private String countryCode;

    /**
     * 头像 URL
     */
    @TableField(value = "avatar")
    private String avatar;

    /**
     * 证件号码
     */
    @TableField(value = "cert_no")
    private String certNo;

    /**
     * 证件类型
     */
    @TableField(value = "cert_type")
    private String certType;

    /**
     * 是否实名认证 (T: 是, F: 否)
     */
    @TableField(value = "is_certified")
    private String isCertified;

    /**
     * 是否学生认证 (T: 是, F: 否)
     */
    @TableField(value = "is_student_certified")
    private String isStudentCertified;

    /**
     * 是否被封禁 (T: 是, F: 否)
     */
    @TableField(value = "is_blocked")
    private String isBlocked;

    /**
     * 是否是机构 (N: 个人, Y: 机构)
     */
    @TableField(value = "inst_or_corp")
    private String instOrCorp;

    /**
     * 用户状态 (T: 正常, F: 异常)
     */
    @TableField(value = "user_status")
    private String userStatus;

    /**
     * 用户类型
     */
    @TableField(value = "user_type")
    private Integer userType;

    /**
     * 会员等级
     */
    @TableField(value = "member_grade")
    private String memberGrade;
}