package com.pn.service.impls.article;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.pn.common.enums.PushStatusEnum;
import com.pn.common.enums.StatusCode;
import com.pn.common.exception.BizException;
import com.pn.common.reqParams.article.TagParam;
import com.pn.common.vos.article.TagVo;
import com.pn.dao.entity.PnArticleTag;
import com.pn.dao.entity.PnTag;
import com.pn.dao.mapper.PnArticleTagMapper;
import com.pn.dao.mapper.PnTagMapper;
import com.pn.service.ArticleTagService;
import com.pn.service.utils.cover.PageUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;

/**
 * 标签管理
 */
@Service
@Slf4j
public class ArticleTagServiceImpl extends ServiceImpl<PnArticleTagMapper, PnArticleTag> implements ArticleTagService {

    @Resource
    private PnTagMapper pnTagMapper;

    @Override
    public void saveBatch(Long articleId, Set<Long> tagIds) {
        if (CollectionUtils.isEmpty(tagIds)) {
            return;
        }
        ArrayList<PnArticleTag> articleTags = Lists.newArrayList();
        tagIds.forEach(m -> {
            PnArticleTag pnArticleTag = new PnArticleTag();
            pnArticleTag.setArticleId(articleId);
            pnArticleTag.setTagId(m);
            articleTags.add(pnArticleTag);
        });
        saveBatch(articleTags);
    }

    @Override
    public Long save(TagParam param) {
        if (Objects.isNull(param.getTagId())) {
            return insert(param);
        }
        return update(param);
    }

    @Override
    public void operate(TagParam param) {
        PnTag pnTag = pnTagMapper.selectById(param.getTagId());
        if (Objects.isNull(pnTag)) {
            throw new BizException(StatusCode.PARAMS_ERROR);
        }
        //下线操作
        if (StringUtils.equalsIgnoreCase(param.getAction(), PushStatusEnum.ONLINE.getAction())) {
            pnTag.setStatus(PushStatusEnum.OFFLINE.getCode());
        }
        //上线操作
        if (StringUtils.equalsIgnoreCase(param.getAction(), PushStatusEnum.ONLINE.getAction())) {
            pnTag.setStatus(PushStatusEnum.ONLINE.getCode());
        }
        pnTagMapper.updateById(pnTag);
    }

    @Override
    public Page<TagVo> page(TagParam param){
        Page<PnTag> page = new Page<>(param.getCurrent(), param.getSize());
        LambdaQueryWrapper<PnTag> wrapper = new LambdaQueryWrapper<PnTag>()
                .eq(Objects.nonNull(param.getTagId()), PnTag::getId, param.getTagId())
                .like(StringUtils.isNotEmpty(param.getTagName()), PnTag::getTagName, param.getTagName());
        Page<PnTag> sourcePage = pnTagMapper.selectPage(page, wrapper);
        Page<TagVo> tagVoPage = PageUtil.coverToPageVo(sourcePage, tag -> TagVo.builder()
                .tagName(tag.getTagName())
                .status(tag.getStatus())
                .tagType(tag.getTagType())
                .build());
        return tagVoPage;
    }

    private Long insert(TagParam param) {
        PnTag pnTag = new PnTag();
        pnTag.setStatus(PushStatusEnum.OFFLINE.getCode());
        pnTag.setTagName(pnTag.getTagName());
        //系统自定义的只能在初始化数据添加
        pnTag.setTagType(2);
        pnTagMapper.insert(pnTag);
        return pnTag.getId();
    }

    private Long update(TagParam param) {
        PnTag tag = pnTagMapper.selectById(param.getTagId());
        if (Objects.isNull(tag)) {
            throw new BizException(StatusCode.PARAMS_ERROR);
        }
        tag.setTagName(param.getTagName());
        pnTagMapper.updateById(tag);
        return tag.getId();
    }
}
