package com.hzh.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzh.springboot.entity.order.BusinessOrder;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author huang
 * @description 针对表【countries】的数据库操作Mapper
 * @createDate 2023-08-17 13:14:03
 * @Entity generator.domain.countries
 */
@Mapper
public interface BusinessOrderMapper extends BaseMapper<BusinessOrder> {
    public List<BusinessOrder> selectData(Map<String,Object> map);

    public  int updateCut(String orderId);

    public List<Map<String,Object>> selectBusinessOrder();

    public List<Map<String,Object>> selectBusinessOrderDetail();

    public List<Map<String,Object>> selectBusinessOrderCutNum(List<Map<String,Object>> list);

    public List<Map<String,Object>> selectBusinessOrderFinishNum(List<Map<String,Object>> list);

    public List<Map<String,Object>> selectBusinessOrderDeliveryNum(List<Map<String,Object>> list);

    public List<String> selectOrderIdList(Map<String,Object> param);

    public String getOrderid(String id);
}




