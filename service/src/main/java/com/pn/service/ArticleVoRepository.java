package com.pn.service;

import com.pn.common.vos.article.ArticleVO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * es article vo
 */
@Repository
public interface ArticleVoRepository extends ElasticsearchRepository<ArticleVO, Long> {
}
