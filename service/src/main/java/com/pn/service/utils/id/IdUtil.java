package com.pn.service.utils.id;

import com.pn.common.enums.ThirdPayWayEnum;

import com.pn.service.utils.DateUtil;
import com.pn.service.utils.id.snowflake.PaiSnowflakeIdGenerator;
import com.pn.service.utils.id.snowflake.SnowflakeProducer;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.atomic.AtomicLong;

import static com.pn.service.utils.CompressUtil.int2str;


/**
 * @author javadadi
 */
public class IdUtil {
    /**
     * 默认的id生成器
     */
    public static SnowflakeProducer DEFAULT_ID_PRODUCER = new SnowflakeProducer(new PaiSnowflakeIdGenerator());

    private static AtomicLong INCR = new AtomicLong((int) (Math.random() * 500));

    private static long lastTime = 0;


    /**
     * 生成全局id
     */
    public static Long genId() {
        return DEFAULT_ID_PRODUCER.genId();
    }

    /**
     * 生成字符串格式全局id
     */
    public static String genStrId() {
        return int2str(genId());
    }


    /**
     * 生成支付的唯一code
     */
    public static String genPayCode(ThirdPayWayEnum payWay, Long id) {
        long now = System.currentTimeMillis();
        if (DateUtil.skipDay(lastTime, now)) {
            lastTime = now;
            INCR.set((int) (Math.random() * 500));
        }
        // 生成唯一支付码
        String uniqueCode = String.format("%06d", INCR.addAndGet(1));
        return payWay.getPrefix() + uniqueCode + "_" + id;
    }

    /**
     * 解析支付code中的 id
     */
    public static Long parseIdFromPayCode(String payCode) {
        if (payCode == null || !payCode.contains("_")) {
            throw new IllegalArgumentException("非法的支付码格式");
        }
        // 提取最后的部分，即 id
        String[] parts = payCode.split("_");
        return Long.parseLong(parts[1]);
    }
}
