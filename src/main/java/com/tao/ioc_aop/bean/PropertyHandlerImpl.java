package com.tao.ioc_aop.bean;

/**
 * Created by Michael on 2017/6/7.
 */

import com.tao.ioc_aop.exception.BeanCreateException;
import com.tao.ioc_aop.exception.PropertyException;
import com.tao.ioc_aop.utils.ClassUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 实现了PropertyHandler接口的类
 */
public class PropertyHandlerImpl implements PropertyHandler {



    @Override
    public Object setProperties(Object obj, Map<String, Object> properties) {

        try {

            //获得对象的Class对象
            Class<?> clazz = obj.getClass();

            for(String key : properties.keySet()) {
                String setterMethodName = this.getSetterMethodName(key);
                //获取属性对象的Class对象//有点问题
                /**
                 * 修改如下：
                 * 首先我们需要知道set方法的参数所属的类型
                 */
                //获取参数的类型
                Class<?> propertyClazz = ClassUtils.getParameterClass(clazz, setterMethodName);

                //获得obj对象的set方法
                Method setterMethod = null;
                try {
                    setterMethod = this.getSetterMethod(clazz, setterMethodName, propertyClazz);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                //通过反射
                /**
                 * 修改，需要根据参数类型将对应的propertyValue的类型进行转换
                 */
                Object propertyValue = ClassUtils.getParameterValue(propertyClazz, properties.get(key));

                Class<?> class11;
                class11 = propertyValue.getClass();

                setterMethod.invoke(obj, propertyValue);
            }

            return obj;

        } catch (IllegalArgumentException e) {
            throw new PropertyException("wrong argument " + e.getMessage());
        } catch (Exception e) {
            throw new PropertyException(e.getMessage());
        }



    }


    @Override
    public Map<String, Method> getSetterMethodsMap(Object obj) {

        //获取对象的所有setter方法
        List<Method> methodList = this.getSetterMethodList(obj);
        Map<String, Method> result = new HashMap<String, Method>();
        for(Method m : methodList) {
            //属性名
            String propertyName = this.getMethodNameWithOutSet(m.getName());
            result.put(propertyName, m);
        }
        return result;
    }




    @Override
    public void executeMethod(Object obj, Object argBean, Method method) {

        try {

            //参数类型
            Class<?>[] parameterTypes = method.getParameterTypes();
            //set方法的参数只有一个
            if(parameterTypes.length == 1) {
                if(isMethodArg(method, argBean.getClass())) {
                    method.invoke(obj, argBean);
                }
            }

        } catch (Exception e) {
            throw new BeanCreateException("自动装配异常" + e.getMessage());
        }

    }


    /**
     * 返回methodName所代表的setter方法的名称，去掉"set"之后的属性名
     * @param methodName
     * @return
     */
    private String getMethodNameWithOutSet(String methodName) {

        String propertyName = methodName.substring(3);
        String firstWord = propertyName.substring(0,1);
        String lowerCase = firstWord.toLowerCase();
        //属性名的第一个字母要小写
        return propertyName.replaceFirst(firstWord, lowerCase);
    }

    /**
     * 获取obj对象的所有setter方法
     * @param obj
     * @return
     */
    private List<Method> getSetterMethodList(Object obj) {

        Class<?> clazz = obj.getClass();
        Method[] methods = clazz.getMethods();

        List<Method> result = new ArrayList<Method>();

        for(Method m : methods) {
            if(m.getName().startsWith("set")) {
                //以set开头的方法
                result.add(m);
            }
        }
        return result;
    }


    private String getSetterMethodName(String propertyName) {
        return "set" + this.getFirstWordToUpperCase(propertyName);
    }

    /**
     * 首字母大写
     * @param s
     * @return
     */
    private String getFirstWordToUpperCase(String s) {
        String firstWord = s.substring(0,1);
        String upper = firstWord.toUpperCase();
        return upper + s.substring(1);
    }

    /**
     * 通过反射获得setter方法
     * @param objClazz
     * @param setterMethodName
     * @param argClazz
     * @return
     */
    private Method getSetterMethod(Class<?> objClazz, String setterMethodName, Class<?> argClazz)
            throws NoSuchMethodException {

        Method setterMethod = this.getMethod(objClazz, setterMethodName, argClazz);
        if(setterMethod == null) {
            //获取失败
            List<Method> methods = this.getMethods(objClazz, setterMethodName);
            Method method = this.findMethod(argClazz, methods);
            if(method == null) {
                throw new NoSuchMethodException(setterMethodName);
            }
            return method;
        } else {
            return setterMethod;
        }
    }


    /**
     * 获得对象的方法名为methodName，且参数的Class对象为argClazz的方法
     * @param objClazz -- 对象的Class对象
     * @param methodName -- 方法名
     * @param argClazz -- 参数的Class对象
     * @return -- 如果有该方法，返回方法对象，如果没有返回null
     */
    private Method getMethod(Class<?> objClazz, String methodName, Class<?> argClazz) {

        Method setterMethod;
        try {
            setterMethod = objClazz.getMethod(methodName, argClazz);
            return setterMethod;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 获取对象中所有方法名为methodName并且只有一个参数的方法集合
     * @param objClazz -- 对象的Class对象
     * @param methodName -- 方法名
     * @return
     */
    private List<Method> getMethods(Class<?> objClazz, String methodName) {

        //结果集合
        List<Method> methodList = new ArrayList<Method>();
        Method[] methods = objClazz.getMethods();
        //遍历
        for(Method m : methods) {
            //如果方法名相同
            if(m.getName().equals(methodName)) {
                //的到方法的所有参数
                Class<?>[] c = m.getParameterTypes();
                //如果只有一个参数，则加到结果集合中
                if(c.length == 1) {
                    methodList.add(m);
                }
            }
        }
        return methodList;
    }


    /**
     * 从方法集合中查找指定参数类型的方法
     * @param argClazz
     * @param methodList
     * @return
     */
    private Method findMethod(Class<?> argClazz, List<Method> methodList) {

        for(Method m : methodList) {
            //判断参数类型与方法的形参类型是否一致
            if(this.isMethodArg(m, argClazz)) {
                return m;
            }
        }
        return null;
    }

    /**
     * 判断方法method的形参的类类型是否与argClazz一样
     * @param method
     * @param argClazz
     * @return
     */
    private boolean isMethodArg(Method method, Class<?> argClazz) {

        Class<?>[] c = method.getParameterTypes();
        //只有一个形参的才符合要求
        if(c.length == 1) {
            try {
                /**
                 * 将参数类型(argClazz)与方法中的参数类型进行强制转换, 不抛异常说明
                 * 传入的参数是方法参数的子类的类型，或者就是方法参数的类型。
                 */
                argClazz.asSubclass(c[0]);
                return true;
            } catch (ClassCastException e) {
                //抛出异常，说明asSubclass转换失败
                return false;
            }
        }
        return false;
    }
}
















