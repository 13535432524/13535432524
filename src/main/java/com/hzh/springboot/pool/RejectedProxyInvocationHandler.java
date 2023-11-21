package com.hzh.springboot.pool;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @description 线程池拒绝策略代理类
 * @author wangkp
 * @create 2023/4/13 17:32
 */
@Slf4j
@AllArgsConstructor
public class RejectedProxyInvocationHandler implements InvocationHandler {

    private final Object target;

    private final String threadPoolId;

    private final AtomicLong rejectCount;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //线程池拒绝次数统计
        rejectCount.incrementAndGet();
        log.info("线程任务执行触发拒绝策略了,threadPoolId:{},rejectCount:{}",threadPoolId,rejectCount.get());
        //TODO 发送钉钉飞书预警
        return method.invoke(target, args);
    }
}
