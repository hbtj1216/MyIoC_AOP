package com.tao.ioc_aop.utils;

/**
 * Created by Michael on 2017/6/20.
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式工具类
 */
public class RegExUtils {

    /**
     * 判断给定字符串target是否符合正则表达式regex的格式
     * @param regex
     * @param target
     * @return
     */
    public static boolean match(String regex, String target) {

        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(target);
        return m.matches();
    }

}





