package com.zyp.validation;

import com.zyp.anno.State;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StateValidation implements ConstraintValidator<State,String> {
    //<给哪个注解提供校验规则，校验的数据类型>
    /*
    * @param s 要校验的数据
    * @param constraintValidatorContext
    * @return 如果返回falae 则是校验不通过 如果返回true则校验通过
    * */
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(s==null){
            return false;
        }
        if(s.equals("已发布") || s.equals("草稿")){
            return true;
        }
        return false;
    }
}
