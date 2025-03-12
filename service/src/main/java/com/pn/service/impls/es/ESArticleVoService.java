package com.pn.service.impls.es;

import com.pn.common.vos.article.ArticleVO;
import com.pn.service.ArticleVoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * 文章 ES 操作服务
 */
@Service
@Slf4j
public class ESArticleVoService {

    @Resource
    private ArticleVoRepository repository;

    /**
     * 根据文章 ID 获取文章详情
     *
     * @param articleId 文章 ID
     * @return 文章详情，若不存在则返回 null
     */
    public ArticleVO getByArticleId(Long articleId) {
        return repository.findById(articleId).orElse(null);
    }

    /**
     * 保存或更新文章（ID 存在则更新，不存在则新增）
     *
     * @param articleVO 文章对象
     * @return 保存后的文章对象
     */
    public ArticleVO saveArticleVo(ArticleVO articleVO) {
        log.info("保存或更新文章: {}", articleVO);
        return repository.save(articleVO);
    }

    /**
     * 批量保存或更新文章
     *
     * @param articles 文章列表
     * @return 保存后的文章列表
     */
    public List<ArticleVO> saveAll(List<ArticleVO> articles) {
        log.info("批量保存或更新文章, 数量: {}", articles.size());
        return (List<ArticleVO>) repository.saveAll(articles);
    }

    /**
     * 根据文章 ID 删除文章
     *
     * @param articleId 文章 ID
     */
    public void deleteById(Long articleId) {
        log.info("删除文章 ID: {}", articleId);
        repository.deleteById(articleId);
    }

    /**
     * 批量删除文章
     *
     * @param articleIds 文章 ID 列表
     */
    public void deleteByIds(List<Long> articleIds) {
        log.info("批量删除文章, 数量: {}", articleIds.size());
        articleIds.forEach(repository::deleteById);
    }

    /**
     * 判断文章是否存在
     *
     * @param articleId 文章 ID
     * @return 存在返回 true，否则返回 false
     */
    public boolean exists(Long articleId) {
        return repository.existsById(articleId);
    }
}
