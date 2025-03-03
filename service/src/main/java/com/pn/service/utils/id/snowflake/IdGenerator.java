package com.pn.service.utils.id.snowflake;

/**
 * @author javadadi
 */
public interface IdGenerator {
    /**
     * 生成分布式id
     */
    Long nextId();
}
