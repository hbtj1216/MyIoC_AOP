package com.tao.ioc_aop.bean;

import com.tao.ioc_aop.exception.BeanCreateException;
import com.tao.ioc_aop.utils.ClassUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael on 2017/6/6.
 */

/**
 * 使用构造器创建bean的类
 */
public class BeanCreaterImpl implements BeanCreater {


    /**
     * 使用默认的无参构造器方法创建bean实例
     * @param className -- 要创建实例bean的类的全名，可以从bean标签中的class属性获得
     * @return
     */
    @Override
    public Object createBeanUseDefaultConstructor(String className) {

        try {

            Class<?> clazz = Class.forName(className);
            //使用反射,创建对象
            return clazz.newInstance();

        } catch (ClassNotFoundException e) {
            throw new BeanCreateException("没有找到" + className + "该类。" + e.getMessage());
        } catch (InstantiationException e) {
            throw new BeanCreateException("没有找到默认的无参构造函数。" + e.getMessage());
        } catch (IllegalAccessException e) {
            throw new BeanCreateException(e.getMessage());
        }

    }


    /**
     * 使用有参数的构造函数创建bean实例
     * @param className -- bean实例对应的类全名
     * @param args -- 构造函数参数对象
     * @return
     */
    @Override
    public Object createBeanUseDefineConstructor(String className, List<Object> args) {

        try {

            //获取参数's的Class对象数组
            Class<?>[] argsClasses = this.getArgsClasses(args);
            Class<?> clazz = Class.forName(className);
            //获得构造函数
            Constructor constructor = this.getConstructor(clazz, argsClasses);
            //根据带参构造函数创建bean实例
            return constructor.newInstance(args.toArray());

        } catch (ClassNotFoundException e) {
            throw new BeanCreateException("没有找到" + className + "该类。" + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 获得构造函数参数对象集合对应的class数组
     * @param args
     * @return
     */
    private Class<?>[] getArgsClasses(List<Object> args) {

        List<Class<?>> result = new ArrayList<Class<?>>();
        for(Object arg : args) {
            result.add(ClassUtils.getClass(arg));
        }
        Class<?>[] array = new Class[result.size()];
        return result.toArray(array);
    }


    /**
     * 通过反射获得构造函数
     * @param clazz
     * @param argsClasses
     * @return
     */
    private Constructor getConstructor(Class<?> clazz, Class<?>[] argsClasses) throws NoSuchMethodException {

        //获得带参构造函数
        Constructor<?> constructor = getProcessConstructor(clazz, argsClasses);

        //如果没得到构造器对象
        if(constructor == null) {
            //获取所有public的构造器对象
            Constructor<?>[] constructors = clazz.getConstructors();
            //遍历
            for (Constructor<?> c : constructors) {
                Class<?>[] paramClasses = c.getParameterTypes();
                if(paramClasses.length == argsClasses.length) {
                    if(isSameArgs(paramClasses, argsClasses)) {
                        return c;
                    }
                }
            }
        } else {
            return constructor;
        }

        throw new NoSuchMethodException("找不到指定的构造器");
    }


    /**
     * 获取贷参数的构造函数
     * @param clazz
     * @param argsClasses
     * @return
     */
    private Constructor getProcessConstructor(Class<?> clazz, Class<?>[] argsClasses) {

        try {
            //获得带参数的构造函数
            Constructor<?> constructor = clazz.getConstructor(argsClasses);
            return constructor;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }


    private boolean isSameArgs(Class<?>[] argClasses, Class<?>[] tempClasses) {

        for(int i = 0; i < argClasses.length; i++) {
            try {
                argClasses[i].asSubclass(tempClasses[i]);
                if(i == (argClasses.length - 1)) {
                    return true;
                }
            } catch (Exception e) {
                //转换失败，捕获异常
                break;
            }
        }
        return false;
    }
}


















