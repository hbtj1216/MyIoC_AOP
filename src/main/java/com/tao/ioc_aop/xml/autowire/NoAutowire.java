package com.tao.ioc_aop.xml.autowire;

/**
 * Created by Michael on 2017/6/5.
 */
public class NoAutowire implements Autowire {

    private String type;

    public NoAutowire(String type) {
        this.type = type;
    }

    //直接返回"no"表示不自动装配
    @Override
    public String getType() {

        return "no";
    }
}
