package com.pn.service.impls.article;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pn.common.reqParams.article.ArticleIndexParam;
import com.pn.common.vos.article.ArticleIndexVo;
import com.pn.dao.entity.PnArticle;
import com.pn.dao.mapper.PnArticleMapper;
import com.pn.service.ArticleReadService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 文章前台
 */
@Service
@Slf4j
public class ArticleReadServiceImpl extends ServiceImpl<PnArticleMapper,PnArticle> implements ArticleReadService {

    @Resource
    private PnArticleMapper mapper;

    public Page<ArticleIndexVo> page(ArticleIndexParam param){
        Page<PnArticle> articlePage = new Page<>(param.getCurrent(), param.getSize());
        if (StringUtils.isNotEmpty(param.getSearch())){
            searchPage(param.getSearch(),articlePage);
        }

    }

    @SuppressWarnings("all")
    private Page<PnArticle> searchPage(String search,Page<PnArticle> articlePage){

    }
 }
