package com.pn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pn.common.reqParams.article.ArticleSaveParams;
import com.pn.dao.entity.PnArticle;

/**
 * @author: javadadi
 * @Time: 13:41
 * @ClassName: ArticleWriteService
 */
public interface ArticleWriteService extends IService<PnArticle> {
    Long save(ArticleSaveParams params);
}
