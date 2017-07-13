package com.tao.ioc_aop.xml.element;

/**
 * Created by Michael on 2017/6/5.
 */

/**
 * 代表一个type属性节点
 */
public class TypeAttr implements Attr {

    private Object value;

    public TypeAttr(Object value) {
        this.value = value;
    }

    @Override
    public String getAttrType() {

        return "type";
    }

    @Override
    public Object getValue() {

        return this.value;
    }
}
