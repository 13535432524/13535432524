package com.hzh.springboot.common.result;

public enum ResultEnum {


    LOGIN_OUT_SUCCESS(1, 200, "退出成功!"),
    LOGIN_IN_SUCCESS(1, 200, "登录成功!"),

    AUTHORITY_FAILURE(-1, 200, "权限不足!"),

    LOGIN_FAILURE_200401(-1, 200401, "登录失败!"),
    LOGIN_FAILURE_200402(-1, 200402, "登录信息已过期!"),
    LOGIN_FAILURE_200403(-1, 200403, "未登录，请先登录!"),

    OPERATE_SUCCESS(1, 200, "操作成功!"),
    OPERATE_FAILURE(-1, 200, "操作失败!"),

    QUERY_SUCCESS(1, 200, "查询成功!"),
    QUERY_FAILURE(-1, 200, "查询失败!"),

    DELETE_SUCCESS(1, 200, "删除成功!"),
    DELETE_FAILURE(-1, 200, "删除失败!"),

    INSERT_SUCCESS(1, 200, "新增成功!"),
    INSERT_FAILURE(-1, 200, "新增失败!"),

    UPDATE_SUCCESS(1, 200, "修改成功!"),
    UPDATE_FAILURE(-1, 200, "修改失败!"),

    SQL_FAILURE(-1, 200, "数据库异常!"),

    HTTP_FAILURE(-1, 200, "请求异常!"),

    SERVICE_FAILURE(-1, 200, "服务器异常！"),

    PARAM_ERROR(-1,200,"请求参数有误"),

    WX_PAY_EXCEPTION(-1,200,"微信异常"),
    ;



    private Integer status;

    private Integer code;

    private String msg;

    ResultEnum(Integer status, Integer code, String msg) {
        this.status = status;
        this.code = code;
        this.msg = msg;
    }

    ResultEnum( Integer code, String msg) {
        this.status = 0;
        this.code = code;
        this.msg = msg;
    }

    public ResultEnum setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public ResultEnum setCode(Integer code) {
        this.code = code;
        return this;
    }

    public ResultEnum setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
