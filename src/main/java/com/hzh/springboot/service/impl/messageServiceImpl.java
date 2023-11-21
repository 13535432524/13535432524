package com.hzh.springboot.service.impl;

import com.alibaba.fastjson.JSON;

import com.hzh.springboot.common.page.Page;
import com.hzh.springboot.domain.BusinessOrganizationUser;
import com.hzh.springboot.entity.order.BusinessOrder;
import com.hzh.springboot.mapper.BusinessOrderMapper;
import com.hzh.springboot.mapper.BusinessOrganizationUserMapper;
import com.hzh.springboot.service.messageService;
import jdk.nashorn.internal.ir.debug.ObjectSizeCalculator;
import lombok.extern.log4j.Log4j2;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.jetbrains.annotations.NotNull;
import org.redisson.Redisson;
import org.redisson.RedissonLock;
import org.redisson.RedissonMultiLock;
import org.redisson.RedissonReadLock;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

@Service
@Log4j2
public class messageServiceImpl implements messageService {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    BusinessOrderMapper mapper;

    @Autowired
    BusinessOrganizationUserMapper umapper;

    @Autowired
    Redisson redisson;

    @Autowired
    @Qualifier("straightPool")
    ThreadPoolTaskExecutor pool;
    static  int  a=0;
    @Override
    public void sendMessage(int num) {
        SendCallback callback=new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.println("发送成功");
                testDelay();
            }

