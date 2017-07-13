package com.tao.ioc_aop.model;

import com.tao.ioc_aop.annotation.Match;
import com.tao.ioc_aop.aop.advice.AfterAdvice;
import com.tao.ioc_aop.aop.advice.BeforeAdvice;
import com.tao.ioc_aop.aop.advice.EndAdvice;
import com.tao.ioc_aop.aop.advice.ErrorAdvice;

import java.lang.reflect.Method;

/**
 * Created by Michael on 2017/6/20.
 */
@Match(methodMatch = "com.tao.ioc_aop.model.Service.provideService")
public class TransactionAspect implements BeforeAdvice, EndAdvice, ErrorAdvice {


    @Override
    public void before(Method method, Object[] args) {
        System.out.println("开启事务!!!");
    }

    @Override
    public void end(Method method, Object[] args, Object retVal) {
        System.out.println("关闭事务!!!");
    }

    @Override
    public void error(Method method, Object[] args, Exception e) {
        System.out.println("发生错误，回滚事务!!!");
    }
}



