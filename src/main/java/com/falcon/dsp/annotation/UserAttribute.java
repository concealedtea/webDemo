package com.falcon.dsp.annotation;

import java.lang.annotation.*;

/**
 * @author dongbin.yu
 * @from 2016-04-19
 * @since V1.0
 * 用户账户方法参数自动包装|后续可以扩展属性添加
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UserAttribute {
}
