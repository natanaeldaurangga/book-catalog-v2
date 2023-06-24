package com.nael.catalog.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@Aspect
public class LoggingAspect {

    // @Pointcut("execution(*
    // com.nael.catalog.web.*.*(com.nael.catalog.DTO.PublisherCreateRequestDTO))")
    @Pointcut("execution(* com.nael.catalog.web.*.*(..))")
    private void restAPI() {
    }

    @Pointcut("within(com.nael.catalog.web.*)")
    private void withinPointcutExample() {
    }

    @Pointcut("args(com.nael.catalog.DTO.PublisherCreateRequestDTO)")
    private void argsPointcutExample() {
    }

    @Pointcut("@args(com.nael.catalog.anotation.LogThisArg)")
    private void argsAnnotationPointcutExample() {
    }

    @Pointcut("@annotation(com.nael.catalog.anotation.LogThisMethod)")
    private void annotationPointcutExample() {
    }

    // @Before("restAPI() && argsPointcutExample()") // menjelaskan bahwa aspek ini
    // akan dijalankan sebelum fungsi yang
    // @Before("restAPI() && argsAnnotationPointcutExample()") // menjelaskan bahwa
    // aspek ini akan dijalankan sebelum
    // fungsi
    // yang
    // diintercept dieksekusi
    @Before("annotationPointcutExample()")
    public void beforeExecutedExample() {
        log.info("this is log from before method executed");
    }

    @After("annotationPointcutExample()")
    public void afterExecutedExample() {
        log.info("this is log from after method executed");
    }

    @AfterReturning("annotationPointcutExample()")
    public void afterReturningExecutedExample() {
        log.info("this is log from after returning method executed");
    }

    // TODO: Lanjut ke aspect arround
    @AfterThrowing("annotationPointcutExample()")
    public void afterThrowingExecutedExample() {
        log.info("this is log from after throwing method executed");
    }

    @Around("restAPI()")
    public Object processingTimeLogging(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        try {
            log.info("start {}.{}: ", joinPoint.getTarget().getClass().getName(), joinPoint.getSignature().getName());
            // memanggil nama kelas dari join pointnya, memanggil signature dari method join
            // pointnya
            return joinPoint.proceed();
        } finally {
            stopWatch.start();
            log.info("finish {}.{} findBookDetail. execution time = {}", joinPoint.getTarget().getClass().getName(),
                    joinPoint.getSignature().getName(), stopWatch.getTotalTimeMillis());
        }
    }

}
