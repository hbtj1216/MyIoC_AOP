package com.tao.ioc_aop.aop.advice;

/**
 * Created by Michael on 2017/6/19.
 */

import java.lang.reflect.Method;

/**
 * 结束通知。
 * 结束的时候调用。
 */
public interface EndAdvice extends Advice {

    /**
     * 结束通知需要执行的方法
     * @param method
     * @param args
     * @param retVal
     */
    void end(Method method, Object[] args, Object retVal);
}
