package com.pn.common.enums;

public enum TransactionStatus {
    WAIT_BUYER_PAY("交易创建，等待买家付款"),
    TRADE_CLOSED("未付款交易超时关闭，或支付完成后全额退款"),
    TRADE_SUCCESS("交易支付成功"),
    TRADE_FINISHED("交易结束，不可退款");

    private final String description; // 中文描述

    // 构造方法
    TransactionStatus(String description) {
        this.description = description;
    }

    // 获取枚举名称
    public String getName() {
        return this.name(); // 使用枚举自带的 name() 方法
    }

    // 获取中文描述
    public String getDescription() {
        return description;
    }

    // 重写 toString 方法，方便打印
    @Override
    public String toString() {
        return "TransactionStatus{" +
                "name='" + this.name() + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}