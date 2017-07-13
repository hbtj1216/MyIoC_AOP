package com.tao.ioc_aop.xml.element.loader;

/**
 * Created by Michael on 2017/6/4.
 */

import org.dom4j.Document;
import org.dom4j.Element;

import java.util.Collection;
import java.util.List;

/**
 * 加载Element的接口
 */
public interface ElementLoader {

    /**
     * 加载Document中的所有Element
     * @param document
     */
    public void loadAllElements(Document document);

    /**
     * 根据id获取对应的Element
     * @param id
     * @return
     */
    public Element getBeanElement(String id);

    /**
     * 返回所有的Element
     * @return
     */
    public Collection<Element> getAllElements();
}











