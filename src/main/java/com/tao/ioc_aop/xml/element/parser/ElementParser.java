package com.tao.ioc_aop.xml.element.parser;

/**
 * Created by Michael on 2017/6/5.
 */

import com.tao.ioc_aop.xml.autowire.Autowire;
import com.tao.ioc_aop.xml.element.Attr;
import com.tao.ioc_aop.xml.element.ConstructorArg;
import com.tao.ioc_aop.xml.element.PropertyAttr;
import org.dom4j.Element;

import java.util.List;

/**
 * Element元素的解析接口
 */
public interface ElementParser {

    /**
     * 判断一个bean是否需要延迟加载
     * @param bean
     * @return
     */
    public boolean isLazy(Element bean);

    /**
     * 获得bean下面的所有constructor_arg(构造方法参数)元素
     * @param bean
     * @return
     */
    public List<Element> getAllConstructorArgsElements(Element bean);

    /**
     * 获得bean下面的所有constructor-arg元素的所有属性
     * @param bean
     * @return
     */
    public List<ConstructorArg> getConstructorAttrs(Element bean);

    /**
     * 获得bean下面的所有property元素
     * @param bean
     * @return
     */
    public List<Element> getAllPropertyElements(Element bean);

    /**
     * 获取一个bean下面的所有property元素的所有属性
     * @param bean
     * @return
     */
    public List<PropertyAttr> getPropertyAttrs(Element bean);

    /**
     * 获取所有property元素的所有属性
     * @param propertyElementList
     * @return
     */
    public List<PropertyAttr> getPropertyAttrs(List<Element> propertyElementList);

    /**
     * 获取元素的某一个name属性的值
     * @param element
     * @param name
     * @return
     */
    public String getAttributeValue(Element element, String name);

    /**
     * 判断一个bean是否是单例
     * @param bean
     * @return
     */
    public boolean isSingleton(Element bean);

    /**
     * 返回一个bean元素对应的Autowire对象
     * @param bean
     * @return
     */
    public Autowire getAutowire(Element bean);

}














