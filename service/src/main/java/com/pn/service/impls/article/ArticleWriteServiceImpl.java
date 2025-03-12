package com.pn.service.impls.article;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.pn.common.base.UserTokenThreadHolder;
import com.pn.common.constant.PNUserCenterConstant;
import com.pn.common.enums.ArticleActionEnum;
import com.pn.common.enums.PushStatusEnum;
import com.pn.common.enums.StatusCode;
import com.pn.common.exception.BizException;
import com.pn.common.reqParams.article.ArticleSaveParams;
import com.pn.common.vos.article.ArticleIndexVo;
import com.pn.common.vos.article.ArticleVO;
import com.pn.common.vos.article.TagVo;
import com.pn.common.vos.login.UserVo;
import com.pn.dao.entity.*;
import com.pn.dao.mapper.*;
import com.pn.service.ArticleTagService;
import com.pn.service.ArticleWriteService;
import com.pn.service.impls.es.ESArticleIndexService;
import com.pn.service.impls.es.ESArticleVoService;
import com.pn.service.utils.SensitiveUtil;
import com.pn.service.utils.cover.ArticleCoverUtil;
import com.pn.service.utils.cover.ListUtil;
import com.pn.service.utils.id.IdUtil;
import io.minio.messages.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.pn.service.utils.cover.ArticleCoverUtil.paramCoverToArticleDetail;
import static com.pn.service.utils.cover.ArticleCoverUtil.paramCoverToPnArticle;

/**
 * 保存文章的相关操作
 */
@Service
@Slf4j
public class ArticleWriteServiceImpl extends ServiceImpl<PnArticleMapper, PnArticle> implements ArticleWriteService {
    @Resource
    private ArticleTagService articleTagService;

    @Resource
    private PnArticleDetailMapper articleDetailMapper;

    @Resource
    private PnTagMapper tagMapper;

    @Resource
    private PnColumnInfoMapper columnInfoMapper;

    @Resource
    private PnArticleTagMapper articleTagMapper;


    @Resource
    private PnCatalogMapper catalogMapper;

    @Resource
    private PnUserMapper userMapper;

    @Resource
    private ESArticleIndexService indexService;

    @Resource
    private ESArticleVoService voService;
    /**
     * 保存文章-->存草稿 | 直接发布待审核都走这个方法
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long save(ArticleSaveParams params) {
        paramCheck(params);
        //如果是保存
        Long articleId = null;
        UserVo currentUser = UserTokenThreadHolder.getCurrentUser();
        Long userId = currentUser.getId();
        if (Objects.isNull(params.getArticleId())) {
            articleId = insertArticle(params, userId);
            log.info("保存文章===>articleId=={},username=={},userId=={},time=={},operation=={}", articleId, currentUser.getUsername(), userId, new Date(), params.getActionType());
        }
        if (Objects.nonNull(params.getArticleId())) {
            articleId = updateArticle(params);
            log.info("更新文章===>articleId=={},username=={},userId=={},time=={},operation=={}", articleId, currentUser.getUsername(), userId, new Date(), params.getActionType());
        }
        Long finalArticleId = articleId;
        //canal不确定是否开启，用事务同步器同步一次
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            private final Long id = finalArticleId;

            @Override
            public void afterCompletion(int status) {
                if (0 != status) return;
                try {
                    PnArticle article = getById(id);
                    PnArticleDetail articleDetail = articleDetailMapper.getByArticleId(id);
                    List<Long> ids = articleTagMapper.getTagIdsByArticleId(id);
                    List<PnTag> tags = Lists.newArrayList();
                    if (CollectionUtils.isNotEmpty(ids)){
                       tags = tagMapper.selectBatchIds(ids);
                    }
                    PnColumnInfo columnInfo = columnInfoMapper.getByArticleId(id);
                    PnCatalog catalog = catalogMapper.selectById(article.getCatalogId());
                    PnUser pnUser = userMapper.selectById(article.getUserId());
                    ArticleIndexVo articleIndexVo = ArticleCoverUtil.coverTpIndexVo(null, article, tags, columnInfo, catalog, pnUser);
                    ArticleVO articleVO = ArticleCoverUtil.coverToArticleVo(null, article, articleDetail, tags, columnInfo, catalog, pnUser);
                    indexService.saveArticleIndex(articleIndexVo);
                    voService.saveArticleVo(articleVO);
                } catch (Exception e) {
                    log.error("异步插入失败:message={}", e.getMessage());
                }
            }
        });
        return articleId;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long articleId) {
        PnArticle byId = getById(articleId);

    }

    /**
     * 插入文章 首次保存草稿或者发布文章
     */
    private Long insertArticle(ArticleSaveParams params, Long userId) {
        PnArticle pnArticle = paramCoverToPnArticle(null, params, userId);
        //保存文章对象,首次创建文章，直接使用雪花算法生成文章的id
        Long id = IdUtil.genId();
        pnArticle.setId(id);
        //是否需要进行审核
        if (needToReview() && StringUtils.equalsIgnoreCase(params.getActionType(), ArticleActionEnum.POST.getAction())) {
            pnArticle.setStatus(PushStatusEnum.REVIEW.getCode());
        }
        if (StringUtils.equalsIgnoreCase(params.getActionType(), ArticleActionEnum.SAVE.getAction())) {
            pnArticle.setStatus(PushStatusEnum.OFFLINE.getCode());
        }
        //查看是否为官方账号
        if (isOffice()) {
            pnArticle.setOfficalStat(1);
        }
        save(pnArticle);
        //保存文本
        Long articleId = pnArticle.getId();
        PnArticleDetail pnArticleDetail = paramCoverToArticleDetail(null, params, articleId);
        articleDetailMapper.insert(pnArticleDetail);
        //保存文章的标签
        articleTagService.saveBatch(articleId, params.getTagIds());
        //todo 这里要初始化这个文章的相关信息
        return articleId;
    }

