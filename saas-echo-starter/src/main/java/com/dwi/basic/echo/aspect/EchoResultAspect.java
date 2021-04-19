package com.dwi.basic.echo.aspect;

import com.dwi.basic.annotation.echo.EchoResult;
import com.dwi.basic.echo.core.EchoService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * InjectionResult 注解的 AOP 工具
 *
 * @author dwi
 * @date 2020年01月19日09:27:41
 */
@Aspect
@AllArgsConstructor
@Slf4j
public class EchoResultAspect {
    private final EchoService echoService;


    @Pointcut("@annotation(com.dwi.basic.annotation.echo.EchoResult)")
    public void methodPointcut() {
    }


    @Around("methodPointcut()&&@annotation(ir)")
    public Object interceptor(ProceedingJoinPoint pjp, EchoResult ir) throws Throwable {
        Object proceed = pjp.proceed();
        echoService.action(proceed, ir.ignoreFields());
        return proceed;
    }
}
