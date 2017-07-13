package com.tao.ioc_aop.xml.element;

/**
 * Created by Michael on 2017/6/5.
 */
public class NameAttr implements Attr {

    private Object value;

    public NameAttr(Object value) {
        this.value = value;
    }

    @Override
    public String getAttrType() {

        return "name";
    }

    @Override
    public Object getValue() {

        return this.value;
    }

    @Override
    public String toString() {
        return "NameAttr{" +
                "value=" + value +
                '}';
    }
}
