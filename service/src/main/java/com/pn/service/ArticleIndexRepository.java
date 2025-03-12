package com.pn.service;

import com.pn.common.vos.article.ArticleIndexVo;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

/**
 * Elasticsearch 文章索引 Repository
 */
@Repository
public interface ArticleIndexRepository extends ElasticsearchRepository<ArticleIndexVo, Long> {

    /**
     * 根据标题查询文章索引
     * 
     * @param title 文章标题
     * @param pageRequest 分页参数
     * @return 文章索引数据列表
     */
    List<ArticleIndexVo> findByTitleContaining(String title, PageRequest pageRequest);

    /**
     * 根据标签 ID 查询文章索引
     *
     * @param tagId 标签 ID
     * @param pageRequest 分页参数
     * @return 文章索引数据列表
     */
    List<ArticleIndexVo> findByTagVos_TagId(Long tagId, PageRequest pageRequest);

    /**
     * 根据栏目 ID 查询文章索引
     *
     * @param columnId 栏目 ID
     * @param pageRequest 分页参数
     * @return 文章索引数据列表
     */
    List<ArticleIndexVo> findByColumnId(Long columnId, PageRequest pageRequest);

}
