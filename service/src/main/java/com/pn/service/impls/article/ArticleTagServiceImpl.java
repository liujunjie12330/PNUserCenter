package com.pn.service.impls.article;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.pn.dao.entity.PnArticleTag;
import com.pn.dao.mapper.PnArticleTagMapper;
import com.pn.service.ArticleTagService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Set;

/**
 * 标签管理
 */
@Service
public class ArticleTagServiceImpl extends ServiceImpl<PnArticleTagMapper, PnArticleTag> implements ArticleTagService {

    @Override
    public void saveBatch(Long articleId, Set<Long> tagIds){
        if (CollectionUtils.isEmpty(tagIds)){
            return;
        }
        ArrayList<PnArticleTag> articleTags = Lists.newArrayList();
        tagIds.forEach(m->{
            PnArticleTag pnArticleTag = new PnArticleTag();
            pnArticleTag.setArticleId(articleId);
            pnArticleTag.setTagId(m);
            articleTags.add(pnArticleTag);
        });
        saveBatch(articleTags);
    }
}
