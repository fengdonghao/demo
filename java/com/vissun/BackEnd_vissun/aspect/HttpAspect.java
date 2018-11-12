package com.vissun.BackEnd_vissun.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class HttpAspect {
    private final static Logger logger= LoggerFactory.getLogger(HttpAspect.class);

    @Pointcut("execution(public * com.vissun.BackEnd_vissun.Controller.HomeController.*(..))||execution(public * com.vissun.BackEnd_vissun.Controller.AppController.*(..))||execution(public * com.vissun.BackEnd_vissun.Controller.AppHomeController.*(..))")
    public void log(){
    }

    @Before("log()")
    public void doBefore(JoinPoint joinPoint){
        ServletRequestAttributes attributes=(ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request=attributes.getRequest();
        logger.info("start");
        //url
        logger.info("url={}",request.getRequestURL());

        //method
        logger.info("method={}",request.getMethod());

        //ip
        logger.info("ip={}",request.getRemoteAddr());

        //类方法
        logger.info("class_method={}",joinPoint.getSignature().getDeclaringType()+"."+joinPoint.getSignature().getName());

        //参数
        logger.info("arg={}",joinPoint.getArgs());

    }
    @After("log()")
    public void doAfter(){
        logger.info("end");
    }
    /*@AfterReturning(returning = "object",pointcut = "log()")
    public void doAfterReturning(Object object) throws NullPointerException{
        if (object==null){
            throw new NullPointerException();
        }else{
            logger.info("response={}", object.toString());
        }
    }*/
}
