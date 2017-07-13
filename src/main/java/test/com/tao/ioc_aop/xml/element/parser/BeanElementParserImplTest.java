package test.com.tao.ioc_aop.xml.element.parser; 

import com.tao.ioc_aop.xml.document.XmlDocumentHolder;
import com.tao.ioc_aop.xml.element.loader.BeanLoaderImpl;
import com.tao.ioc_aop.xml.element.parser.BeanElementParserImpl;
import org.dom4j.Document;
import org.dom4j.Element;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;
import test.com.tao.ioc_aop.xml.element.loader.BeanLoaderImplTest;

/** 
* BeanElementParserImpl Tester. 
* 
* @author <Authors name> 
* @since <pre>$today</pre>
* @version 1.0 
*/ 
public class BeanElementParserImplTest { 

    private BeanElementParserImpl beanElementParser;
    private BeanLoaderImpl beanLoader;

@Before
public void before() throws Exception {
    String filePath = "D:\\Tao\\IDEAWorkspace\\MyIoC_AOP\\src\\main\\resources\\applicationContext.xml";
    XmlDocumentHolder xmlDocumentHolder = new XmlDocumentHolder();
    Document document = xmlDocumentHolder.getDocument(filePath);
    beanLoader = new BeanLoaderImpl();
    beanLoader.loadAllElements(document);
    beanElementParser = new BeanElementParserImpl();
} 

@After
public void after() throws Exception {
    beanLoader = null;
    beanElementParser = null;
} 

/** 
* 
* Method: isLazy(Element bean) 
* 
*/ 
@Test
public void testIsLazy() throws Exception { 

    Element firstBean = beanLoader.getBeanElement("firstBean");
    boolean isLazy = beanElementParser.isLazy(firstBean);
    System.out.println(isLazy);
    Element secondBean = beanLoader.getBeanElement("secondBean");
    isLazy = beanElementParser.isLazy(secondBean);
    System.out.println(isLazy);

} 

/** 
* 
* Method: getAllConstructorArgsElements(Element bean) 
* 
*/ 
@Test
public void testGetAllConstructorArgsElements() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getConstructorAttrs(Element bean) 
* 
*/ 
@Test
public void testGetConstructorAttrs() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getAllPropertyElements(Element bean) 
* 
*/ 
@Test
public void testGetAllPropertyElements() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getPropertyAttrs(Element bean) 
* 
*/ 
@Test
public void testGetPropertyAttrs() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getAttributeValue(Element element, String name) 
* 
*/ 
@Test
public void testGetAttributeValue() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: isSingleton(Element bean) 
* 
*/ 
@Test
public void testIsSingleton() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getAutowire(Element bean) 
* 
*/ 
@Test
public void testGetAutowire() throws Exception { 
//TODO: Test goes here... 
} 


/** 
* 
* Method: getAttr(Attribute attribute) 
* 
*/ 
@Test
public void testGetAttr() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = BeanElementParserImpl.getClass().getMethod("getAttr", Attribute.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/ 
} 

} 
