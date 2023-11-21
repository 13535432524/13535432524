package com.hzh.springboot.service;


import java.util.concurrent.ExecutionException;

public interface messageService {
    public  void sendMessage(int num);

    public  void testPool() throws InterruptedException;

    public void initRedis() throws InterruptedException;

    public  String updpate(String orderid);

    public void testMul() throws ExecutionException, InterruptedException;

    public void initRedisData();

    public  void testUpdate(String orderId);
}
