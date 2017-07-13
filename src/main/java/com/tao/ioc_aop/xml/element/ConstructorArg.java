package com.tao.ioc_aop.xml.element;

/**
 * Created by Michael on 2017/6/5.
 */

/**
 * 构造函数的一个形参
 */
public class ConstructorArg {

    private String type;    //形参得数据类型(int、java.lang.String...)
    private Attr value;     //形参的值

    public ConstructorArg() {
    }

    public ConstructorArg(String type, Attr value) {
        this.type = type;
        this.value = value;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setValue(Attr value) {
        this.value = value;
    }

    /**
     * 返回形参的数据类型
     * @return
     */
    public String getType() {

        return this.type;
    }

    /**
     * 返回形参的Attr对象，在Attr中可以返回形参的类型和值
     * @return
     */
    public Attr getValue() {

        return this.value;
    }
}
