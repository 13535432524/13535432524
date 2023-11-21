package com.hzh.springboot.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hzh.springboot.common.result.Result;
import com.hzh.springboot.common.result.ResultEnum;
import com.hzh.springboot.common.result.ResultUtil;
import com.hzh.springboot.domain.BusinessOrganizationUser;
import com.hzh.springboot.domain.check.Check;
import com.hzh.springboot.entity.order.BusinessOrder;
import com.hzh.springboot.mapper.BusinessOrderMapper;
import com.hzh.springboot.mapper.BusinessOrganizationUserMapper;
import com.hzh.springboot.service.BaseService;
import lombok.extern.log4j.Log4j2;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@Log4j2
public class BaseServiceImpl implements BaseService {
    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    Redisson redisson;

    @Autowired
    BusinessOrganizationUserMapper userMapper;


    @Override
    public Result<?> login(Check check) {
        BusinessOrganizationUser user=null;
        RLock rLock1= redisson.getLock("user:"+check.getUsername()+":operator");
        rLock1.lock();
        try{
            user= JSON.parseObject(redisTemplate.boundValueOps("user:"+check.getUsername()).get(),BusinessOrganizationUser.class) ;
            if(user==null){
                if((user=userMapper.getDataByUsername(check.getUsername()))!=null){
                    if(rLock1.isLocked() && rLock1.isHeldByCurrentThread()) {
                        redisTemplate.delete("user:"+user.getUsername());
                        redisTemplate.boundValueOps("user:"+user.getUsername()).set(JSONObject.toJSONString(user));
                    }
                }else{
                    return ResultUtil.failureMsg("账号不正确");
                }
            }
        }finally {
            rLock1.unlock();
        }

        if(!check.getPassword().equals(user.getPasssword())){
            return ResultUtil.failureMsg("密码不正确");
        }else{
            RLock rLock= redisson.getLock("token:user:"+user.getUsername());
            rLock.lock();
            try{
                //首先判断token有没有
                String token=redisTemplate.boundListOps("user:token:list:"+user.getUsername()).index(0);
                if(token!=null){
                    return ResultUtil.success(ResultEnum.LOGIN_IN_SUCCESS,token);
                }
                token= UUID.randomUUID().toString();

                if(rLock.isLocked() && rLock.isHeldByCurrentThread()) {
                    redisTemplate.boundValueOps("token:user:"+token).set(JSON.toJSONString(user));
                    redisTemplate.boundListOps("user:token:list:"+user.getUsername()).leftPush(token);
                }
                return ResultUtil.success(ResultEnum.LOGIN_IN_SUCCESS,token);
            }catch (Exception e){
                e.printStackTrace();
                throw new RuntimeException("出错");
            }finally {
                rLock.unlock();
            }

        }
    }



}







