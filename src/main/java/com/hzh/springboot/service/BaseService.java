package com.hzh.springboot.service;


import com.hzh.springboot.common.result.Result;
import com.hzh.springboot.domain.check.Check;

import java.util.concurrent.ExecutionException;


public interface BaseService {
    public Result<?> login(Check check);

}
