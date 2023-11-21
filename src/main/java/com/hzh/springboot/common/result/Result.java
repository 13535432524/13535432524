package com.hzh.springboot.common.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@ApiModel(value="Result对象", description="Result对象")
public class Result<T> implements Serializable {

    @ApiModelProperty(value = "返回状态")
    private Integer status;

    @ApiModelProperty(value = "返回状态码")
    private Integer code;

    @ApiModelProperty(value = "返回描述")
    private String msg;

    @ApiModelProperty(value = "返回数据")
    private T data;

}
