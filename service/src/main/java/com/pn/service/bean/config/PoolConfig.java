package com.pn.service.bean.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
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
     * binlog 回调处理线程池--后续缓存失效可能会用
     */
    public static final ExecutorService COLLECT_POOL = new ThreadPoolExecutor(10, 10,
            10, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(5000),
            new ReceiveThreadFactory("binlog-thread"));

    /**
     * 定时任务线程池
     */
    public static final ExecutorService SCHEDULED_SYNC_POOL = new ThreadPoolExecutor(5, 5,
            10, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(500),
            new ReceiveThreadFactory("scheduled-task-thread"));

    /**
     * 异步任务执行线程池
     */
    public static final ExecutorService RUN_SYNC_JOB_POOL = new ThreadPoolExecutor(5, 5,
            10, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(500),
            new ReceiveThreadFactory("asynchronous-task-thread"));

    /**
     * 更新数据源、catalog、db 默认生命周期线程池, 线程数设置为 5
     */
    public static final ExecutorService UPDATE_TABLE_LIFECYCLE_POOL = new ThreadPoolExecutor(5, 5,
            10, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(500),
            new ReceiveThreadFactory("update-table-lifecycle-thread"));


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
