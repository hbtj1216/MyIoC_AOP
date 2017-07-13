package test.com.tao.ioc_aop.ioc.context;

import com.tao.ioc_aop.aop.core.proxy.AopProxyFactory;
import com.tao.ioc_aop.ioc.context.ApplicationContext;
import com.tao.ioc_aop.ioc.context.ClassPathXmlApplicationContext;
import com.tao.ioc_aop.model.*;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ClassPathXmlApplicationContext Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>$today</pre>
 */
public class ClassPathXmlApplicationContextTest {

    private static final Logger logger = LoggerFactory.getLogger(ClassPathXmlApplicationContextTest.class);

    private ApplicationContext ctx;

    @Before
    public void before() throws Exception {

        ctx = new ClassPathXmlApplicationContext(new String[]{"/applicationContext.xml"});

    }

    @After
    public void after() throws Exception {

        ctx = null;
    }

    @Test
    public void testGetBean() {

        //获取一个bean
        Classroom classroom1 = (Classroom) ctx.getBean("classroom1");
        System.out.println(classroom1);
        Classroom classroom2 = (Classroom) ctx.getBean("classroom2");
        System.out.println(classroom2);

        Student student1 = (Student) ctx.getBean("student1");
        System.out.println(student1);
        Student student2 = (Student) ctx.getBean("student2");
        System.out.println(student2);
        Student student3 = (Student) ctx.getBean("student3");
        System.out.println(student3);


        //AOP测试

        Service jdbcService = (Service) ctx.getBean("jdbcService");

        AopProxyFactory aopProxyFactory = new AopProxyFactory();
        TransactionAspect aspect = (TransactionAspect) ctx.getBean("transactionAspect");

        aopProxyFactory.setTarget(jdbcService);
        aopProxyFactory.addAdvice(aspect);

        IAop p = (IAop) aopProxyFactory.getProxy();
        p.provideService();

    }

    @Test
    public void testContainsBean() {

    }

    @Test
    public void testIsSingleton() {

    }

}













