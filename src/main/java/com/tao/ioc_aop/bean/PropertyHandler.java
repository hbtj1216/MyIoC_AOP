package com.tao.ioc_aop.bean;

/**
 * Created by Michael on 2017/6/7.
 */

import java.lang.reflect.Method;
import java.util.Map;

/**
 * 处理属性的接口
 */
public interface PropertyHandler {

    /**
     * 为对象设置属性的值
     * @param obj
     * @param properties
     * @return
     */
    public Object setProperties(Object obj, Map<String, Object> properties);

    /**
     * 返回一个对象里面所有的setter方法, 封装成map, key为setter方法名去掉set后的字符串
     * @param obj
     * @return
     */
    public Map<String, Method> getSetterMethodsMap(Object obj);

    /**
     * 使用反射调用对象的set方法,在自动装配的时候使用
     * @param obj -- 注入的目标对象
     * @param argBean -- 注入的依赖(属性)对象
     * @param method -- 对应的setter方法
     */
    public void executeMethod(Object obj, Object argBean, Method method);
}


















