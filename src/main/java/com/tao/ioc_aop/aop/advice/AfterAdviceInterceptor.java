package com.tao.ioc_aop.aop.advice;

import com.tao.ioc_aop.aop.advice.AfterAdvice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * Created by Michael on 2017/6/19.
 */

/**
 * 后置通知的拦截器。
 */
public class AfterAdviceInterceptor implements MethodInterceptor {

    //后置通知的引用
    private AfterAdvice afterAdvice;


    public AfterAdviceInterceptor(AfterAdvice afterAdvice) {
        this.afterAdvice = afterAdvice;
    }


    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {

        /**
         * 后置通知拦截器的方法调用中，首先通过动态代理调用切面方法，
         * 然后调用后置通知对象的after方法
         */
        Object retVal = invocation.proceed();
        this.afterAdvice.after(invocation.getMethod(), invocation.getArguments(), retVal);

        return retVal;
    }
}







