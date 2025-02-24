package com.pn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pn.common.reqParams.article.ColumnParam;
import com.pn.dao.entity.PnColumnInfo;

/**
 * 专栏后台管理接口
 */
public interface ColumnSettingService extends IService<PnColumnInfo> {
    Long save(ColumnParam param);

    Long saveArticleColumn(Long columnId, Long articleId);

    void delete(Long columnId);
}
