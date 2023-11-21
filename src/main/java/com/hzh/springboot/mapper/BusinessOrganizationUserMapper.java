package com.hzh.springboot.mapper;

import com.hzh.springboot.common.page.Page;
import com.hzh.springboot.domain.BusinessOrganizationUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author huang
* @description 针对表【business_organization_user】的数据库操作Mapper
* @createDate 2023-09-21 19:27:54
* @Entity com.hzh.springboot.domain.BusinessOrganizationUser
*/
public interface BusinessOrganizationUserMapper extends BaseMapper<BusinessOrganizationUser> {
    public List<BusinessOrganizationUser> getUserList(Page page);

    public  BusinessOrganizationUser getDataByUsername(String username);
}




