package com.tao.ioc_aop.aop.advice;

import java.lang.reflect.Method;

/**
 * Created by Michael on 2017/6/19.
 */

/**
 * 异常通知。
 * 在切面方法抛出异常的时候调用。
 */
public interface ErrorAdvice extends Advice {

    /**
     * 异常通知需要执行的方法
     * @param method
     * @param args
     * @param e
     */
    void error(Method method, Object[] args, Exception e);
}
