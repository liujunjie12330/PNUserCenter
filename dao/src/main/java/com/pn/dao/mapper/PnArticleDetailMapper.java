package com.pn.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pn.dao.entity.PnArticleDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PnArticleDetailMapper extends BaseMapper<PnArticleDetail> {

    PnArticleDetail getByArticleId(@Param("articleId") Long articleId);
}