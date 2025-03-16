package com.pn.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pn.dao.entity.PnTransactions;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PnTransactionsMapper extends BaseMapper<PnTransactions> {
    PnTransactions getByOrderNo(@Param("orderNo") String orderNo);
}