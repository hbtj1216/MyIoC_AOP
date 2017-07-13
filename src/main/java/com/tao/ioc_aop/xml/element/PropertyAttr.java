package com.tao.ioc_aop.xml.element;

/**
 * Created by Michael on 2017/6/5.
 */
public class PropertyAttr {

    private String name;    //property的name
    private Attr value;     //值

    public PropertyAttr() {
    }

    public PropertyAttr(String name, Attr value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Attr getValue() {
        return value;
    }

    public void setValue(Attr value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "PropertyAttr{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}











