package test.com.tao.ioc_aop.xml.element.loader; 

import com.tao.ioc_aop.xml.document.XmlDocumentHolder;
import com.tao.ioc_aop.xml.element.loader.BeanLoaderImpl;
import org.dom4j.Document;
import org.dom4j.Element;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

/** 
* BeanLoaderImpl Tester. 
* 
* @author <Authors name> 
* @since <pre>$today</pre>
* @version 1.0 
*/ 
public class BeanLoaderImplTest { 

    private Document document;
    private BeanLoaderImpl beanLoader;


@Before
public void before() throws Exception {
    String filePath = "D:\\Tao\\IDEAWorkspace\\MyIoC_AOP\\src\\main\\resources\\applicationContext.xml";
    document = new XmlDocumentHolder().getDocument(filePath);
    beanLoader = new BeanLoaderImpl();
    beanLoader.loadAllElements(document);
} 

@After
public void after() throws Exception {
    document = null;
    beanLoader = null;
} 

/** 
* 
* Method: loadAllElements(Document document) 
* 
*/ 
@Test
public void testLoadAllElements() throws Exception { 

    beanLoader.loadAllElements(document);
} 

/** 
* 
* Method: getElement(String id) 
* 
*/ 
@Test
public void testGetElement() throws Exception { 

    Element firstBean = beanLoader.getBeanElement("firstBean");
    Element secondBean = beanLoader.getBeanElement("secondBean");

    System.out.println("bean的个数: " + beanLoader.getBeansNum());

    System.out.println("firstBean id = " + firstBean.attributeValue("id"));
    System.out.println("firstBean class = " + firstBean.attributeValue("class"));

    System.out.println("secondBean id = " + secondBean.attributeValue("id"));
    System.out.println("secondBean class = " + secondBean.attributeValue("class"));


} 

/** 
* 
* Method: getAllElements() 
* 
*/ 
@Test
public void testGetAllElements() throws Exception { 
//TODO: Test goes here... 
} 


} 
