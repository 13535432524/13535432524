package com.hzh.springboot.advice;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import static java.lang.Thread.sleep;

//@Aspect
@Component
public class myAdvice {

    @Before(value="execution(* com.hzh.springboot.controller.countriesController.*(..))")
    public void before(){
        System.out.print("给老子增强");
    }

    @AfterReturning(value="execution(* com.hzh.springboot.controller.countriesController.*(..))")
    public void after(){
        System.out.print("给老子增强");
    }

    @After(value="execution(* com.hzh.springboot.controller.countriesController.*(..))")
    public void finish() throws InterruptedException {
        sleep(5000);
        System.out.print("给老子结束");
    }
}
