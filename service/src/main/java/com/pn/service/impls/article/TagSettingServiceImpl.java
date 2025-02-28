package com.pn.service.impls.article;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pn.common.base.UserTokenThreadHolder;
import com.pn.common.enums.PushStatusEnum;
import com.pn.common.enums.StatusCode;
import com.pn.common.exception.BizException;
import com.pn.common.reqParams.article.TagParam;
import com.pn.common.vos.article.TagVo;
import com.pn.common.vos.login.UserVo;
import com.pn.dao.entity.PnTag;
import com.pn.dao.mapper.PnArticleTagMapper;
import com.pn.dao.mapper.PnTagMapper;
import com.pn.service.TagSettingService;
import com.pn.service.utils.SensitiveUtil;
import com.pn.service.utils.cover.ArticleCoverUtil;
import com.pn.service.utils.cover.PageUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * 标签后台接口
 */
@Service
public class TagSettingServiceImpl extends ServiceImpl<PnTagMapper, PnTag> implements TagSettingService {
    @Resource
    private PnArticleTagMapper articleTagMapper;

    @Override
    public Long save(TagParam param) {
        if (Objects.isNull(param.getTagId())) {
            return insert(param);
        }
        return update(param);
    }

    @Override
    public void delete(TagParam param) {
        PnTag pnTag = getById(param.getTagId());
        if (Objects.isNull(pnTag)) {
            throw new BizException(StatusCode.PARAMS_ERROR);
        }
        UserVo currentUser = UserTokenThreadHolder.getCurrentUser();
        if (!Objects.equals(currentUser.getId(), pnTag.getCreateBy())) {
            throw new BizException(StatusCode.NO_SUCH_PERMISSION);
        }
        removeById(param.getTagId());
    }

    @Override
    public void operate(TagParam param) {
        PnTag pnTag = getById(param.getTagId());
        if (Objects.isNull(pnTag)) {
            throw new BizException(StatusCode.PARAMS_ERROR);
        }
        UserVo currentUser = UserTokenThreadHolder.getCurrentUser();
        if (!Objects.equals(currentUser.getId(), pnTag.getCreateBy())) {
            throw new BizException(StatusCode.NO_SUCH_PERMISSION);
        }
        if (StringUtils.equalsIgnoreCase(param.getAction(), PushStatusEnum.ONLINE.getAction())) {
            pnTag.setStatus(PushStatusEnum.ONLINE.getCode());
        }
        if (StringUtils.equalsIgnoreCase(param.getAction(), PushStatusEnum.OFFLINE.getAction())) {
            pnTag.setStatus(PushStatusEnum.OFFLINE.getCode());
        }
        updateById(pnTag);
    }

    @Override
    public Page<TagVo> page(TagParam param) {
        Page<PnTag> page = new Page<PnTag>(param.getCurrent(), param.getSize());
        LambdaQueryWrapper<PnTag> wrapper = new LambdaQueryWrapper<PnTag>()
                .like(StringUtils.isNotEmpty(param.getTagName()), PnTag::getTagName, param.getTagName())
                .eq(Objects.nonNull(param.getStatus()), PnTag::getStatus, param.getStatus());
        Page<PnTag> source = page(page, wrapper);
        Page<TagVo> tagVoPage = PageUtil.coverToPageVo(source, tag -> TagVo
                .builder()
                .tagId(tag.getId())
                .tagType(tag.getTagType())
                .tagName(tag.getTagName())
                .status(tag.getStatus())
                .build());
        return tagVoPage;
    }

    private void paramCheck(TagParam param) {
        Long tagId = param.getTagId();
        if (Objects.nonNull(tagId) && tagId <= 0) {
            throw new BizException(StatusCode.PARAMS_ERROR);
        }
        Long articleId = param.getArticleId();
        if (Objects.nonNull(articleId) && articleId >= 3) {
            throw new BizException(StatusCode.PARAMS_ERROR);
        }
        String tagName = param.getTagName();
        if (StringUtils.isEmpty(tagName) && SensitiveUtil.check(tagName)) {
            throw new BizException(StatusCode.PARAMS_ERROR);
        }
        //String action = param.getAction();
    }

    private Long insert(TagParam param) {
        UserVo currentUser = UserTokenThreadHolder.getCurrentUser();
        PnTag pnTag = ArticleCoverUtil.paramCoverToPNTag(null, param, currentUser.getId());
        save(pnTag);
        return pnTag.getId();
    }

    private Long update(TagParam param) {
        PnTag pnTag = getById(param.getTagId());
        if (Objects.isNull(pnTag)) {
            throw new BizException(StatusCode.PARAMS_ERROR);
        }
        UserVo currentUser = UserTokenThreadHolder.getCurrentUser();
        if (!Objects.equals(currentUser.getId(), pnTag.getCreateBy())) {
            throw new BizException(StatusCode.NO_SUCH_PERMISSION);
        }
        pnTag = ArticleCoverUtil.paramCoverToPNTag(pnTag, param, currentUser.getId());
        updateById(pnTag);
        return pnTag.getId();
    }

}
