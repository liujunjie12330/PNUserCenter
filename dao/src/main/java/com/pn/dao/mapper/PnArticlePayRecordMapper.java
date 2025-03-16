package com.pn.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pn.dao.entity.PnArticlePayRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PnArticlePayRecordMapper extends BaseMapper<PnArticlePayRecord> {
    Boolean isPaid(@Param("payUserId") Long payUserId, @Param("articleId") Long articleId);
    PnArticlePayRecord getByOutBizNo(@Param("outBizNo") String outBizNo);
}