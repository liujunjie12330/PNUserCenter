package com.pn.service.impls.article;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pn.common.reqParams.article.ArticleIndexParam;
import com.pn.common.vos.article.ArticleFootCountDTO;
import com.pn.common.vos.article.ArticleIndexVo;
import com.pn.dao.bo.article.ArticleIndexBo;
import com.pn.dao.entity.PnArticle;
import com.pn.dao.mapper.PnArticleMapper;
import com.pn.service.ArticleReadService;
import com.pn.service.utils.cover.PageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 文章前台
 */
@Service
@Slf4j
public class ArticleReadServiceImpl extends ServiceImpl<PnArticleMapper, PnArticle> implements ArticleReadService {

    @Resource
    private PnArticleMapper mapper;

    @Override
    public Page<ArticleIndexVo> page(ArticleIndexParam param) {
        Page<ArticleIndexBo> page = new Page<>(param.getCurrent(), param.getSize());
        Page<ArticleIndexBo> indexBoPage = mapper.indexPage(page, param);
        //从redis取出文章的相关数据,后面这里优化成从es里面那数据
        Page<ArticleIndexVo> pageVo = PageUtil.coverToPageVo(indexBoPage, bo -> {
            ArticleIndexVo articleIndexVo = new ArticleIndexVo();
            BeanUtil.copyProperties(bo, articleIndexVo);
            //todo从redis里面拿出统计数据，这里进行简单的模拟
            articleIndexVo.setArticleFootCountDTO(new ArticleFootCountDTO());
            return articleIndexVo;
        });
        return pageVo;
    }

}
