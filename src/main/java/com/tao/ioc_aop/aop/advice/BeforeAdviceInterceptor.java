package com.tao.ioc_aop.aop.advice;

/**
 * Created by Michael on 2017/6/19.
 */

import com.tao.ioc_aop.aop.advice.BeforeAdvice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * 前置通知拦截器。
 */
public class BeforeAdviceInterceptor implements MethodInterceptor {

    //前置通知的引用
    private BeforeAdvice beforeAdvice;


    public BeforeAdviceInterceptor(BeforeAdvice beforeAdvice) {
        this.beforeAdvice = beforeAdvice;
    }


    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {

        /**
         * 在前置通知拦截器的方法调用中，首先调用前置通知对象的before方法,
         * 然后再通过动态代理对象调用切面方法
         */
        this.beforeAdvice.before(invocation.getMethod(), invocation.getArguments());
        return invocation.proceed();
    }
}









