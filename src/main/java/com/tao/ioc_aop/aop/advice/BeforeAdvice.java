package com.tao.ioc_aop.aop.advice;

/**
 * Created by Michael on 2017/6/19.
 */

import java.lang.reflect.Method;

/**
 * 前置通知接口。
 * 在切面方法执行之前处理。
 */
public interface BeforeAdvice extends Advice {

    /**
     * 前置通知需要执行的方法
     * @param method
     * @param args
     */
    void before(Method method, Object[] args);
}
