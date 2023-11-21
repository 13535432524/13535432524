package com.hzh.springboot.controller;

import com.hzh.springboot.common.result.Result;
import com.hzh.springboot.common.result.ResultUtil;
import com.hzh.springboot.service.messageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
public class countriesController {

    @Autowired
    private messageService message;

    @PostMapping(value="/getCounById/{id}")
    public Result countriesController(@PathVariable("id") int num ) throws InterruptedException {
        message.testPool();
        return ResultUtil.success();
    }

    @PostMapping(value="/initRedis")
    public Result<?> initRedis( ) throws InterruptedException {
        message.initRedisData();
        return ResultUtil.success();
    }

    @PostMapping(value="/updateCut")
    public Result<?> updateCut(String orderId) {
        String msg=message.updpate(orderId);
        return  ResultUtil.success(msg,null);
    }

    @PostMapping(value = "/sendMessage")
    public Result<?> sendMessage() {
        message.sendMessage(3);
        return ResultUtil.success("msg",null);
    }

    @PostMapping(value = "/testMul")
    public Result<?> testMul() throws ExecutionException, InterruptedException {
        message.testMul();
        return ResultUtil.success("msg",null);
    }
    @PostMapping(value = "/testUpdate")
    public Result<?> testUpdate(String orderId) {
        message.testUpdate(orderId);
        return ResultUtil.success("msg",null);
    }

}
