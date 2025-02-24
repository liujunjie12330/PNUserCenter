package com.pn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pn.common.vos.article.CatalogPaveVo;
import com.pn.dao.entity.PnCatalog;

import java.util.List;

/**
 * 分类接口
 */
public interface CatalogService extends IService<PnCatalog> {
    List<CatalogPaveVo> listAllCatalog();
}
