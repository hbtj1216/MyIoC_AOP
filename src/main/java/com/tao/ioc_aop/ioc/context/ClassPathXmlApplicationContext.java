package com.tao.ioc_aop.ioc.context;

/**
 * Created by Michael on 2017/6/12.
 */
public class ClassPathXmlApplicationContext extends AbstractApplicationContext {

    public ClassPathXmlApplicationContext(String[] xmlPaths) {
        //初始化
        this.initElements(xmlPaths);
        //实例化所有非懒加载的bean
        this.createAllBeans();
    }
}
