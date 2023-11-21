package com.hzh.springboot.exception;

import com.hzh.springboot.common.result.Result;
import com.hzh.springboot.common.result.ResultEnum;
import com.hzh.springboot.common.result.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.stream.Collectors;


@Component
@Slf4j
@ControllerAdvice
public class myException {

    @ExceptionHandler({BindException.class})
    @ResponseBody
    public Result<?> BindingResult(BindException ex){
        String message =ex.getAllErrors().stream().map(item->item.getDefaultMessage()).collect(Collectors.joining());
        return ResultUtil.failureMsg(message);
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public Result<?> handleNull(Exception ex) {
        log.error("发生空指针异常：",ex);
        return ResultUtil.failure(ResultEnum.OPERATE_FAILURE);
    }



    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public Result<?> handleRuntime(Exception ex) {
        log.error("发生未知异常：",ex);
        return ResultUtil.failure(ResultEnum.SQL_FAILURE);
    }




}
