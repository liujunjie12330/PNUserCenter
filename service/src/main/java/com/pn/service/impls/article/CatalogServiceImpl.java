package com.pn.service.impls.article;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pn.common.vos.article.CatalogPaveVo;
import com.pn.dao.entity.PnCatalog;
import com.pn.dao.mapper.PnCatalogMapper;
import com.pn.service.CatalogService;
import com.pn.service.utils.cover.ListUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 分类
 */
@Slf4j
@Service
public class CatalogServiceImpl extends ServiceImpl<PnCatalogMapper, PnCatalog> implements CatalogService {

    @Override
    public List<CatalogPaveVo> listAllCatalog() {
        //后面更新从缓存里面获取
        List<PnCatalog> list = list();
        List<CatalogPaveVo> vos = ListUtil.coverToListVo(list, pnCatalog -> CatalogPaveVo.builder()
                .categoryName(pnCatalog.getCategoryName())
                .categoryId(pnCatalog.getId()).build());
        return vos;
    }
}
