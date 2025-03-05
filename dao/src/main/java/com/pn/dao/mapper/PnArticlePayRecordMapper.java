package com.pn.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pn.dao.entity.PnArticlePayRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PnArticlePayRecordMapper extends BaseMapper<PnArticlePayRecord> {
    boolean isPaid(@Param("articleId")Long articleId,@Param("userId")Long userId);
}