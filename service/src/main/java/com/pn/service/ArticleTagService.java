package com.pn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pn.dao.entity.PnArticleTag;

import java.util.Set;

/**
 * @author: javadadi
 * @Time: 19:47
 * @ClassName: ArticleTagService
 */
public interface ArticleTagService extends IService<PnArticleTag> {

    void saveBatch(Long articleId, Set<Long> tagIds);
}
