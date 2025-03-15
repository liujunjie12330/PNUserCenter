package com.pn.service.impls.article;

import cn.hutool.core.bean.BeanUtil;
import com.alipay.api.AlipayApiException;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.pn.common.base.UserTokenThreadHolder;
import com.pn.common.enums.StatusCode;
import com.pn.common.exception.BizException;
import com.pn.common.reqParams.article.ArticleIndexParam;
import com.pn.common.vos.article.ArticleIndexVo;
import com.pn.common.vos.article.ArticleVO;
import com.pn.common.vos.login.UserVo;
import com.pn.dao.bo.article.ArticleIndexBo;
import com.pn.dao.entity.*;
import com.pn.dao.mapper.*;
import com.pn.service.ArticlePayService;
import com.pn.service.ArticleReadService;
import com.pn.service.impls.es.ESArticleIndexService;
import com.pn.service.impls.es.ESArticleVoService;
import com.pn.service.utils.cover.ArticleCoverUtil;
import com.pn.service.utils.cover.PageUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

import static com.pn.common.enums.ArticleReadTypeEnum.*;

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

    @Resource
    private ArticlePayService articlePayService;

    @Resource
    private ESArticleIndexService esArticleIndexService;

    @Resource
    private ESArticleVoService voService;

    /**
     * 首页文章分页
     * @param param param 查询参数
     * @return Page<ArticleIndexVo> 分页数据
     */
    @Override
    public Page<ArticleIndexVo> page(ArticleIndexParam param) {
        Page<ArticleIndexVo> pageVo;
        Page<ArticleIndexVo> search = esArticleIndexService.search(param);
        if (Objects.nonNull(search) && CollectionUtils.isEmpty(search.getRecords())) {
            Page<ArticleIndexBo> page = new Page<>(param.getCurrent(), param.getSize());
            Page<ArticleIndexBo> indexBoPage = mapper.indexPage(page, param);
            //从redis取出文章的相关数据,后面这里优化成从es里面那数据
            pageVo = PageUtil.coverToPageVo(indexBoPage, bo -> {
                ArticleIndexVo articleIndexVo = new ArticleIndexVo();
                BeanUtil.copyProperties(bo, articleIndexVo);
                return articleIndexVo;
            });
        } else {
            pageVo = search;
        }
        return pageVo;
    }

    /**
     * 文章是否支付
     * @param articleId articleId
     * @return Boolean 是否支付
     */
    @Override
    public Boolean isPaid(Long articleId) {
        UserVo currentUser = UserTokenThreadHolder.getCurrentUser();
        //现在redis里面查存不存在
        return articlePayService.isPaid(articleId, currentUser.getId());
    }

    /**
     * 阅读文章
     * @param id id
     * @return ArticleVO 文章详情
     * @throws AlipayApiException AlipayApiException
     */
    @Override
    public ArticleVO read(Long id) throws AlipayApiException {
        //首先查找文章是否存在
        PnArticle article = getById(id);
        if (Objects.isNull(article)) {
            throw new BizException(StatusCode.PARAMS_ERROR);
        }
        //如果文章存在，查看文章的阅读状态
        Integer readType = article.getReadType();
        //如果是文章的作者，直接阅读
        if (Objects.equals(article.getUserId(), UserTokenThreadHolder.getCurrentUser().getId())) {
            return readNormal(article);
        }
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
        if (Objects.equals(readType, PAY_READ.getType())) {
            return readPay(article);
        }
        return null;
    }

    /**
     * 支付阅读
     * @param article article
     * @return articleVO
     * @throws AlipayApiException AlipayApiException
     */
    private ArticleVO readPay(PnArticle article) throws AlipayApiException {
        //首先查看登陆状态
        if (!UserTokenThreadHolder.isLogin()) {
            throw new BizException(StatusCode.USER_NO_LOGIN);
        }
        //查询是否支付过
        UserVo currentUser = UserTokenThreadHolder.getCurrentUser();
        if (articlePayService.isPaid(article.getId(), currentUser.getId())) {
            return readNormal(article);
        }
        String url = articlePayService.payArticle(article.getId());
        if (StringUtils.isEmpty(url)) {
            throw new BizException(StatusCode.SYSTEM_ERROR);
        }
        ArticleVO vo = new ArticleVO();
        vo.setUrl(url);
        vo.setIsNeedTpPay(true);
        return vo;
    }

    /**
     * 登陆阅读
     * @param article article
     * @return articleVO
     */
    private ArticleVO readLogin(PnArticle article) {
        //首先查看登陆状态
        if (!UserTokenThreadHolder.isLogin()) {
            throw new BizException(StatusCode.USER_NO_LOGIN);
        }
        return readNormal(article);
    }


    /**
     * 直接阅读
     */
    private ArticleVO readNormal(PnArticle article) {
        ArticleVO articleVO = voService.getByArticleId(article.getId());
        if (Objects.isNull(articleVO)) {
            //拿到文章详情
            Long articleId = article.getId();
            PnArticleDetail articleDetail = detailMapper.getByArticleId(articleId);
            if (Objects.isNull(articleDetail)) {
                throw new BizException(StatusCode.PARAMS_ERROR);
            }
            //拿到作者相关信息
            PnUser pnUser = userMapper.selectById(article.getUserId());
            if (Objects.isNull(pnUser)) {
                throw new BizException(StatusCode.PARAMS_ERROR);
            }
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
            //查询文章的分类
            PnCatalog catalog = catalogMapper.selectById(article.getCatalogId());
            articleVO = ArticleCoverUtil.coverToArticleVo(article, articleDetail, tags, null, catalog, pnUser);
        }

        return articleVO;
    }

}
