package com.zyp.anno;

import com.zyp.validation.StateValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.util.List;
import java.lang.annotation.*;

@Documented //元注解
@Target({ElementType.FIELD})    //元注解,表示该注解可以用在什么东西上面，类，属性，方法等
@Retention(RetentionPolicy.RUNTIME) //元注解，表示保留到什么时候
@Constraint(validatedBy = {StateValidation.class})  //指定提供校验规则的类
//@Repeatable(List.class)
public @interface State {
    //表示校验失败的时候，提示的信息
    String message() default "state的值只能是已发布或者草稿";

    //表示分组
    Class<?>[] groups() default {};

    //负载 能够获取到state注解的附加信息，一般用不到
    Class<? extends Payload>[] payload() default {};
}
