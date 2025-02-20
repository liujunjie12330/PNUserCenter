package com.pn.service.impls.article;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pn.common.base.UserTokenThreadHolder;
import com.pn.common.enums.StatusCode;
import com.pn.common.exception.BizException;
import com.pn.common.reqParams.article.ColumnParam;
import com.pn.common.vos.login.UserVo;
import com.pn.dao.entity.PnArticle;
import com.pn.dao.entity.PnColumnArticle;
import com.pn.dao.entity.PnColumnInfo;
import com.pn.dao.mapper.PnArticleMapper;
import com.pn.dao.mapper.PnColumnArticleMapper;
import com.pn.dao.mapper.PnColumnInfoMapper;
import com.pn.service.ColumnSettingService;
import com.pn.service.utils.SensitiveUtil;
import com.pn.service.utils.cover.ArticleCoverUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;

/**
 * 专栏后台接口
 */
@Service
public class ColumnSettingServiceImpl extends ServiceImpl<PnColumnInfoMapper, PnColumnInfo> implements ColumnSettingService {

    @Resource
    private PnArticleMapper articleMapper;

    @Resource
    private PnColumnArticleMapper columnArticleMapper;

    @Override
    public Long save(ColumnParam param) {
        paramCheck(param);
        if (Objects.isNull(param.getColumnId())) {
            return insert(param);
        }
        return update(param);
    }

    @Override
    public Long saveArticleColumn(Long columnId, Long articleId) {
        PnColumnInfo columnInfo = getById(columnId);
        if (Objects.isNull(columnInfo)) {
            throw new BizException(StatusCode.PARAMS_ERROR);
        }
        PnArticle article = articleMapper.selectById(articleId);
        if (Objects.isNull(article)) {
            throw new BizException(StatusCode.PARAMS_ERROR);
        }
        UserVo currentUser = UserTokenThreadHolder.getCurrentUser();
        //现在只有自己的文章才能够设置到自己创建的专栏里面
        if (Objects.equals(columnInfo.getUserId(), currentUser.getId()) ||
                Objects.equals(columnInfo.getUserId(), article.getUserId())) {
            throw new BizException(StatusCode.NO_SUCH_PERMISSION);
        }
        PnColumnArticle pnColumnArticle = ArticleCoverUtil.paramCoverToCA(articleId, columnId);
        columnArticleMapper.insert(pnColumnArticle);
        return pnColumnArticle.getArticleId();
    }

    public void delete(Long columnId) {
        PnColumnInfo columnInfo = getById(columnId);
        if (Objects.isNull(columnInfo)) {
            throw new BizException(StatusCode.PARAMS_ERROR);
        }
        UserVo currentUser = UserTokenThreadHolder.getCurrentUser();
        if (!Objects.equals(columnInfo.getUserId(), currentUser.getId())) {
            throw new BizException(StatusCode.NO_SUCH_PERMISSION);
        }
        //查询专栏下是否有文章，存在的时候不能删除
        LambdaQueryWrapper<PnColumnArticle> wrapper = new LambdaQueryWrapper<PnColumnArticle>().eq(PnColumnArticle::getColumnId, columnId);
        Long count = columnArticleMapper.selectCount(wrapper);
        if (count > 0) {
            throw new BizException(StatusCode.COLUMN_HAS_ARTICLE);
        }
        removeById(columnId);
    }

    private Long insert(ColumnParam param) {
        UserVo currentUser = UserTokenThreadHolder.getCurrentUser();
        PnColumnInfo columnInfo = ArticleCoverUtil.paramCoverToColumnInfo(null, param, currentUser.getId());
        save(columnInfo);
        return columnInfo.getId();
    }

    private Long update(ColumnParam param) {
        UserVo currentUser = UserTokenThreadHolder.getCurrentUser();
        PnColumnInfo columnInfo = getById(param.getColumnId());
        if (Objects.isNull(columnInfo)) {
            throw new BizException(StatusCode.PARAMS_ERROR);
        }
        if (!Objects.equals(columnInfo.getId(), columnInfo.getUserId())) {
            throw new BizException(StatusCode.NO_SUCH_PERMISSION);
        }
        columnInfo = ArticleCoverUtil.paramCoverToColumnInfo(null, param, currentUser.getId());
        updateById(columnInfo);
        return columnInfo.getId();
    }

    private void paramCheck(ColumnParam param) {
        Long columnId = param.getColumnId();
        if (columnId != null && columnId <= 0) {
            throw new BizException(StatusCode.PARAMS_ERROR);
        }
        String column = param.getColumn();
        if (StringUtils.isNotEmpty(column) && SensitiveUtil.check(column)) {
            throw new BizException(StatusCode.ARTICLE_HAS_SENSITIVE_WORD);
        }
        String introduction = param.getIntroduction();
        if (StringUtils.isNotEmpty(introduction) && SensitiveUtil.check(introduction)) {
            throw new BizException(StatusCode.ARTICLE_HAS_SENSITIVE_WORD);
        }
        Integer state = param.getState();
        if (state >= 3) {
            throw new BizException(StatusCode.PARAMS_ERROR);
        }
        Integer nums = param.getNums();
        if (nums <= 0) {
            throw new BizException(StatusCode.PARAMS_ERROR);
        }
        Integer type = param.getType();
        if (type >= 3) {
            throw new BizException(StatusCode.PARAMS_ERROR);
        }
        Date freeStartTime = param.getFreeStartTime();
        Date freeEndTime = param.getFreeEndTime();
        if (Objects.nonNull(freeEndTime) && Objects.nonNull(freeStartTime) && freeEndTime.before(freeStartTime)) {
            throw new BizException(StatusCode.PARAMS_ERROR);
        }
    }
}
