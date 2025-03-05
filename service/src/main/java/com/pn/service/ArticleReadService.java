package com.pn.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pn.common.reqParams.article.ArticleIndexParam;
import com.pn.common.vos.article.ArticleIndexVo;
import com.pn.common.vos.article.ArticleVO;
import com.pn.dao.entity.PnArticle;

/**
 * 文章前台接口
 */
public interface ArticleReadService extends IService<PnArticle> {
    Page<ArticleIndexVo> page(ArticleIndexParam param);

    ArticleVO read(Long id);
}
