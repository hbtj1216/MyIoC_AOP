package com.tao.ioc_aop.xml.element;

/**
 * Created by Michael on 2017/6/5.
 */

/**
 * 代表一个value属性节点
 */
public class ValueAttr implements Attr {

    private Object value;

    public ValueAttr(Object value) {
        this.value = value;
    }

    @Override
    public String getAttrType() {

        return "value";
    }

    @Override
    public Object getValue() {

        return this.value;
    }

    @Override
    public String toString() {
        return "ValueAttr{" +
                "value=" + value +
                '}';
    }
}
