package com.tao.ioc_aop.xml.autowire;

/**
 * Created by Michael on 2017/6/5.
 */

/**
 * 自动装配接口
 */
public interface Autowire {

    /**
     * 返回类中需要自动装配的类型的值
     * @return
     */
    public String getType();
}
