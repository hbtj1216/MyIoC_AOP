package com.tao.ioc_aop.ioc.context;

import com.tao.ioc_aop.bean.BeanCreater;
import com.tao.ioc_aop.bean.BeanCreaterImpl;
import com.tao.ioc_aop.bean.PropertyHandler;
import com.tao.ioc_aop.bean.PropertyHandlerImpl;
import com.tao.ioc_aop.exception.BeanCreateException;
import com.tao.ioc_aop.utils.ClassUtils;
import com.tao.ioc_aop.xml.autowire.Autowire;
import com.tao.ioc_aop.xml.autowire.ByNameAutowire;
import com.tao.ioc_aop.xml.autowire.NoAutowire;
import com.tao.ioc_aop.xml.document.DocumentHolder;
import com.tao.ioc_aop.xml.document.XmlDocumentHolder;
import com.tao.ioc_aop.xml.element.*;
import com.tao.ioc_aop.xml.element.loader.BeanLoaderImpl;
import com.tao.ioc_aop.xml.element.loader.ElementLoader;
import com.tao.ioc_aop.xml.element.parser.BeanElementParserImpl;
import com.tao.ioc_aop.xml.element.parser.ElementParser;
import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;

/**
 * Created by michael on 17-6-10.
 */
public abstract class AbstractApplicationContext implements ApplicationContext {

    Logger logger = LoggerFactory.getLogger(AbstractApplicationContext.class);

    //文档持有者
    protected DocumentHolder documentHolder = new XmlDocumentHolder();

    //元素加载者
    protected ElementLoader beanLoader = new BeanLoaderImpl();

    //bean元素解析者
    protected ElementParser beanParser = new BeanElementParserImpl();

    //bean元素创建者
    protected BeanCreater beanCreater = new BeanCreaterImpl();

    //属性处理者
    PropertyHandler propertyHandler = new PropertyHandlerImpl();

    //保存bean实例的map
    protected Map<String, Object> beanMap = new HashMap<String, Object>();



    @Override
    public Object getBean(String id) {

        //从map中获取bean(单例bean)
        Object beanInstance = this.beanMap.get(id);

        if(beanInstance == null) {
            //如果没有, 调用handleBean()来处理(返回的不是单例bean)
            beanInstance = this.handleBean(id);
        }

        return beanInstance;

    }

    @Override
    public boolean containsBean(String id) {

        Element beanElement = beanLoader.getBeanElement(id);
        return (beanElement == null) ? false : true;
    }

    @Override
    public boolean isSingleton(String id) {

        Element beanElement = beanLoader.getBeanElement(id);
        return beanParser.isSingleton(beanElement);
    }


