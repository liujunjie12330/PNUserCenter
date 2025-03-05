package com.pn.service.impls.record;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pn.dao.entity.PnAlipayUserInfo;
import com.pn.dao.entity.PnTransactions;
import com.pn.dao.mapper.PnAlipayUserInfoMapper;
import com.pn.dao.mapper.PnTransactionsMapper;
import com.pn.service.PayRecordService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 支付记录
 */
@Service
@Slf4j
public class PayRecordServiceImpl extends ServiceImpl<PnTransactionsMapper, PnTransactions> implements PayRecordService {
    @Resource
    private PnAlipayUserInfoMapper infoMapper;

    @Override
    public void saveRecord(Map<String, String[]> parameterMap) {
        String buyerId = parameterMap.get("buyer_id")[0];
        PnAlipayUserInfo userInfo = infoMapper.getByUuId(buyerId);
        PnTransactions pnTransactions = new PnTransactions();
        for (String key : parameterMap.keySet()) {

        }
    }
}
