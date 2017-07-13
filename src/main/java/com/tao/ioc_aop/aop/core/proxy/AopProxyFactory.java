package com.tao.ioc_aop.aop.core.proxy;

/**
 * Created by Michael on 2017/6/20.
 */

import com.tao.ioc_aop.aop.advice.Advice;
import com.tao.ioc_aop.aop.core.AdvicedSupport;
import com.tao.ioc_aop.aop.core.proxy.cglib_proxy.CglibAopProxy;
import com.tao.ioc_aop.aop.core.proxy.jdk_proxy.JdkAopProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Aop代理的工厂类
 */
public class AopProxyFactory {

    private static final Logger logger = LoggerFactory.getLogger(AopProxyFactory.class);

    /**
     * 被代理的目标对象
     */
    private Object target;

    /**
     * 目标对象上的通知集合
     */
    private List<Advice> adviceList = new ArrayList<Advice>();


    /**
     * 设置被代理的目标对象
     * @param target
     * @return
     */
    public AopProxyFactory setTarget(Object target) {
        this.target = target;
        return this;
    }

    /**
     * 为被代理对象添加通知(Advice)
     * @param advice
     * @return
     */
    public AopProxyFactory addAdvice(Advice advice) {
        if(advice == null) {
            throw new NullPointerException("添加的Advice对象不能为null");
        }
        this.adviceList.add(advice);
        return this;
    }


    /**
     * 获取代理对象
     * @return
     */
    public Object getProxy() {

        if(target == null) {
            throw new NullPointerException("被代理的目标对象不能为null");
        }

        AdvicedSupport advicedSupport = new AdvicedSupport(this.target, this.adviceList);

        AopProxy proxy = null;
        //根据是否实现了接口，选择jdk动态代理或者cglib
        if(advicedSupport.hasInterfaces()) {
            logger.info("采用jdk的动态代理...");
            proxy = new JdkAopProxy(advicedSupport);
        } else {
            logger.info("采用cglib动态代理...");
            proxy = new CglibAopProxy(advicedSupport);
        }

        return proxy.getProxy();
    }

}








