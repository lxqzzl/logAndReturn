package com.test.ge.common.annotation;

import com.test.ge.common.returns.PlatformResult;
import com.test.ge.common.returns.Result;

import java.lang.annotation.*;

/**
 * 自定义返回数据封装注解
 *
 * @author lxq
 */

@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ResponseResultAnnotation {
    Class<? extends Result>  value() default PlatformResult.class;
}