    /**
     * 更新文章,再次保存文章到草稿
     */
    private Long updateArticle(ArticleSaveParams params) {
        PnArticle article = getById(params.getArticleId());
        UserVo currentUser = UserTokenThreadHolder.getCurrentUser();
        //只能够修改自己的文章
        if (Objects.equals(currentUser.getId(), article.getUserId())) {
            throw new BizException(StatusCode.NO_SUCH_PERMISSION);
        }
        //正在审核的文章不能修改
        if (Objects.equals(article.getStatus(), PushStatusEnum.REVIEW.getCode())) {
            throw new BizException(StatusCode.ARTICLE_IS_REVIEWING);
        }
        article = paramCoverToPnArticle(article, params, currentUser.getId());
        //查看是否为官方账号
        if (isOffice()) {
            article.setOfficalStat(1);
        }
        //是否需要进行审核
        if (needToReview() && StringUtils.equalsIgnoreCase(params.getActionType(), ArticleActionEnum.POST.getAction())) {
            article.setStatus(PushStatusEnum.REVIEW.getCode());
        }
        if (StringUtils.equalsIgnoreCase(params.getActionType(), ArticleActionEnum.SAVE.getAction())) {
            article.setStatus(PushStatusEnum.OFFLINE.getCode());
        }
        updateById(article);
        //保存tags
        //保存文章的标签
        articleTagService.saveBatch(article.getId(), params.getTagIds());
        return article.getId();
    }

    /**
     * 是否为官方账号
     */
    private boolean isOffice() {
        UserVo currentUser = UserTokenThreadHolder.getCurrentUser();
        return currentUser.getIsAdmin() == 1;
    }

    /**
     * 查看是否需要进行审核
     */
    private boolean needToReview() {
        //管理员的不需要进行审核,后续可以加上特殊的白名单
        UserVo currentUser = UserTokenThreadHolder.getCurrentUser();
        return currentUser.getIsAdmin() == 1;
    }

    /**
     * 参数检查==>注意枚举的检查。是通过最大值来确定的
     */
    private void paramCheck(ArticleSaveParams params) {
        Long articleId = params.getArticleId();
        if (Objects.nonNull(articleId) && articleId <= 0) {
            throw new BizException(StatusCode.PARAMS_ERROR);
        }
        String title = params.getTitle();
        if (title.length() > 255) {
            throw new BizException(StatusCode.PARAMS_ERROR);
        }
        if (SensitiveUtil.check(title)) {
            throw new BizException(StatusCode.ARTICLE_HAS_SENSITIVE_WORD);
        }
        String summary = params.getSummary();
        if (summary.length() > 255) {
            throw new BizException(StatusCode.PARAMS_ERROR);
        }
        if (SensitiveUtil.check(summary)) {
            throw new BizException(StatusCode.ARTICLE_HAS_SENSITIVE_WORD);
        }
        String content = params.getContent();
        if (StringUtils.isNotEmpty(content)) {
            content = SensitiveUtil.replace(content, '*');
            params.setContent(content);
        }
        Integer status = params.getStatus();
        if (Objects.nonNull(status) && status >= 3) {
            throw new BizException(StatusCode.PARAMS_ERROR);
        }
        String actionType = params.getActionType();
        if (!PNUserCenterConstant.ARTICLE_OPERATE.contains(actionType)) {
            throw new BizException(StatusCode.PARAMS_ERROR);
        }
        Integer readType = params.getReadType();
        if (readType >= 5) {
            throw new BizException(StatusCode.PARAMS_ERROR);
        }
    }

}
