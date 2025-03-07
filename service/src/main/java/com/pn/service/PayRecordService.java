package com.pn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pn.dao.entity.PnTransactions;

import java.util.Map;

/**
 * 支付记录
 */
public interface PayRecordService extends IService<PnTransactions> {
    Long saveRecord(Map<String, String[]> parameterMap);
}
