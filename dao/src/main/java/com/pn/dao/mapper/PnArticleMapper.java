package com.pn.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pn.common.reqParams.article.ArticleAdminParam;
import com.pn.dao.bo.article.PnArticleBo;
import com.pn.dao.entity.PnArticle;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PnArticleMapper extends BaseMapper<PnArticle> {
    Page<PnArticleBo> page(Page<PnArticleBo> page, @Param("param") ArticleAdminParam param);
}