    /**
     * 读取多个xml文件, 初始化Element, 保存在beanLoader中的map中
     * @param xmlPaths
     */
    protected void initElements(String[] xmlPaths) {

        //获取项目的根路径classes/
        URL classPathUrl = AbstractApplicationContext.class.getClassLoader().getResource(".");
        //使用UTF-8进行解码
        String classPath = null;
        try {
            classPath = URLDecoder.decode(classPathUrl.getPath(), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        for(String path : xmlPaths) {
            //获得Document对象
            Document document = documentHolder.getDocument(classPath + path);
            //获取bean
            beanLoader.loadAllElements(document);
        }
    }


    /**
     * 实例化一个bean
     * @param id
     * @return
     */
    protected Object createBean(String id) {

        Element element = beanLoader.getBeanElement(id);
        if(element == null) {
            //没有找到对应的xml文件, 抛出异常
            throw new BeanCreateException("没找到 " + id+"对应的bean元素");
        }

        //如果找到bean所对应的<bean> Element, 实例化bean
        Object beanInstance = this.instanceBeanElement(element);
        logger.info("创建bean: " + id);

        //注入依赖
        //先判断是不是自动注入autowire
        Autowire autowire = beanParser.getAutowire(element);
        if(autowire instanceof ByNameAutowire) {
            //使用name自动装配
            this.autowireByName(beanInstance);
        } else if(autowire instanceof NoAutowire) {
            //使用setter注入
            this.setterInject(beanInstance, element);
        }

        logger.info("创建完毕的bean: " + beanInstance);
        //返回创建的bean
        return beanInstance;
    }



    /**
     * 由对应的Element实例化一个bean
     * @param beanElement
     * @return
     */
    protected Object instanceBeanElement(Element beanElement) {

        //获取类名
        String className = beanParser.getAttributeValue(beanElement, "class");
        //获取constructor-arg标签集合
        List<Element> constructorElements = beanParser.getAllConstructorArgsElements(beanElement);

        if(constructorElements.size() == 0) {
            //如果没有constructor-arg标签, 使用默认的无参构造函数实例化bean
            return beanCreater.createBeanUseDefaultConstructor(className);
        } else {
            //如果有constructor-arg标签, 使用带参数的构造函数实例化bean
            List<Object> args = this.getConstructorArgs(beanElement);
            return beanCreater.createBeanUseDefineConstructor(className, args);
        }
    }



    /**
     * 获得bean的构造函数参数集合
     * @param beanElement
     * @return
     */
    protected List<Object> getConstructorArgs(Element beanElement) {

        List<ConstructorArg> conArgList = beanParser.getConstructorAttrs(beanElement);
        List<Object> result = new ArrayList<Object>();

        for(ConstructorArg ca : conArgList) {
            Attr attr = ca.getValue();
            if(attr instanceof ValueAttr) {
                //如果是值类型
                /**
                 * 修改：若果是值类型，需要判断是哪种值类型
                 */
                String type = ca.getType(); //获取值的类型(int,float,String...)
                Object value = attr.getValue();
                //根据值得类型将值转换城对应的类型
                Object arg = ClassUtils.getValue(type, value);
                result.add(arg);
            } else if(attr instanceof RefAttr) {
                //如果是引用类型
                RefAttr temp = (RefAttr) attr;
                String refId = (String) temp.getValue();    //引用对象的名称id
                result.add(this.getBean(refId));
            }
        }
        return result;
    }


    /**
     * 处理id对应的bean, 如果是单例bean，则创建并加入到beanMap中，如果不是，则直接返回
     * @param id
     * @return
     */
    protected Object handleBean(String id) {

        Object beanInstance = this.createBean(id);

        if(this.isSingleton(id)) {
            this.beanMap.put(id, beanInstance);
        }
        return beanInstance;
    }


    /**
     * 自动装配bean
     * @param bean
     */
    protected void autowireByName(Object bean) {

        //得到bean中所有的setter方法
        Map<String, Method> setterMethodsMap = propertyHandler.getSetterMethodsMap(bean);

        for(String s : setterMethodsMap.keySet()) {
            //获得被注入的bean
            Element e = beanLoader.getBeanElement(s);
            if(e == null) {
                continue;
            }
            Object beanIns = this.getBean(s);
            //setter
            Method setterMethod = setterMethodsMap.get(s);
            //注入
            propertyHandler.executeMethod(bean, beanIns, setterMethod);
            logger.info("执行" + setterMethod.getName() + "方法, 给对象" + bean + "注入" + beanIns);

        }
    }


    /**
     * 通过配置中的<property>元素,使用setter方法为bean注入依赖
     * @param bean
     * @param beanElement
     */
    protected void setterInject(Object bean, Element beanElement) {

        //获取所有的property标签
        List<Element> propertyElementList = beanParser.getAllPropertyElements(beanElement);

        //获取要注入的属性map
        Map<String, Object> propertyMap = this.getPropertyArgs(propertyElementList);

        //注入
        propertyHandler.setProperties(bean, propertyMap);

    }

    /**
     * 获取参数对象的map集合
     * @param propertyElementList
     * @return
     */
    protected Map<String, Object> getPropertyArgs(List<Element> propertyElementList) {

        Map<String, Object> result = new HashMap<String, Object>();
        List<PropertyAttr> propertyAttrList = beanParser.getPropertyAttrs(propertyElementList);

        //logger.info(propertyAttrList.toString());

        for(PropertyAttr propertyAttr : propertyAttrList) {
            String propertyName = propertyAttr.getName();   //属性名称
            Attr propertyValue = propertyAttr.getValue();   //值
            if(propertyValue instanceof ValueAttr) {
                //值属性
                result.put(propertyName, propertyValue.getValue());
            } else if(propertyValue instanceof RefAttr) {
                //引用属性
                //获取引用的bean
                Object propertyBean = this.getBean((String) propertyValue.getValue());
                result.put(propertyName, propertyBean);
            }
        }
        return result;
    }


    /**
     * 创建所有的bean实例
     */
    protected void createAllBeans() {

        Collection<Element> beanElementList = beanLoader.getAllElements();
        for(Element beanElement : beanElementList) {
            //获取lazy的值
            boolean lazy = beanParser.isLazy(beanElement);
            if(!lazy) {
                String id = beanElement.attributeValue("id");
                Object beanInstance = this.getBean(id);
                if(beanInstance == null) {
                    this.handleBean(id);
                }
            }
        }
    }


}


















