package cn.blueshit.testweb.utils;

import com.google.common.base.Stopwatch;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhaoheng on 16/8/31.
 */
@Component
@Aspect
public class TestAspectJ {


    @Pointcut("execution(* cn.blueshit.testweb.controller.TestController.testRestful(..))")
    public void deleGateway() {
    }

    @Before(value = "deleGateway()")
    public void doBefore() {
        System.out.println("befort");

    }


    @Around(value = "deleGateway()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint)  {
        Stopwatch started = Stopwatch.createStarted();
        for (Object object : proceedingJoinPoint.getArgs()) {
            System.out.println(object);
            if (object!=null&&Man.class.isAssignableFrom(object.getClass())) {
                System.out.println("1111");
                System.out.println(object.toString());
            }
            if (object!=null&&Persion.class.isAssignableFrom(object.getClass())) {
                System.out.println("2222");
                System.out.println(object.toString());
            }
        }
        System.out.println("time1 " + started.elapsed(TimeUnit.MILLISECONDS));

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        System.out.println("brfore" + proceedingJoinPoint.getSignature() + "\n");
        System.out.println("brfore" + proceedingJoinPoint.getSignature().getName() + "\n");
        System.out.println("brfore" + request + "\n");
        System.out.println("brfore" + proceedingJoinPoint.getArgs() + "\n");
        System.out.println("time2 " + started.elapsed(TimeUnit.MILLISECONDS));
        try {
            return proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }


}
