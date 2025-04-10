package com.zyp.anno;

import com.zyp.enumeration.OperationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoFill {
    /**
     * @ description 对数据库的操作的sql类型，比如： update、insert
     *
     * @ return com.zyp.enumeration.OperationType
     * @ author DELL
     */
    OperationType value();
}
