package com.hzh.springboot.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName business_organization_user
 */
@TableName(value ="business_organization_user")
@Data
public class BusinessOrganizationUser implements Serializable {
    /**
     * 
     */
    @TableId(value = "id")
    private String id;

    /**
     * 
     */
    @TableField(value = "name")
    private String name;

    /**
     * 
     */
    @TableField(value = "gender")
    private String gender;

    /**
     * 
     */
    @TableField(value = "passsword")
    private String passsword;

    /**
     * 
     */
    @TableField(value = "username")
    private String username;

    /**
     * 
     */
    @TableField(value = "organizationId")
    private String organizationid;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}