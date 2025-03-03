package com.pn.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pn.dao.entity.PnArticleTag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Locale;

@Mapper
public interface PnArticleTagMapper extends BaseMapper<PnArticleTag> {
    List<Long> getTagIdsByArticleId(@Param("articleId")Long articleId);
}