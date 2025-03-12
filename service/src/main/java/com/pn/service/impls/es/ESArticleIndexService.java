package com.pn.service.impls.es;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pn.common.reqParams.article.ArticleIndexParam;
import com.pn.common.vos.article.ArticleIndexVo;
import com.pn.service.ArticleIndexRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 文章索引服务类，负责与 Elasticsearch 进行交互
 */
@Service
public class ESArticleIndexService {

    @Resource
    private ArticleIndexRepository articleIndexRepository;

    @Resource
    private ElasticsearchRestTemplate template;

    /**
     * 保存或更新文章索引数据
     *
     * @param articleIndexVo 文章索引对象
     * @return 保存后的文章索引
     */
    public ArticleIndexVo saveArticleIndex(ArticleIndexVo articleIndexVo) {
        return articleIndexRepository.save(articleIndexVo);
    }

    /**
     * 根据文章 ID 查找文章索引数据
     *
     * @param articleId 文章 ID
     * @return 文章索引数据
     */
    public ArticleIndexVo findByArticleId(Long articleId) {
        return articleIndexRepository.findById(articleId).orElse(null);
    }

    /**
     * 删除指定文章 ID 的文章索引
     *
     * @param articleId 文章 ID
     */
    public void deleteArticleIndex(Long articleId) {
        articleIndexRepository.deleteById(articleId);
    }

    /**
     * 列表分页查询
     *
     * @param param 分页查询条件
     */
    public Page<ArticleIndexVo> search(ArticleIndexParam param) {
        String search = param.getSearch();
        Long catalogId = param.getCatalogId();
        Long tagId = param.getTagId();
        Long columnId = param.getColumnId();
        Long articleId = param.getArticleId();
        Long userId = param.getUserId();
        Long size = param.getSize();
        Long current = param.getCurrent();
        // 构建查询条件
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        //search要进行分词查询
        if (StringUtils.isNotEmpty(search)) {
            boolQuery.must(QueryBuilders.multiMatchQuery(search,
                            "title", "summary", "authorName", "shortTitle", "columnName", "catalogName", "tagName")
                    .analyzer("ik_smart")
                    .operator(Operator.OR)
                    .minimumShouldMatch("60%"));
        }
        if (Objects.nonNull(catalogId)) {
            boolQuery.filter(QueryBuilders.termQuery("catalogId", catalogId));
        }
        if (Objects.nonNull(tagId)) {
            boolQuery.filter(QueryBuilders.termQuery("tagId", tagId));
        }
        if (Objects.nonNull(columnId)) {
            boolQuery.filter(QueryBuilders.termQuery("columnId", columnId));
        }
        if (Objects.nonNull(articleId)) {
            boolQuery.filter(QueryBuilders.termQuery("articleId", articleId));
        }
        if (Objects.nonNull(userId)) {
            boolQuery.filter(QueryBuilders.termQuery("userId", userId));
        }


        // 分页参数
        PageRequest pageRequest = PageRequest.of(current.intValue(), size.intValue());

        // 构建查询
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(boolQuery)
                .withSorts(SortBuilders.fieldSort("toppingStat").order(SortOrder.DESC))
                .withPageable(pageRequest)
                .build();

        // 执行查询
        SearchHits<ArticleIndexVo> searchHits = template.search(searchQuery, ArticleIndexVo.class);

        // 转换为 Page 对象
        List<ArticleIndexVo> articles = searchHits.getSearchHits().stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
        Page<ArticleIndexVo> voPage = new Page<>(current, size, searchHits.getTotalHits());
        voPage.setRecords(articles);
        return voPage;
    }
}
