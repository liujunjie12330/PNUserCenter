package com.pn.service.impls.article;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pn.common.reqParams.article.ArticleAdminParam;
import com.pn.common.vos.article.ArticleAdminVo;
import com.pn.dao.bo.article.PnArticleBo;
import com.pn.dao.mapper.PnArticleDetailMapper;
import com.pn.dao.mapper.PnArticleMapper;
import com.pn.service.ArticleSettingService;
import com.pn.service.utils.cover.PageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 文章管理员接口
 *
 * @author javadadi
 */
@Service
@Slf4j
public class ArticleSettingServiceImpl implements ArticleSettingService {
    @Resource
    private PnArticleDetailMapper detailMapper;

    @Resource
    private PnArticleMapper articleMapper;

    @Override
    public Page<ArticleAdminVo> page(ArticleAdminParam param) {
        Page<PnArticleBo> page = new Page<>(param.getCurrent(), param.getSize());
        Page<PnArticleBo> articleBoPage = articleMapper.page(page, param);
        Page<ArticleAdminVo> pageVo = PageUtil.coverToPageVo(articleBoPage, pnArticleBo -> ArticleAdminVo.builder()
                .articleId(pnArticleBo.getId())
                .authorId(pnArticleBo.getUserId())
                .authorName(pnArticleBo.getUsername())
                .title(pnArticleBo.getTitle())
                .shortTitle(pnArticleBo.getShortTitle())
                .cover(pnArticleBo.getPicture())
                .status(pnArticleBo.getStatus())
                .officalStat(pnArticleBo.getOfficalStat())
                .toppingStat(pnArticleBo.getToppingStat())
                .creamStat(pnArticleBo.getRecommend())
                .updateTime(pnArticleBo.getUpdateAt())
                .build());
        return pageVo;
    }

}
