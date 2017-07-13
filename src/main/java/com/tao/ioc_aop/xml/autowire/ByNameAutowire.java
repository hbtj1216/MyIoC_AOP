package com.tao.ioc_aop.xml.autowire;

/**
 * Created by Michael on 2017/6/5.
 */

/**
 * 根据名称自动装配的类
 */
public class ByNameAutowire implements Autowire {

    //自动装配的类型
    private String type;

    public ByNameAutowire(String type) {
        this.type = type;
    }


    @Override
    public String getType() {

        return this.type;
    }
}
