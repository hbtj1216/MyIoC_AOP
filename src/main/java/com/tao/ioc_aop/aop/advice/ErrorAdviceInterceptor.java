package com.tao.ioc_aop.aop.advice;

import com.tao.ioc_aop.aop.advice.ErrorAdvice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * Created by Michael on 2017/6/19.
 */
public class ErrorAdviceInterceptor implements MethodInterceptor {

    //异常通知的引用
    private ErrorAdvice errorAdvice;


    public ErrorAdviceInterceptor(ErrorAdvice errorAdvice) {
        this.errorAdvice = errorAdvice;
    }


    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {

        Object retVal = null;
        try {
            retVal = invocation.proceed();
        } catch (Exception e) {
            //发生异常
            this.errorAdvice.error(invocation.getMethod(), invocation.getArguments(), e);
            throw e;
        }

        return retVal;
    }
}





