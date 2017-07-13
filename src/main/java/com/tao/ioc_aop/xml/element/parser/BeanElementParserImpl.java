package com.tao.ioc_aop.xml.element.parser;

import com.tao.ioc_aop.xml.autowire.Autowire;
import com.tao.ioc_aop.xml.autowire.ByNameAutowire;
import com.tao.ioc_aop.xml.autowire.NoAutowire;
import com.tao.ioc_aop.xml.element.*;
import org.dom4j.Attribute;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael on 2017/6/5.
 */

/**
 * 实现类ElementParser接口的bean元素的解析类
 */
public class BeanElementParserImpl implements ElementParser {


    @Override
    public boolean isLazy(Element bean) {

        //得到该bean的lazy-init属性的值

        Boolean lazyStatus;
        if(this.getAttributeValue(bean, "lazy-init") == null) {
            lazyStatus = false;
        } else {
            lazyStatus = Boolean.valueOf(this.getAttributeValue(bean, "lazy-init"));
        }

        //得到该bean的父元素beans
        Element parentElement = bean.getParent();
        //获取beans的default-lazy-init属性的值
        Boolean parentLazyStatus;
        if(this.getAttributeValue(parentElement, "default-lazy-init") == null) {
            parentLazyStatus = false;
        } else {
            parentLazyStatus = Boolean.valueOf(this.getAttributeValue(parentElement, "default-lazy-init"));
        }

        //判断
        if(parentLazyStatus) {
            //beans延迟
            if(!lazyStatus) {
                return false;
            }
            return true;
        } else {
            //beans不延迟
            if(lazyStatus) {
                return true;
            }
            return false;
        }
    }

    @Override
    public List<Element> getAllConstructorArgsElements(Element bean) {

        //获取bean下的所有子元素
        List<Element> children = bean.elements();
        List<Element> result = new ArrayList<Element>();
        for(Element child : children) {
            if("constructor-arg".equals(child.getName())) {
                result.add(child);
            }
        }
        return result;
    }

    @Override
    public List<ConstructorArg> getConstructorAttrs(Element bean) {

        //获得constructor-arg元素的集合
        List<Element> constructorList = this.getAllConstructorArgsElements(bean);
        //保存所有形参的集合
        List<ConstructorArg> argsList = new ArrayList<ConstructorArg>();

        //对于每个constructor-arg
        for(Element constructor : constructorList) {
            //形参
            ConstructorArg constructorArg = new ConstructorArg();
            //获取constructor-arg元素的两个属性
            //type属性
            Attribute typeAttr = constructor.attribute(0);
            constructorArg.setType(typeAttr.getValue());    //获取形参类型
            //第二个属性
            Attribute secondAttr = constructor.attribute(1);
            //根据第二个属性的类型进行判断
            Attr param = this.getAttr(secondAttr);
            constructorArg.setValue(param);

            argsList.add(constructorArg);
        }
        return argsList;
    }

    @Override
    public List<Element> getAllPropertyElements(Element bean) {

        List<Element> children = bean.elements();
        List<Element> result = new ArrayList<Element>();
        for(Element child : children) {
            if("property".equals(child.getName())) {
                result.add(child);
            }
        }
        return result;
    }

    @Override
    public List<PropertyAttr> getPropertyAttrs(Element bean) {

        //获得该bean的所有property元素集合
        List<Element> propertyList = this.getAllPropertyElements(bean);
        List<PropertyAttr> attrList = new ArrayList<PropertyAttr>();

        for(Element property : propertyList) {
            PropertyAttr propertyAttr = new PropertyAttr();
            //获取第一个属性
            Attribute firstAttr = property.attribute(0);
            propertyAttr.setName(firstAttr.getName());
            //判断第二个属性
            Attr secondAttr = this.getAttr(property.attribute(1));
            propertyAttr.setValue(secondAttr);

            attrList.add(propertyAttr);
        }
        return attrList;
    }

    @Override
    public List<PropertyAttr> getPropertyAttrs(List<Element> propertyElementList) {

        List<PropertyAttr> attrList = new ArrayList<PropertyAttr>();

        for(Element property : propertyElementList) {
            PropertyAttr propertyAttr = new PropertyAttr();
            //获取第一个属性
            Attribute firstAttr = property.attribute(0);
            propertyAttr.setName(firstAttr.getValue());
            //判断第二个属性
            Attr secondAttr = this.getAttr(property.attribute(1));
            propertyAttr.setValue(secondAttr);

            attrList.add(propertyAttr);
        }
        return attrList;
    }

    @Override
    public String getAttributeValue(Element element, String name) {

        String value = element.attributeValue(name);
        return value;
    }

    @Override
    public boolean isSingleton(Element bean) {

        String value = bean.attributeValue("singleton");
        if(value == null) {
            return true;
        }
        return Boolean.valueOf(value);
    }

    @Override
    public Autowire getAutowire(Element bean) {

        //获得"autowire"的值
        String type = this.getAttributeValue(bean, "autowire");
        //父元素的"default-autowire"的值
        String parentType = this.getAttributeValue(bean.getParent(), "default-autowire");

        if("no".equals(parentType)) {
            if("byName".equals(type)) {
                return new ByNameAutowire(type);
            }
            return new NoAutowire(type);
        } else if("byName".equals(parentType)) {
            if("no".equals(type)) {
                return new NoAutowire(type);
            }
            return new ByNameAutowire(type);
        }
        return new NoAutowire(type);
    }


    /**
     * 根据attrbute所属的类型返回不同的Attr对象
     * @param attribute
     * @return
     */
    private Attr getAttr(Attribute attribute) {

        //获取名称
        String name = attribute.getName();
        String value = attribute.getValue();
        if("value".equals(name)) {
            return new ValueAttr(value);
        } else if("ref".equals(name)) {
            return new RefAttr(value);
        }
        return null;
    }


}













