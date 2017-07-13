package com.tao.ioc_aop.aop.core;

/**
 * Created by Michael on 2017/6/19.
 */

import com.tao.ioc_aop.annotation.Match;
import com.tao.ioc_aop.utils.ClassUtils;
import com.tao.ioc_aop.utils.RegExUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * 匹配类和方法
 */
public class Matcher {

    private static final Logger logger = LoggerFactory.getLogger(Matcher.class);


    /**
     * 查看类的方法是否匹配
     */
    private String methodMatcher;

    /**
     * 切面上是否有匹配器, 即类上面是否有@Match注解
     * 默认为false, 表示没有匹配器。
     */
    private boolean hasMatcher;


    /**
     * 获取实现类上面的@Match注解，修改方法匹配参数methodMatcher的值。
     * @param interfaceClazz -- 接口的Class对象
     * @param implementClazz -- 实现类的Class对象
     */
    public Matcher(Class<?> interfaceClazz, Class<?> implementClazz) {

        //获取实现类上的@Match注解
        Match match1 = implementClazz.getAnnotation(Match.class);
        //设置Matcher类中的两个变量
        this.setMethodMatcher(match1);

        // 获取接口的第一个方法（每个接口只有一个方法。如BeforeAdvice接口只有一个before方法）
        Method method = interfaceClazz.getMethods()[0];
        logger.info("method name is {}", method.getName());
        //获取方法上面的注解
        Match match2 = ClassUtils.getMethodAnnotation(interfaceClazz,
                method.getName(), method.getParameterTypes(), Match.class);

        this.setMethodMatcher(match2);

        logger.info(method + "注解信息为：" + this.methodMatcher);
    }


    /**
     * 设置方法匹配器的值
     * @param match -- @Match注解对象
     */
    private void setMethodMatcher(Match match) {

        if(match != null) {
            //如果有@Match注解,设置前面的两个变量的值
            this.hasMatcher = true;
            this.methodMatcher = match.methodMatch();
        }
    }


    /**
     * 查看方法匹配器中的匹配和指定要匹配的方法是否一致
     * @param method -- 目标对象的方法
     * @param targetClass -- 目标对象所属的类
     * @return
     */
    public boolean matches(Method method, Class<? extends Object> targetClass) {

        //如果方法上面没有@Match注解
        if(!hasMatcher) {
            return true;
        }

        //获取方法名 -- 类名.方法名
        String targetMethodName = targetClass.getName() + "." + method.getName();
        String defineMatcher = this.methodMatcher;
        //logger.info("匹配样式为{},这个方法的样式为{}",defineMatcher,targetMethodName);

        return this.match(targetMethodName, defineMatcher);
    }


    /**
     * 利用正则进行匹配
     * @param targetMethodName
     * @param defineMatcher
     * @return
     */
    private boolean match(String targetMethodName, String defineMatcher) {

        return RegExUtils.match(defineMatcher, targetMethodName);
    }


}











