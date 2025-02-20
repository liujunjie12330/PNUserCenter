package com.pn.service.impls.article;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pn.common.enums.StatusCode;
import com.pn.common.exception.BizException;
import com.pn.common.reqParams.article.CatalogParam;
import com.pn.common.vos.article.CatalogPaveVo;
import com.pn.dao.entity.PnCatalog;
import com.pn.dao.mapper.PnCatalogMapper;
import com.pn.service.CatalogSettingService;
import com.pn.service.utils.cover.PageUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * 类目后台接口
 */
@Service
@Slf4j
public class CatalogSettingServiceImpl extends ServiceImpl<PnCatalogMapper, PnCatalog> implements CatalogSettingService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long save(CatalogParam param) {
        if (Objects.isNull(param.getCatalogId())) {
            return insert(param);
        } else {
            return update(param);
        }
    }

    @Override
    public void delete(CatalogParam param) {
        PnCatalog catalog = getById(param);
        if (Objects.isNull(catalog)) {
            throw new BizException(StatusCode.PARAMS_ERROR);
        }
        removeById(catalog.getId());
    }

    @Override
    public void operate(CatalogParam param) {
        PnCatalog catalog = getById(param.getCatalogId());
        if (Objects.isNull(catalog)) {
            throw new BizException(StatusCode.PARAMS_ERROR);
        }
        catalog.setStatus(param.getStatus());
        updateById(catalog);
    }

    @Override
    public Page<CatalogPaveVo> page(CatalogParam param) {
        Page<PnCatalog> catalogPage = new Page<>(param.getCurrent(), param.getSize());
        LambdaQueryWrapper<PnCatalog> queryWrapper = new LambdaQueryWrapper<PnCatalog>()
                .like(StringUtils.isNotEmpty(param.getCategoryName()), PnCatalog::getCategoryName, param.getCategoryName())
                .orderByAsc(PnCatalog::getRank);
        Page<PnCatalog> page = page(catalogPage, queryWrapper);
        Page<CatalogPaveVo> voPage = PageUtil.coverToPageVo(page, pnCatalog -> CatalogPaveVo.builder()
                .rank(pnCatalog.getRank())
                .status(pnCatalog.getStatus())
                .categoryId(pnCatalog.getId())
                .categoryName(pnCatalog.getCategoryName())
                .build());
        return voPage;
    }


    private Long insert(CatalogParam param) {
        PnCatalog pnCatalog = new PnCatalog();
        pnCatalog.setCategoryName(param.getCategoryName());
        pnCatalog.setRank(param.getRank());
        save(pnCatalog);
        return pnCatalog.getId();
    }

    private Long update(CatalogParam param) {
        PnCatalog catalog = getById(param.getCatalogId());
        if (Objects.isNull(catalog)) {
            throw new BizException(StatusCode.PARAMS_ERROR);
        }
        catalog.setCategoryName(param.getCategoryName());
        catalog.setRank(param.getRank());
        catalog.setStatus(0);
        updateById(catalog);
        return catalog.getId();
    }
}