            @Override
            public void onException(Throwable throwable) {
                System.out.println("发送失败");
                System.out.println(throwable.getMessage());
                testDelay();
            }
        };
        for (long i=0;i<1;i++){
            pool.execute(()->{
                long page=0;
                while((page=redisTemplate.boundValueOps("orderIncre").increment(0))<=20){
                    Map<String,Object> map=new HashMap<>(2);
                    map.put("star",1000);
                    map.put("end",1000);
                    List<BusinessOrder> list=mapper.selectData(map);
                    for (BusinessOrder item:
                            list) {
                        redisTemplate.boundListOps("order").leftPush(JSON.toJSONString(item));
                        Message message =new Message("order_id",item.getOrderid().getBytes());
                        rocketMQTemplate.asyncSend("order_id",message,callback);
                        break;
                    }

                }

            });
        }






    }

    public  void testDelay(){
        synchronized (messageServiceImpl.class){
            a++;
            System.out.println(a);
            System.out.println(System.currentTimeMillis());
        }
    }
    @Override
    public void testPool() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1000);
        for (long i=0;i<1000;i++){
            pool.execute(()->{
                countDownLatch.countDown();
            });
        }

        countDownLatch.await();
        System.out.println("下单完成");
    }


    @Override
    public void initRedis(){
        int pageSize=1000;
        Long star=System.currentTimeMillis();
        CountDownLatch countDownLatch = new CountDownLatch(100);
        new CyclicBarrier(100);
        try {

            redisTemplate.boundValueOps("orderIncre").set("0");
            for (long i=0;i<30;i++){
                pool.execute(()->{
                    long page=0;
                    while((page=redisTemplate.boundValueOps("orderIncre").increment(1))<=100){
                        Map<String,Object> map=new HashMap<>(2);
                        map.put("star",(page-1)*pageSize);
                        map.put("end",pageSize);
                        List<BusinessOrder> list=mapper.selectData(map);
                       for (BusinessOrder item:
                       list) {
                           redisTemplate.boundListOps("order").leftPush(JSON.toJSONString(item));
                       }
                        countDownLatch.countDown();
                        AtomicInteger atomicInteger = new AtomicInteger();
                        atomicInteger.incrementAndGet();
                    }

                });
            }
        }catch (Exception e){

        }
        Long end=System.currentTimeMillis();
        System.out.println(((end-star)/1000)+"秒");
    }

    @Override
    public  String updpate(String orderid){
//        Map<String,Object> map=new HashMap<>(15);
//        map.put("orderId","JD12131321231");
//        map.put("otuo","广州");
//        map.put("上海","fsdaf");
        Lock lock= new ReentrantLock();
        lock.lock();;
//        redisTemplate.boundHashOps("CG2015115133").putAll(map);
//        return null;
//        BusinessOrder businessOrder=(BusinessOrder)JSON.parse(redisTemplate.opsForList().leftPop("order"));
//        orderid=businessOrder.getOrderid();
//        Long star=System.currentTimeMillis();
//        RLock lock=redisson.getLock(orderid);
//        lock.lock();
//        try{
//            int num=mapper.selectById(orderid).getCutbedNum();
//            if(num>800){
//                if(lock.isLocked() && lock.isHeldByCurrentThread()){
//                    System.out.println("超过");
//                    Long end=System.currentTimeMillis();
//                    redisTemplate.opsForList().leftPush("order-time",((end-star))+"超秒");
//                    lock.unlock();
//                }
//                return "超过";
//            }else{
//                if(lock.isLocked() && lock.isHeldByCurrentThread()) {
//                    mapper.updateCut(orderid);
//                    System.out.println("修改成功");
//                    Long end = System.currentTimeMillis();
//                    redisTemplate.opsForList().leftPush("order-time", ((end - star)) + "超秒");
//                }
//                lock.unlock();
//                return "修改成功";
//            }
//        }finally {
//            lock.unlock();
//        }
        try {
            Thread.sleep(20);
            return null;
        }catch (Exception e){

        }finally {
            return null;
        }


//        Lock lock= new ReentrantLock();
//        lock.lock();
//        try{
//            int num=mapper.selectById(orderid).getCutbedNum();
//            if(num>800){
//                    System.out.println("超过");
//                    Long end=System.currentTimeMillis();
//                    redisTemplate.opsForList().leftPush("order-time",((end-star))+"超秒");
//                lock.unlock();
//                return "超过";
//            }else{
//                    mapper.updateCut(orderid);
//                    System.out.println("修改成功");
//                    Long end = System.currentTimeMillis();
//                    redisTemplate.opsForList().leftPush("order-time", ((end - star)) + "超秒");
//                lock.unlock();
//                return "修改成功";
//            }
//        }finally {
//            lock.unlock();
//        }


//        synchronized (this){
//            int num=mapper.selectById(orderid).getCutbedNum();
//            if(num>800){
//                System.out.println("超过");
//                Long end=System.currentTimeMillis();
//                redisTemplate.opsForList().leftPush("order-time",((end-star))+"超秒");
//                return "超过";
//            }else{
//                mapper.updateCut(orderid);
//                System.out.println("修改成功");
//                Long end = System.currentTimeMillis();
//                redisTemplate.opsForList().leftPush("order-time", ((end - star)) + "超秒");
//                return "修改成功";
//            }
//        }
    }

    @Override
    public  void testMul() throws ExecutionException, InterruptedException {
        Long nstart=System.currentTimeMillis();
        {
            List<Map<String, Object>> orderList=mapper.selectBusinessOrderDetail();
            List<Map<String, Object>> cut= mapper.selectBusinessOrderCutNum(orderList);
            List<Map<String, Object>> finish=mapper.selectBusinessOrderFinishNum(orderList);
            List<Map<String, Object>> delivery=mapper.selectBusinessOrderDeliveryNum(orderList);
            List<Map<String,Object>> resultList=new ArrayList<>();
            List<Object> setRe=new ArrayList<>();
            for (Map<String,Object> item:
                    orderList) {
                setRe.add(item.get("orderId"));
            }
            for (Object item:
                    setRe) {
                Map<String,Object> map=new HashMap<>();
                map.put("orderId",item);
                resultList.add(map);
            }
            resultList=resultList.stream().distinct().collect(Collectors.toList());
            for (Map<String,Object> item:
                    orderList) {
                int cutNum=0;
                int finishNum=0;
                int deliveryNum=0;
                for (Map<String,Object> cutitem:
                        cut) {
                    if(cutitem.get("detailId").equals(item.get("detailId"))){
                        BigDecimal num=(BigDecimal) cutitem.get("num");
                        cutNum+=num.intValue();
                    }
                }
                for (Map<String,Object> finishitem:
                        finish) {
                    if(finishitem.get("detailId").equals(item.get("detailId"))){
                        BigDecimal num=(BigDecimal) finishitem.get("num");
                        finishNum+=num.intValue();
                    }
                }
                for (Map<String,Object> deliveryItem:
                        delivery) {
                    if(deliveryItem.get("detailId").equals(item.get("detailId"))){
                        BigDecimal num=(BigDecimal) deliveryItem.get("num");
                        deliveryNum+=num.intValue();
                    }
                }
                item.put("cutNum",cutNum);
                item.put("finishNum",finishNum);
                item.put("deliveryNum",deliveryNum);
            }

            for (Map<String,Object> item:
                    resultList) {
                int cutNum=0;
                int finishNum=0;
                int deliveryNum=0;
                for (Map<String,Object> orderItem:
                        orderList) {
                    if(orderItem.get("orderId").equals(item.get("orderId"))){
                        int itemcutNum=(Integer) orderItem.get("cutNum");
                        int itemfinishNum=(Integer) orderItem.get("finishNum");
                        int itemdeliveryNum=(Integer) orderItem.get("deliveryNum");
                        cutNum+=itemcutNum;
                        finishNum+=itemfinishNum;
                        deliveryNum+=itemdeliveryNum;
                    }
                }
                item.put("cutNum",cutNum);
                item.put("finishNum",finishNum);
                item.put("deliveryNum",deliveryNum);
            }
            long nend=System.currentTimeMillis();
            System.out.println("result:"+(nend-nstart));
            System.out.println("result:"+resultList.toString());
        }


        Long start=System.currentTimeMillis();
        CompletableFuture<List<Map<String,Object>>> future1=CompletableFuture.supplyAsync(()->{
            List<Map<String, Object>> maps1 = mapper.selectBusinessOrderDetail();
            return maps1;
        },pool);

        CompletableFuture<List<Map<String,Object>>> future2=future1.thenApplyAsync((items)->{
            List<Map<String, Object>> maps1 = mapper.selectBusinessOrderCutNum(items);
            return maps1;
        },pool);



        CompletableFuture<List<Map<String,Object>>> future3=future1.thenApplyAsync((items)->{
            List<Map<String, Object>> maps1 = mapper.selectBusinessOrderFinishNum(items);
            return maps1;
        },pool);

        CompletableFuture<List<Map<String,Object>>> future4=future1.thenApplyAsync((items)->{
            List<Map<String, Object>> maps1 = mapper.selectBusinessOrderDeliveryNum(items);
            return maps1;
        },pool);
        CompletableFuture<Void> future5=CompletableFuture.allOf(future1,future2,future3,future4);
        CompletableFuture<List<Map<String, Object>>> future6=future5.thenApplyAsync(res->{
            List<Map<String, Object>> orderList=future1.join();
            List<Map<String, Object>> cut=future2.join();
            List<Map<String, Object>> finish=future3.join();
            List<Map<String, Object>> delivery=future4.join();
            List<Map<String,Object>> resultList=new ArrayList<>();
            List<Object> setRe=new ArrayList<>();
            for (Map<String,Object> item:
                    orderList) {
                setRe.add(item.get("orderId"));
            }
            for (Object item:
                    setRe) {
                Map<String,Object> map=new HashMap<>();
                map.put("orderId",item);
                resultList.add(map);
            }
            resultList=resultList.stream().distinct().collect(Collectors.toList());
            for (Map<String,Object> item:
                    orderList) {
                int cutNum=0;
                int finishNum=0;
                int deliveryNum=0;
                for (Map<String,Object> cutitem:
                        cut) {
                   if(cutitem.get("detailId").equals(item.get("detailId"))){
                       BigDecimal num=(BigDecimal) cutitem.get("num");
                       cutNum+=num.intValue();
                   }
                }
                for (Map<String,Object> finishitem:
                        finish) {
                    if(finishitem.get("detailId").equals(item.get("detailId"))){
                        BigDecimal num=(BigDecimal) finishitem.get("num");
                        finishNum+=num.intValue();
                    }
                }
                for (Map<String,Object> deliveryItem:
                        delivery) {
                    if(deliveryItem.get("detailId").equals(item.get("detailId"))){
                        BigDecimal num=(BigDecimal) deliveryItem.get("num");
                        deliveryNum+=num.intValue();
                    }
                }
                item.put("cutNum",cutNum);
                item.put("finishNum",finishNum);
                item.put("deliveryNum",deliveryNum);
            }

            for (Map<String,Object> item:
                    resultList) {
                int cutNum=0;
                int finishNum=0;
                int deliveryNum=0;
                for (Map<String,Object> orderItem:
                        orderList) {
                    if(orderItem.get("orderId").equals(item.get("orderId"))){
                        int itemcutNum=(Integer) orderItem.get("cutNum");
                        int itemfinishNum=(Integer) orderItem.get("finishNum");
                        int itemdeliveryNum=(Integer) orderItem.get("deliveryNum");
                        cutNum+=itemcutNum;
                        finishNum+=itemfinishNum;
                        deliveryNum+=itemdeliveryNum;
                    }
                }
                item.put("cutNum",cutNum);
                item.put("finishNum",finishNum);
                item.put("deliveryNum",deliveryNum);
            }

            return resultList;
        },pool);
        long end=System.currentTimeMillis();
        System.out.println("result:"+(end-start));
        System.out.println("result:"+future6.get().toString());
    }

    @Override
    public  void testUpdate(String orderId){
        Long nstart=System.currentTimeMillis();
        if(!Boolean.TRUE.equals(redisTemplate.boundSetOps("orderList").isMember(orderId))){
            mapper.getOrderid(orderId);
        }
        Long end=System.currentTimeMillis();
        System.out.println("result:"+(end-nstart));
    }

    public  void initRedisData(){
//        AtomicInteger page=new AtomicInteger(1);
//        do{
//
//                Map<String,Object> map=new HashMap<>(2);
//                map.put("star",(page.get()-1)*(10000));
//                map.put("end",10000);
//                List<String> list=mapper.selectOrderIdList(map);
//                String [] listStr=new String[list.size()];
//                for(int i=0;i<list.size();i++){
//                    listStr[i]=list.get(i);
//                }
//                System.out.println(page);
//                redisTemplate.boundSetOps("orderList").add(listStr);
//
//        } while (page.incrementAndGet()<=20);
        Page pages= Page.getData(1,10000);
        StopWatch stopWatch1=new StopWatch();
        stopWatch1.start();
        List<BusinessOrganizationUser> userList1 = umapper.getUserList(pages);
        System.out.println("大小"+ObjectSizeCalculator.getObjectSize(userList1));
        for (;;){
            List<BusinessOrganizationUser> userList = umapper.getUserList(pages);
            if(userList==null || userList.size()==0){
                break;
            }
            redisTemplate.executePipelined(new SessionCallback<Object>() {
                @Override
                public  Object execute(RedisOperations operations) throws DataAccessException {
                    for (BusinessOrganizationUser item:
                            userList) {
                        operations.boundValueOps("user:"+item.getUsername())
                                .set(JSON.toJSONString(item));
                    }
                    return null;
                }
            });
            pages.incre();
        }
        stopWatch1.stop();
        System.out.println("for所需时间："+stopWatch1.getTotalTimeMillis()+"s");
    }





}
