package com.hzh.springboot.pool;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.*;

/**
 * @author wkp
 * @description 动态线程池注册器&运行监听器
 * @create 2023-04-13
 */
@Slf4j
public class DynamicThreadPoolRegister {
    public static final String coreSize = "coreSize";
    public static final String maxSize = "maxSize";
    private static final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    //存放动态线程池实例,key为threadPoolId
    private static Map<String, DynamicThreadPool> threadPoolMap = new ConcurrentHashMap<>();

    static {
        //后台定时任务监控线程池运行状态,可以进行预警
        scheduledExecutorService.scheduleAtFixedRate(()->{
            Set<String> set = threadPoolMap.keySet();
            for(String threadPoolId:set){
                DynamicThreadPool pool = threadPoolMap.get(threadPoolId);
                //线程池最大线程数
                int maximumPoolSize = pool.getMaximumPoolSize();
                //线程池当前线程数
                int poolSize = pool.getPoolSize();
                //活跃线程数
                int activeCount = pool.getActiveCount();
                BlockingQueue<Runnable> queue = pool.getQueue();
                //队列元素个数
                int queueSize = queue.size();
                //队列剩余容量
                int remainingCapacity = queue.remainingCapacity();
                //队列容量
                int queueCapacity = queueSize + remainingCapacity;
                //TODO 根据配置的活跃线程数或者队列任务数量阈值进行预警
                //TODO 预警阈值可以改成动态配置
                if(poolSize>maximumPoolSize*0.5){
                    log.info("线程池负载过高了,poolSize:{},maximumPoolSize{}",threadPoolId,poolSize,maximumPoolSize);
                }
                if(queueSize>queueCapacity*0.5){
                    log.info("线程池任务堆积了,queueSize:{},queueCapacity{}",threadPoolId,queueSize,queueCapacity);
                }
            }
            log.info("线程池监控定时任务,threadPoolSize:{}",threadPoolMap.size());
        },5,10,TimeUnit.SECONDS);
    }

    public static ThreadPoolExecutor buildThreadPool(String threadPoolId, long executeTimeOut, int corePoolSize, int maximumPoolSize) {
        return buildThreadPool(threadPoolId, executeTimeOut, corePoolSize, maximumPoolSize, 60,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(10), Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy());
    }

    public static ThreadPoolExecutor buildThreadPool(String threadPoolId, long executeTimeOut, int corePoolSize, int maximumPoolSize,
                                                     long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue,
                                                     ThreadFactory threadFactory, RejectedExecutionHandler redundancyHandler) {
        DynamicThreadPool dynamicThreadPool = new DynamicThreadPool(threadPoolId, executeTimeOut, corePoolSize,
                maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, redundancyHandler);
        threadPoolMap.put(threadPoolId, dynamicThreadPool);
        return dynamicThreadPool;
    }

    /**
     * 刷新线程池配置
     * @param threadPoolConfig
     */
    public static void refreshThreadPool(Map<String, Map<String, Integer>> threadPoolConfig) {
        if (MapUtils.isEmpty(threadPoolConfig)) {
            return;
        }
        Set<String> set = threadPoolConfig.keySet();
        for (String threadPoolId : set) {
            DynamicThreadPool dynamicThreadPool = threadPoolMap.get(threadPoolId);
            if (Objects.isNull(dynamicThreadPool)) {
                continue;
            }
            Map<String, Integer> propertyMap = threadPoolConfig.get(threadPoolId);
            //根据配置修改线程池的线程数,队列,拒绝策略等等
            dynamicThreadPool.setCorePoolSize(propertyMap.get(coreSize));
            dynamicThreadPool.setMaximumPoolSize(propertyMap.get(maxSize));
        }
    }
}