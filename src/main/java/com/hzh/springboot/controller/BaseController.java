package com.hzh.springboot.controller;

import com.hzh.springboot.common.result.Result;
import com.hzh.springboot.domain.check.Check;
import com.hzh.springboot.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author huang
 */
@RestController
public class BaseController {
    @Autowired
    BaseService service;
    @PostMapping(value="/login")
    public Result<?> login(@Validated Check check){
        return service.login(check);
    }
}
