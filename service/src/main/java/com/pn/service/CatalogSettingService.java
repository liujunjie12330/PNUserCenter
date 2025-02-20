package com.pn.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pn.common.reqParams.article.CatalogParam;
import com.pn.common.vos.article.CatalogPaveVo;
import com.pn.dao.entity.PnCatalog;

/**
 * 类目后台接口
 */
public interface CatalogSettingService extends IService<PnCatalog> {

    Long save(CatalogParam param);

    void delete(CatalogParam param);

    void operate(CatalogParam param);

    Page<CatalogPaveVo> page(CatalogParam param);
}
