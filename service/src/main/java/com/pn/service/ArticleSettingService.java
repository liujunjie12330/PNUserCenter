package com.pn.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pn.common.reqParams.article.ArticleAdminParam;
import com.pn.common.vos.article.ArticleAdminVo;

/**
 * 文章后台接口
 */
public interface ArticleSettingService {

    Page<ArticleAdminVo> page(ArticleAdminParam param);
}
