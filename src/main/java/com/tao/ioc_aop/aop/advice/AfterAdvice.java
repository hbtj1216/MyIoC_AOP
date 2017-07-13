package com.tao.ioc_aop.aop.advice;

/**
 * Created by Michael on 2017/6/19.
 */

import java.lang.reflect.Method;

/**
 * 后置通知接口。
 * 在切面方法执行之后处理。
 */
public interface AfterAdvice extends Advice {

    /**
     * 后置通知需要执行的方法
     * @param method
     * @param args
     * @param retVal
     */
    void after(Method method, Object[] args, Object retVal);
}
