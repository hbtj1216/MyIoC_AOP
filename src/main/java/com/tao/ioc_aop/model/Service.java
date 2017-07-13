package com.tao.ioc_aop.model;

/**
 * Created by Michael on 2017/6/20.
 */

/**
 * 提供服务的类
 */
public class Service implements IAop {

    private String serviceName; //服务名称

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }


    @Override
    public void provideService() {
        System.out.println("提供" + serviceName + "...");
    }


    @Override
    public String toString() {
        return "Service{" +
                "serviceName='" + serviceName + '\'' +
                '}';
    }
}
