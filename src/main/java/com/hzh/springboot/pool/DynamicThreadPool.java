package com.hzh.springboot.pool;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Proxy;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author wangkp
 * @description 自定义动态线程池
 * @create 2023-04-13 10:17
 */
@Getter
@Setter
@Slf4j
public class DynamicThreadPool extends ThreadPoolExecutor {
    private String threadPoolId;
    private final AtomicLong rejectCount = new AtomicLong();
    private Long executeTimeOut;
    private final ThreadLocal<Long> executeTimeThreadLocal = new ThreadLocal<>();

    /**
     * 动态线程池构造方法
     *
     * @param threadPoolId             动态线程池唯一标识
     * @param executeTimeOut           线程任务执行超时时间
     * @param corePoolSize             核心线程池数量
     * @param maximumPoolSize          最大线程池数量
     * @param keepAliveTime            空闲线程活跃时间
     * @param unit                     时间单位
     * @param workQueue                任务队列
     * @param threadFactory            线程工厂
     * @param rejectedExecutionHandler 线程池拒绝策略
     */
    public DynamicThreadPool(String threadPoolId, long executeTimeOut, int corePoolSize, int maximumPoolSize,
                             long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue,
                             ThreadFactory threadFactory, RejectedExecutionHandler rejectedExecutionHandler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, rejectedExecutionHandler);
        this.threadPoolId = threadPoolId;
        this.executeTimeOut = executeTimeOut;
        //对拒绝策略创建动态代理,在代理里面可以发送预警通知
        RejectedExecutionHandler rejectedProxy = createProxy(rejectedExecutionHandler, threadPoolId, rejectCount);
        setRejectedExecutionHandler(rejectedProxy);
    }

    public static RejectedExecutionHandler createProxy(RejectedExecutionHandler rejectedExecutionHandler, String threadPoolId, AtomicLong rejectedNum) {
        RejectedExecutionHandler rejectedProxy = (RejectedExecutionHandler) Proxy
                .newProxyInstance(
                        rejectedExecutionHandler.getClass().getClassLoader(),
                        new Class[]{RejectedExecutionHandler.class},
                        new RejectedProxyInvocationHandler(rejectedExecutionHandler, threadPoolId, rejectedNum));
        return rejectedProxy;
    }

    @Override
    public void execute(@NonNull Runnable command) {
        super.execute(command);
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        if (executeTimeOut == null) {
            return;
        }
        //线程任务执行之前设置当前时间
        executeTimeThreadLocal.set(System.currentTimeMillis());
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        Long startTime = executeTimeThreadLocal.get();
        if (startTime == null) {
            return;
        }

        try {
            //线程任务执行之后计算时间差值,获取线程任务执行时间是否超时
            long endTime = System.currentTimeMillis();
            long executeTime = endTime - startTime;
            boolean isExecuteTimeout = executeTime > executeTimeOut;
            if (isExecuteTimeout) {
                log.info("线程任务执行超时了,threadPoolId:{},executeTime:{}", threadPoolId, executeTime);
                //TODO 发送钉钉飞书预警
            }
        } finally {
            executeTimeThreadLocal.remove();
        }
    }

    @Override
    public void setCorePoolSize(int corePoolSize) {
        super.setCorePoolSize(corePoolSize);
    }

    @Override
    public void setMaximumPoolSize(int maximumPoolSize) {
        super.setMaximumPoolSize(maximumPoolSize);
    }
}