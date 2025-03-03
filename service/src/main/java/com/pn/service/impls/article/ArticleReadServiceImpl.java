package com.pn.service.impls.article;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.pn.common.base.UserTokenThreadHolder;
import com.pn.common.enums.StatusCode;
import com.pn.common.exception.BizException;
import com.pn.common.reqParams.article.ArticleIndexParam;
import com.pn.common.vos.article.ArticleFootCountVo;
import com.pn.common.vos.article.ArticleIndexVo;
import com.pn.common.vos.article.ArticleVO;
import com.pn.dao.bo.article.ArticleIndexBo;
import com.pn.dao.entity.*;
import com.pn.dao.mapper.*;
import com.pn.service.ArticleReadService;
import com.pn.service.utils.cover.ArticleCoverUtil;
import com.pn.service.utils.cover.PageUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

import static com.pn.common.enums.ArticleReadTypeEnum.LOGIN;
import static com.pn.common.enums.ArticleReadTypeEnum.NORMAL;

/**
 * 文章前台
 */
@Service
@Slf4j
public class ArticleReadServiceImpl extends ServiceImpl<PnArticleMapper, PnArticle> implements ArticleReadService {

    @Resource
    private PnArticleMapper mapper;

    @Resource
    private PnArticleDetailMapper detailMapper;

    @Resource
    private PnUserMapper userMapper;

    @Resource
    private PnArticleTagMapper articleTagMapper;

    @Resource
    private PnTagMapper tagMapper;

    @Resource
    private PnCatalogMapper catalogMapper;

    @Resource
    private PnColumnInfoMapper columnInfoMapper;

    @Override
    public Page<ArticleIndexVo> page(ArticleIndexParam param) {
        Page<ArticleIndexBo> page = new Page<>(param.getCurrent(), param.getSize());
        Page<ArticleIndexBo> indexBoPage = mapper.indexPage(page, param);
        //从redis取出文章的相关数据,后面这里优化成从es里面那数据
        Page<ArticleIndexVo> pageVo = PageUtil.coverToPageVo(indexBoPage, bo -> {
            ArticleIndexVo articleIndexVo = new ArticleIndexVo();
            BeanUtil.copyProperties(bo, articleIndexVo);
            //todo从redis里面拿出统计数据，这里进行简单的模拟
            articleIndexVo.setArticleFootCountVo(null);
            return articleIndexVo;
        });
        return pageVo;
    }


    public ArticleVO read(Long id) {
        //首先查找文章是否存在
        PnArticle article = getById(id);
        if (Objects.isNull(article)) {
            throw new BizException(StatusCode.PARAMS_ERROR);
        }
        //如果文章存在，查看文章的阅读状态
        Integer readType = article.getReadType();
        /*
          直接阅读--->不需要登陆权限
         */
        if (Objects.equals(readType, NORMAL.getType())) {
            return readNormal(article);
        }
        /*
           登陆阅读
         */
        if (Objects.equals(readType, LOGIN.getType())) {
            return readLogin(article);
        }
        /*
        todo 限时阅读
         */
        /*
         * 付费阅读
         */

        return null;
    }

    private ArticleVO readPay(PnArticle article){
        //首先查看登陆状态
        if (UserTokenThreadHolder.isLogin()) {
            throw new BizException(StatusCode.USER_NO_LOGIN);
        }
        return null;
    }


    private ArticleVO readLogin(PnArticle article) {
        //首先查看登陆状态
        if (UserTokenThreadHolder.isLogin()) {
            throw new BizException(StatusCode.USER_NO_LOGIN);
        }
        return readNormal(article);
    }


    /**
     * 直接阅读
     */
    private ArticleVO readNormal(PnArticle article) {
        //拿到文章详情
        Long articleId = article.getId();
        PnArticleDetail articleDetail = detailMapper.getByArticleId(articleId);
        if (Objects.isNull(articleDetail)) {
            throw new BizException(StatusCode.PARAMS_ERROR);
        }
        //拿到作者相关信息
        PnUser pnUser = userMapper.selectById(article.getCreateBy());
        if (Objects.isNull(pnUser)) {
            throw new BizException(StatusCode.PARAMS_ERROR);
        }
        //todo 拿到文章与用户的相关统计信息

        //查询文章的标签信息
        List<Long> tagIds = articleTagMapper.getTagIdsByArticleId(articleId);
        List<PnTag> tags;
        if (CollectionUtils.isNotEmpty(tagIds)) {
            tags = tagMapper.selectBatchIds(tagIds);
        } else {
            tags = Lists.newArrayList();
        }
        //查询文章的专栏信息，如果有专栏的话，要推荐专栏
        PnColumnInfo columnInfo = columnInfoMapper.getByArticleId(articleId);
        //todo 专栏也有付费信息
        //查询文章的分类
        PnCatalog catalog = catalogMapper.selectById(article.getCatalogId());
        return ArticleCoverUtil.coverToArticleVo(article, articleDetail, tags, null, catalog, pnUser);
    }

}
