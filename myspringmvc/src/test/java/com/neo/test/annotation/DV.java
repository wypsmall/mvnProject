package com.neo.test.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.PARAMETER})
public @interface DV {

	//是否可以为空
    boolean nullable() default false;
     
    //最大长度
    int maxLength() default 0;
     
    //最小长度
    int minLength() default 0;
     
    //提供几种常用的正则验证
    RegexType regexType() default RegexType.NONE;
     
    //自定义正则验证
    String regexExpression() default "";
     
    //参数或者字段描述,这样能够显示友好的异常信息
    String description() default "";
}
