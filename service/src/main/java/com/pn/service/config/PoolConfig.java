package com.pn.service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author javadadi
 * 2024-09-12
 */
@Configuration
public class PoolConfig {

    @Value(value = "${sync.pool.size:5}")
    private Integer syncPoolSize;

    @Bean("syncPool")
    public ExecutorService buildSyncPool() {
        // 默认线程为 10
        return new ThreadPoolExecutor(syncPoolSize, syncPoolSize,
                10, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(500),
                new ReceiveThreadFactory("sync-thread"));
    }

    /**
     * 默认线程池
     */
    public static final ExecutorService DEFAULT_POOL = new ThreadPoolExecutor(10, 10,
            10, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(5000),
            new ReceiveThreadFactory("binlog-thread"));
    private static class ReceiveThreadFactory implements ThreadFactory {
        private final String threadName;

        ReceiveThreadFactory(String threadName) {
            this.threadName = threadName;
        }

        private final AtomicInteger count = new AtomicInteger(0);

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            String newThreadName = this.threadName + count.addAndGet(1);
            t.setName(newThreadName);
            return t;
        }
    }
}
