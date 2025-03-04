package com.pn.service.impls.pay.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 对第三方用户进行转账
 */
@Data
public class AlipayToThirdUserDto implements Serializable {
    private static final long serialVersionUID = 7968342473272268554L;
    /**
     * articleId
     */
    private Long articleId;
    /**
     * authorId
     */
    private Long authorId;
    /**
     * 系统生成的订单号
     */
    private String outBizNo;
    /**
     * 金额
     */
    private String transAmount;
    /**
     * 交易标题
     */
    private String title;
    /**
     * 备注
     */
    private String remark;

    /*系统固定信息*/
    public static enum bizScene {
        //默认用法
        DIRECT_TRANSFER("DIRECT_TRANSFER", "单笔无密转账到支付宝"),
        PERSONAL_COLLECTION("PERSONAL_COLLECTION", "C2C现金红包-领红包"),
        CAE_TRANSFER("CAE_TRANSFER", "CAE代扣"),
        DIRECT_ALLOCATION("DIRECT_ALLOCATION", "余额充值到记账本"),
        DIRECT_ALLOCATION_TRANSFER("DIRECT_ALLOCATION_TRANSFER", "资金调拨转账"),
        ENTRUST_ALLOCATION("ENTRUST_ALLOCATION", "记账本间资金调拨"),
        ENTRUST_ALLOCATION_TRANSFER("ENTRUST_ALLOCATION_TRANSFER", "调拨并转账"),
        ENTRUST_TRANSFER("ENTRUST_TRANSFER", "记账本代发"),
        OVERSEA_FCY_TRANSFER("OVERSEA_FCY_TRANSFER", "境外结汇入金"),
        THIRDPARTY_PERSONAL_COLLECTION("THIRDPARTY_PERSONAL_COLLECTION", "红包资金发放"),
        THIRDPARTY_PERSONAL_COLLECTION_CONFIRM("THIRDPARTY_PERSONAL_COLLECTION_CONFIRM", "红包资金领取"),
        UNLIMITED_PAY("UNLIMITED_PAY", "大额无限付");

        private final String code;
        private final String description;

        bizScene(String code, String description) {
            this.code = code;
            this.description = description;
        }

        public String getDescription() {
            return description;
        }

        public String getCode() {
            return code;
        }
    }

    /**
     * 销售产品码
     */
    public static final String PRODUCT_CODE = "TRANS_ACCOUNT_NO_PWD";
    /**
     * identity类型
     */
    public static final String IDENTITY_TYPE = "ALIPAY_USER_ID";
}
