package com.pn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pn.common.reqParams.article.ArticleSaveParams;
import com.pn.dao.entity.PnArticle;

/**
 *文章保存类
 */
public interface ArticleWriteService extends IService<PnArticle> {
    Long save(ArticleSaveParams params);
    void delete(Long articleId);
}
