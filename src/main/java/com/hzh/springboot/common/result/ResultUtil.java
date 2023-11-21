package com.hzh.springboot.common.result;



public class ResultUtil {

    /**
     * @Description 成功
     * @Param @return  @exception
     * @Author panxg
     * @Date 2020/11/28 12:28
     **/
    public static <T> Result<T>  success() {
        return ResultUtil.getResultSuccess();
    }

    public static <T> Result<T>  success(ResultEnum resultEnum) {
        return ResultUtil.getResult(resultEnum);
    }

    public static <T> Result<T>  success(ResultEnum resultEnum,T data) {
        Result<T> result = ResultUtil.getResult(resultEnum);
        result.setData(data);
        return result;
    }

    public static <T> Result<T>  successMsg(String msg) {
        Result<T> resultSuccess = ResultUtil.getResultSuccess();
        resultSuccess.setMsg(msg);
        return resultSuccess;
    }

    public static <T> Result<T>  successData(T data) {
        Result<T> resultSuccess = ResultUtil.getResultSuccess();
        resultSuccess.setData(data);
        return resultSuccess;
    }

    public static <T> Result<T>  success(String msg, T data) {
        Result<T> resultSuccess = ResultUtil.getResultSuccess();
        resultSuccess.setData(data);
        resultSuccess.setMsg(msg);
        return resultSuccess;
    }

    /**
     * @Description 失败
     * @Param @return  @exception
     * @Author panxg
     * @Date 2020/11/28 12:27
     **/
    public static <T> Result<T>  failure() {
        return ResultUtil.getResultFailure();
    }

    public static <T> Result<T>  failure(ResultEnum resultEnum) {
        return ResultUtil.getResult(resultEnum);
    }

    public static <T> Result<T>  failure(ResultEnum resultEnum,T data) {
        Result<T> result = ResultUtil.getResult(resultEnum);
        result.setData(data);
        return result;
    }

    public static <T> Result<T>  failureMsg(String msg) {
        return ResultUtil.getResultFailure(msg);
    }

    public static <T> Result<T>  failureData(T data) {
        Result<T> resultFailure = ResultUtil.getResultFailure();
        resultFailure.setData(data);
        return resultFailure;
    }

    public static <T> Result<T>  failure(String msg, T data) {
        Result<T> resultFailure = ResultUtil.getResultFailure();
        resultFailure.setData(data);
        resultFailure.setMsg(msg);
        return resultFailure;
    }


    /**
     * @Description 获取成功默认返回实体
     * @Param @return  @exception
     * @Author panxg
     * @Date 2020/11/28 12:27
     **/
    public static <T> Result<T> getResultSuccess() {
        Result<T> result = new Result<>();
        result.setStatus(ResultConstant.RESULT_STATUS_SUCCESS);
        result.setMsg(ResultConstant.RESULT_MSG_SUCCESS);
        result.setCode(ResultConstant.RESULT_CODE_DEFAULT);
        return result;
    }

    /**
     * @Description 获取失败默认返回实体
     * @Param @return  @exception
     * @Author panxg
     * @Date 2020/11/28 12:27
     **/
    public static <T> Result<T>  getResultFailure(String... msg) {
        Result<T> result = new Result<>();
        result.setStatus(ResultConstant.RESULT_STATUS_FAILURE);
        if (msg==null || msg.length==0) {
            result.setMsg(ResultConstant.RESULT_MSG_FAILURE);
        }else{
            result.setMsg(msg[0]);
        }
        result.setCode(ResultConstant.RESULT_CODE_DEFAULT);
        return result;
    }

    /**
     * @Description 获取默认返回实体
     * @Param @return  @exception
     * @Author panxg
     * @Date 2020/11/28 12:27
     **/
    public static <T> Result<T>  getResult() {
        Result<T> result = new Result<>();
        result.setStatus(ResultConstant.RESULT_STATUS_SUCCESS);
        result.setMsg(ResultConstant.RESULT_MSG_SUCCESS);
        result.setCode(ResultConstant.RESULT_CODE_DEFAULT);
        return result;
    }

    /**
     * @Description 获取枚举入参返回实体
     * @Param @return  @exception
     * @Author panxg
     * @Date 2020/11/28 12:27
     **/
    public static <T> Result<T>  getResult(ResultEnum resultEnum) {
        Result<T> result = new Result<>();
        result.setStatus(resultEnum.getStatus());
        result.setMsg(resultEnum.getMsg());
        result.setCode(resultEnum.getCode());
        return result;
    }
}