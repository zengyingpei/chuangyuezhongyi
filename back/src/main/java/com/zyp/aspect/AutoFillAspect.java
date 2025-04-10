package com.zyp.aspect;

import com.zyp.anno.AutoFill;
import com.zyp.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

@Aspect
@Slf4j
@Component
public class AutoFillAspect {
    /**
     * @ description 定义切入点
     *
     * @ return void
     * @ author DELL
     */
    @Pointcut("execution(* com.zyp.mapper.*.*(..)) && @annotation(com.zyp.anno.AutoFill)")
    public void autoFillPointCut(){
    }

    @Before("autoFillPointCut()")   //写上切入点
    public void autoFill(JoinPoint joinPoint){
        log.info("进入通知");

        // 获得被拦截的方法的签名对象
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        // 获得方法上的注解
        AutoFill autoFill = signature.getMethod().getAnnotation(AutoFill.class);

        // 获取在注解上指明的要操作的sql语句的类型
        OperationType operationType = autoFill.value();

        // 获取被拦截的方法上的 方法参数
        Object[] args = joinPoint.getArgs();

        if(args==null || args.length==0){
            return;
        }

        // 约定大于配置， 我们约定 使用autoFill注解的方法 第一个参数是要操作的实体类型
        Object entity = args[0];

        // 准备要填充的数据
        LocalDateTime now = LocalDateTime.now();

        // 如果是插入
        if(operationType==OperationType.INSERT){
            try{
                Method setCreateTime = entity.getClass().getDeclaredMethod("setCreateTime", LocalDateTime.class);
                Method setUpdateTime = entity.getClass().getDeclaredMethod("setUpdateTime", LocalDateTime.class);

                setCreateTime.invoke(entity, now);
                setUpdateTime.invoke(entity, now);
            }catch (Exception e){
                log.error("自动填充失败");
            }
        }else{      // 修改
            try{
                Method setUpdateTime = entity.getClass().getDeclaredMethod("setUpdateTime", LocalDateTime.class);

                setUpdateTime.invoke(entity, now);
            }catch (Exception e){
                log.error("自动填充失败");
            }
        }
    }
}
