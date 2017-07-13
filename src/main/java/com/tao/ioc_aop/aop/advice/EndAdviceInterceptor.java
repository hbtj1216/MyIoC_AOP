package com.tao.ioc_aop.aop.advice;

import com.tao.ioc_aop.aop.advice.EndAdvice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * Created by Michael on 2017/6/19.
 */
public class EndAdviceInterceptor implements MethodInterceptor {

    //结束通知的引用
    private EndAdvice endAdvice;


    public EndAdviceInterceptor(EndAdvice endAdvice) {
        this.endAdvice = endAdvice;
    }


    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {

        Object retVal = null;
        try {
            retVal = invocation.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            this.endAdvice.end(invocation.getMethod(), invocation.getArguments(), retVal);
        }

        return retVal;
    }
}







