package com.pn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pn.dao.entity.PnArticlePayRecord;

/**
 * 文章支付
 */
public interface ArticlePayService extends IService<PnArticlePayRecord> {
    String payArticle(Long articleId);

    boolean isPaid(Long articleId, Long userId);
}
