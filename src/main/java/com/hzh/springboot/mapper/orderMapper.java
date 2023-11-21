package com.hzh.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzh.springboot.generator.domain.countries;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author huang
* @description 针对表【countries】的数据库操作Mapper
* @createDate 2023-08-17 13:14:03
* @Entity generator.domain.countries
*/
@Mapper
public interface orderMapper extends BaseMapper<countries> {
    public  int updateByRegoinId(countries coun);

    public List<countries> getListByCountries(countries coun);
}




