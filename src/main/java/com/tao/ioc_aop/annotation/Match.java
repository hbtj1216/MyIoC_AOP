package com.tao.ioc_aop.annotation;

import java.lang.annotation.*;

/**
 * Created by Michael on 2017/6/19.
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Match {

    String methodMatch() default "";
}
