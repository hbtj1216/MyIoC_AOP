package com.tao.ioc_aop.aop.core.proxy.jdk_proxy;

import com.tao.ioc_aop.aop.core.AdvicedSupport;
import com.tao.ioc_aop.aop.core.ReflectiveMethodInvocation;
import com.tao.ioc_aop.aop.core.proxy.AopProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by Michael on 2017/6/19.
 */

/**
 * JDK动态代理
 */
public class JdkAopProxy implements AopProxy, InvocationHandler {

    //所有的通知信息都封装在这个对象里面
    private AdvicedSupport advicedSupport;

    public JdkAopProxy(AdvicedSupport advicedSupport) {
        this.advicedSupport = advicedSupport;
    }

    @Override
    public Object getProxy() {

        return Proxy.newProxyInstance(advicedSupport.getClassLoader(),
                advicedSupport.getInterfaces(), this);
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        return new ReflectiveMethodInvocation(advicedSupport.getAllInterceptor(),
                advicedSupport.getMatchers(), advicedSupport.getTarget(),
                method, args).proceed();
    }
}








