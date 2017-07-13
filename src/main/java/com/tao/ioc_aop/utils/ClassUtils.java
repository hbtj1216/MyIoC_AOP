package com.tao.ioc_aop.utils;

/**
 * Created by Michael on 2017/6/6.
 */

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * 获取Class对象的工具类
 */
public class ClassUtils {

    private ClassUtils() {
    }

    /**
     * 获取参数Object对象对应的class对象
     * @param obj
     * @return
     */
    public static Class<?> getClass(Object obj) {

        if(obj instanceof Byte) {
            return Byte.TYPE;
        } else if(obj instanceof Boolean) {
            return Boolean.TYPE;
        } else if(obj instanceof Character) {
            return Character.TYPE;
        } else if(obj instanceof Short) {
            return Short.TYPE;
        } else if(obj instanceof Integer) {
            return Integer.TYPE;
        } else if(obj instanceof Long) {
            return Long.TYPE;
        } else if(obj instanceof Float) {
            return Float.TYPE;
        } else if(obj instanceof Double) {
            return Double.TYPE;
        }

        return obj.getClass();
    }


    /**
     * 获取setter方法的参数类型的Class对象
     * @param objClazz  对象
     * @param setterMethodName  对象的setter方法名
     * @return
     */
    public static Class<?> getParameterClass(Class<?> objClazz, String setterMethodName) {

        //获取所有的方法
        Method[] methods = objClazz.getMethods();
        for (Method method : methods) {
            //如果方法名和setterMethodName相同
            if(setterMethodName.equals(method.getName())) {
                //获取setter方法的参数的类型的集合
                Class<?>[] paramTypes = method.getParameterTypes();
                if(paramTypes.length == 1) {
                    //只有一个参数
                    return paramTypes[0];
                }
            }
        }
        return null;
    }


    /**
     * 根据setter参数类型，返回对应的参数值
     * @param paramClazz
     * @param data
     * @return
     */
    public static Object getParameterValue(Class<?> paramClazz, Object data) {

        if(paramClazz == Byte.TYPE || paramClazz == Byte.class) {
            return Byte.valueOf((String) data);
        }
        if(paramClazz == Boolean.TYPE || paramClazz == Boolean.class) {
            return Boolean.valueOf((String) data);
        } else if(paramClazz == Character.TYPE || paramClazz == Character.class) {
            return ((String) data).charAt(0);
        } else if(paramClazz == Short.TYPE || paramClazz == Short.class) {
            return Short.valueOf((String) data);
        } else if(paramClazz == Integer.TYPE || paramClazz == Integer.class) {
            return Integer.valueOf((String) data);
        } else if(paramClazz == Long.TYPE || paramClazz == Long.class) {
            return Long.valueOf((String) data);
        } else if(paramClazz == Float.TYPE || paramClazz == Float.class) {
            return Float.valueOf((String) data);
        } else if(paramClazz == Double.TYPE || paramClazz == Double.class) {
            return Double.valueOf((String) data);
        } else {
            return data;
        }
    }



    /**
     * 判断className的类型是否为基础类型。如java.lang.Integer, 是的话将数据进行转换
     * 成对应的类型该方法是供本类中的方法调用的，作用是根据type类型的值将对应的value数据转换
     * 成对应的type类型的值
     * @param className
     * @param data
     * @return
     */
    public static Object getValue(String className, Object data) {
        /**
         * 下面的所有if和else if都是判断是否是java的8中基本数据类型的包装类型
         */

        if (isType(className, "Byte") || isType(className, "byte")) {
            return Byte.valueOf((String) data);
        } else if (isType(className, "Boolean") || isType(className, "boolean")) {
            return Boolean.valueOf((String) data);
        } else if (isType(className, "Character") || isType(className, "char")) {
            //如果是Character类型则取第一个字符
            return ((String) data).charAt(0);
        } else if (isType(className, "Short") || isType(className, "short")) {
            return Short.valueOf((String) data);
        } else if (isType(className, "Integer") || isType(className, "int")) {
            return Integer.parseInt((String) data);
        } else if (isType(className, "Long") || isType(className, "long")) {
            return Long.valueOf((String) data);
        } else if (isType(className, "Float") || isType(className, "float")) {
            return Float.valueOf((String) data);
        } else if (isType(className, "Double") || isType(className, "double")) {
            return Double.valueOf((String) data);
        } else {
            //如果不是8种基本数据类型的包装类那么就是String类或者自定义的类了，直接返回该值
            return data;
        }
    }



    /**
     * 该方法是判断类名中是否含有对应的type字符串的方法，如判断className:java.lang.Integer中
     * 是否包含Integer这样就返回true，不包含则返回false，该方法是供上面的方法调用的
     * @param className
     * @param type
     * @return
     */
    private static boolean isType(String className, String type) {
        if (className.lastIndexOf(type) != -1)
            return true;
        return false;
    }


    /**
     * 通过类的Class对象，创建类的实例
     * @param className
     * @param paramType
     * @param args
     * @return
     */
    public static Object newInstance(String className, Class<?>[] paramType, Object[] args) {

        try {
            return Class.forName(className).getConstructor(paramType).newInstance(args);
        } catch (Exception e) {
            throw new RuntimeException("对象创建失败!", e);
        }
    }


    /**
     * 获取方法上面的注解
     * @param targetClass -- 目标对象的Class对象
     * @param methodName -- 目标对象的方法名
     * @param parameterTypes -- 方法参数的Class对象集合
     * @param annotationClass -- 注解的Class对象
     * @param <T>
     * @return
     */
    public static <T extends Annotation> T getMethodAnnotation(
            Class<?> targetClass, String methodName, Class<?>[] parameterTypes,
            Class<T> annotationClass) {

        try {
            return targetClass.getMethod(methodName, parameterTypes).getAnnotation(annotationClass);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("找不到这个方法" + methodName, e);
        }
    }
}

















