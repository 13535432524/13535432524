package com.hzh.springboot.service;

import com.hzh.springboot.generator.domain.countries;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author huang
* @description 针对表【countries】的数据库操作Service
* @createDate 2023-08-17 13:14:03
*/
public interface countriesService extends IService<countries> {
       public List<countries> updateRegion(countries coun);
}
