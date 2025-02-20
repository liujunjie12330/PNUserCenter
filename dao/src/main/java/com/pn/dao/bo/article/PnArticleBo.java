package com.pn.dao.bo.article;

import com.pn.dao.entity.PnArticle;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author javadadi
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PnArticleBo extends PnArticle {
    private static final long serialVersionUID = 7857216173403829362L;
    /**
     * 作者姓名
     */
    private String username;
}
