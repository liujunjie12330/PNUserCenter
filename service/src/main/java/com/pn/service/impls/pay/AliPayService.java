package com.pn.service.impls.pay;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pn.dao.entity.PnTransactions;
import com.pn.dao.mapper.PnTransactionsMapper;
import com.pn.service.impls.PayService;
import org.springframework.stereotype.Service;

@Service
public class AliPayService extends ServiceImpl<PnTransactionsMapper, PnTransactions> implements PayService {


    public void pay(){
        return;
    }
}
