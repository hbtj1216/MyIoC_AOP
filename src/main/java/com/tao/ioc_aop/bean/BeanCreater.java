package com.tao.ioc_aop.bean;

/**
 * Created by Michael on 2017/6/6.
 */

import java.util.List;

/**
 * 创建bean的接口
 */
public interface BeanCreater {

    /**
     * 使用无参的构造器创建bean实例，不设置任何属性
     * @param className
     * @return
     */
    public Object createBeanUseDefaultConstructor(String className);

    /**
     * 使用有参数的构造器创建bean
     * @param className
     * @param args
     * @return
     */
    public Object createBeanUseDefineConstructor(String className, List<Object> args);
}



