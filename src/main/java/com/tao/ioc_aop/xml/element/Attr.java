package com.tao.ioc_aop.xml.element;

/**
 * Created by Michael on 2017/6/5.
 */

/**
 * 属性接口
 */
public interface Attr {

    /**
     * 返回该属性标签的类型
     * @return
     */
    public String getAttrType();

    /**
     * 返回该属性标签的值
     * @return
     */
    public Object getValue();
}
