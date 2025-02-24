package com.pn.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pn.common.reqParams.article.TagParam;
import com.pn.common.vos.article.TagVo;
import com.pn.dao.entity.PnTag;

/**
 * 标签后台接口
 */
public interface TagSettingService extends IService<PnTag> {
    Long save(TagParam param);

    void delete(TagParam param);

    void operate(TagParam param);

    Page<TagVo> page(TagParam param);
}
