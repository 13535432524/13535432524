package com.hzh.springboot.interceptor;

import com.alibaba.fastjson.JSON;
import com.hzh.springboot.common.result.Result;
import com.hzh.springboot.common.result.ResultEnum;
import com.hzh.springboot.common.result.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
@Slf4j
public class myInterceptor implements HandlerInterceptor
{
    @Autowired
    StringRedisTemplate redis;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token=request.getParameter("token");
        if(!StringUtils.hasLength(token) || token ==null){
            returnJson(response,ResultUtil.failure(ResultEnum.LOGIN_FAILURE_200403));
            return false;
        }
        String redisKey= "token:user:"+token;
        if(!StringUtils.hasLength(redis.boundHashOps(redisKey).getKey()) || token==null){
            returnJson(response,ResultUtil.failure(ResultEnum.LOGIN_FAILURE_200402));
            return false;
        }
        return true;

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    private void returnJson(HttpServletResponse response, Result<?> result){
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try {
            writer = response.getWriter();
            writer.print(JSON.toJSON(result));
        } catch (IOException e){
            log.error("拦截器输出流异常"+e);
        } finally {
            if(writer != null){
                writer.close();
            }
        }
    }
}
