package com.pn.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pn.dao.entity.PnOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PnOrderMapper extends BaseMapper<PnOrder> {
    Boolean exist(@Param("articleId") Long articleId, @Param("userId") Long userId, @Param("type") String type);
    PnOrder selectArticleIdAndUserId(@Param("articleId") Long articleId, @Param("userId") Long userId);

}