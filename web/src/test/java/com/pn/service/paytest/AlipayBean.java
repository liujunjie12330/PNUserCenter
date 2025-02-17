package com.pn.service.paytest;

import lombok.Data;

import java.io.Serializable;


/**
 * 支付实体对象
 * 根据支付宝接口协议，其中的属性名，必须使用下划线，不能修改
 * 
 * @author 借我丹青妙笔
 */
public class AlipayBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商户订单号，必填
     *
     */
    private String out_trade_no="20150320010101001";
    /**
     * 订单名称，必填
     */
    private String subject="iphone";
    /**
     * 付款金额，必填
     * 根据支付宝接口协议，必须使用下划线
     */
    private String total_amount = "1000.00";
    /**
     * 商品描述，可空
     */
    private String body;
    /**
     * 超时时间参数
     */
    private String timeout_express= "10m";
    /**
     * 产品编号
     */
    private String product_code= "FAST_INSTANT_TRADE_PAY";
}

