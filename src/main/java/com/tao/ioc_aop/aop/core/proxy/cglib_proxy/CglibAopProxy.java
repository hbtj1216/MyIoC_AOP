package com.tao.ioc_aop.aop.core.proxy.cglib_proxy;

import com.tao.ioc_aop.aop.advice.Advice;
import com.tao.ioc_aop.aop.core.AdvicedSupport;
import com.tao.ioc_aop.aop.core.proxy.AopProxy;

/**
 * Created by Michael on 2017/6/20.
 */
public class CglibAopProxy implements AopProxy {

    private AdvicedSupport advicedSupport;


    public CglibAopProxy(AdvicedSupport advicedSupport) {
        this.advicedSupport = advicedSupport;
    }

    @Override
    public Object getProxy() {
        return null;
    }
}
