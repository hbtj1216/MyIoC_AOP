package com.tao.ioc_aop.aop.core;

/**
 * Created by Michael on 2017/6/20.
 */

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 作为方法调用链的主执行器
 */
public class ReflectiveMethodInvocation implements MethodInvocation {

    /**
     * 通知(Advice)的拦截器链
     */
    private List<MethodInterceptor> chain;

    /**
     * 每个Advice所配置的匹配信息
     */
    private List<Matcher> matchers;

    /**
     * 目标对象
     */
    private Object target;

    /**
     * 目标方法
     */
    private Method method;

    /**
     * 目标方法的方法参数集合
     */
    private Object[] arguments;

    /**
     * 索引
     */
    private int index;



    public ReflectiveMethodInvocation(List<MethodInterceptor> chain,
                                      List<Matcher> matchers, Object target,
                                      Method method, Object[] arguments) {
        this.chain = chain;
        this.matchers = matchers;
        this.target = target;
        this.method = method;
        this.arguments = arguments;
    }



    @Override
    public Method getMethod() {
        return this.method;
    }

    @Override
    public Object[] getArguments() {
        return this.arguments;
    }


    @Override
    public Object getThis() {
        return this.target;
    }

    @Override
    public AccessibleObject getStaticPart() {
        return this.method;
    }


    @Override
    public Object proceed() throws Throwable {

        if(index == chain.size()) {
            //到达链的末端，直接调用目标方法
            return invokeJoinpoint();
        }
        //否则，对于调用链上的每一个AdviceInterceptor
        Matcher matcher = matchers.get(index);
        //查看是否匹配
        if(matcher.matches(this.method, this.target.getClass())) {
            //如果匹配，调用拦截器方法
            return chain.get(index++).invoke(this);
        } else {
            index++;
            return proceed();
        }
    }


    protected Object invokeJoinpoint() throws Throwable {
        //通过反射调用目标方法
        return this.method.invoke(this.target, this.arguments);
    }
}










