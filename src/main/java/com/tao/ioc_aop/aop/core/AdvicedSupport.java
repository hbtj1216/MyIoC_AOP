package com.tao.ioc_aop.aop.core;

/**
 * Created by Michael on 2017/6/19.
 */

import com.tao.ioc_aop.aop.advice.Advice;
import com.tao.ioc_aop.utils.ClassUtils;
import org.aopalliance.intercept.MethodInterceptor;

import java.util.ArrayList;
import java.util.List;

/**
 * 辅助支持类。
 * 封装目标对象、目标对象的类型、加载在目标对象上的所有Advice
 */
public class AdvicedSupport {

    /**
     * 被代理对象
     */
    private Object target;

    /**
     * 被代理对象的类类型
     */
    private Class<?> targetClass;

    /**
     * 加载在该target目标对象上的所有Advice
     */
    private List<Advice> allAdvice;

    /**
     * 加载在该目标对象上的所有的Advice所对应的方法拦截器
     */
    private List<MethodInterceptor> allInterceptor;

    /**
     * 拦截器的匹配信息。
     * matchers.get(i)表示第i个(i从0开始)的Advice拦截器所设置的匹配信息。
     * 如果没有设置，则匹配所有。如果设置了，则按照设置的信息进行匹配。
     */
    private List<Matcher> matchers;

    /**
     * 该对象实现的所有接口的Class对象集合
     */
    private Class<?>[] interfaces;

    /**
     * 该对象的类加载器
     */
    private ClassLoader classLoader;


    /**
     * 构造函数
     * @param target -- 被代理的目标对象
     * @param allAdvice -- 该对象的所有通知(Advice)的集合
     */
    public AdvicedSupport(Object target, List<Advice> allAdvice) {

        this.target = target;
        this.targetClass = target.getClass();
        this.allAdvice = allAdvice;
        this.interfaces = this.targetClass.getInterfaces();
        this.classLoader = this.targetClass.getClassLoader();
        this.allInterceptor = new ArrayList<MethodInterceptor>(allAdvice.size());
        this.matchers = new ArrayList<Matcher>(allAdvice.size());

        //获取所有Advice实现的接口
        for(Advice advice : allAdvice) {
            for(Class<?> interfaceClazz : advice.getClass().getInterfaces()) {
                //没太懂...TODO
                this.allInterceptor.add((MethodInterceptor) ClassUtils.newInstance(
                        interfaceClazz.getName()+"Interceptor",
                        new Class[] {interfaceClazz},
                        new Object[] {advice}));

                //将该advice对应的方法、类匹配信息添加到集合中
                this.matchers.add(new Matcher(interfaceClazz, advice.getClass()));
            }
        }
    }


    public Object getTarget() {
        return target;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }

    public List<Advice> getAllAdvice() {
        return allAdvice;
    }

    public List<MethodInterceptor> getAllInterceptor() {
        return allInterceptor;
    }

    public List<Matcher> getMatchers() {
        return matchers;
    }

    public Class<?>[] getInterfaces() {
        return interfaces;
    }

    public ClassLoader getClassLoader() {
        return classLoader;
    }


    /**
     * 被代理对象是否实现了接口
     * @return -- 如果实现了接口，返回true；如果没有实现接口，返回false。
     */
    public boolean hasInterfaces() {
        return this.targetClass.getInterfaces().length > 0;
    }

}
















