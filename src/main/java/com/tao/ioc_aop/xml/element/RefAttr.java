package com.tao.ioc_aop.xml.element;

/**
 * Created by Michael on 2017/6/5.
 */

/**
 * 代表一个ref属性节点的元素
 */
public class RefAttr implements Attr {

    //引用的值
    private Object value;

    public RefAttr(Object value) {
        this.value = value;
    }

    @Override
    public String getAttrType() {

        return "ref";
    }

    @Override
    public Object getValue() {

        return this.value;
    }

    @Override
    public String toString() {
        return "RefAttr{" +
                "value=" + value +
                '}';
    }
}
