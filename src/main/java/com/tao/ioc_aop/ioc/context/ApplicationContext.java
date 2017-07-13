package com.tao.ioc_aop.ioc.context;

/**
 * Created by Michael on 2017/6/10.
 */

/**
 * 应用上下文接口
 */
public interface ApplicationContext {

    /**
     * 根据id获取bean对象
     * @param id
     * @return
     */
    public Object getBean(String id);

    /**
     * 判断某个bean是否存在
     * @param id
     * @return
     */
    public boolean containsBean(String id);

    /**
     * 判断bean是否是单例
     * @param id
     * @return
     */
    public boolean isSingleton(String id);
}





















