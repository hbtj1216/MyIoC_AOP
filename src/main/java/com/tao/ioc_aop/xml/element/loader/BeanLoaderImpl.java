package com.tao.ioc_aop.xml.element.loader;

import org.dom4j.Document;
import org.dom4j.Element;

import java.util.*;

/**
 * Created by Michael on 2017/6/4.
 */

/**
 * 加载Bean元素的类
 */
public class BeanLoaderImpl implements ElementLoader {

    /**
     * 保存一个Document中的根节点(beans)下的所有bean元素的map
     * 其中，key对应bean中的id, value对应bean元素本身
     */
    Map<String, Element> beanMap = new HashMap<String, Element>();



    @Override
    public void loadAllElements(Document document) {

        //得到根节点(beans)下的所有节点(bean)的集合
        List<Element> elementList = document.getRootElement().elements();
        //保存在map中
        for(Element e : elementList) {
            String id = e.attributeValue("id");
            this.beanMap.put(id, e);
        }
    }


    @Override
    public Element getBeanElement(String id) {

        return this.beanMap.get(id);
    }


    @Override
    public Collection<Element> getAllElements() {

        return this.beanMap.values();
    }


    public int getBeansNum() {

        return this.beanMap.size();
    }



}














