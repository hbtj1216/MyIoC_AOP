package test.com.tao.ioc_aop.xml.document; 

import com.tao.ioc_aop.xml.document.XmlDocumentHolder;
import org.dom4j.Document;
import org.dom4j.Element;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

import java.util.List;

/** 
* XmlDocumentHolder Tester. 
* 
* @author <Authors name> 
* @since <pre>$today</pre>
* @version 1.0 
*/ 
public class XmlDocumentHolderTest { 

    private XmlDocumentHolder xmlDocumentHolder;

@Before
public void before() throws Exception {

    //在测试类创建之前，先生成XmlDocumentHolder对象
    xmlDocumentHolder = new XmlDocumentHolder();
} 

@After
public void after() throws Exception {

    xmlDocumentHolder = null;
} 

/** 
* 
* Method: getDocument(String filePath) 
* 
*/ 
@Test
public void testGetDocument() throws Exception { 

    //String filePath = this.getClass().getClassLoader().getResource("/main/resources/applicationContext.xml").getPath();
    String filePath = "D:\\Tao\\IDEAWorkspace\\MyIoC_AOP\\src\\main\\resources\\applicationContext.xml";
    Document document = xmlDocumentHolder.getDocument(filePath);
    if(document == null) {
        throw new Exception("document is null.");
    }
    Element root = document.getRootElement();
    List<Element> beanList = root.elements();
    for(Element e : beanList) {
        System.out.println("beanId = " + e.attributeValue("id"));
        System.out.println("beanClass = " + e.attributeValue("class"));
    }
} 


/** 
* 
* Method: readDocumentFromXmlFile(String filePath) 
* 
*/ 
@Test
public void testReadDocumentFromXmlFile() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = XmlDocumentHolder.getClass().getMethod("readDocumentFromXmlFile", String.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/ 
} 

} 
