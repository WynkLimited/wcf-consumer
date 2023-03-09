package in.wynk.consumerservice.annotation;

import com.github.annotation.analytic.core.service.AnalyticService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
@Aspect
public class TimeItAspect {

    @Around(value = "execution(@in.wynk.lib.analytics.TimeIt * *.*(..))")
    public Object timeIt(ProceedingJoinPoint pjp) throws Throwable {
        long startTime = System.currentTimeMillis();
        MethodSignature methodSignature = (MethodSignature) pjp.getStaticPart().getSignature();
        Method method = methodSignature.getMethod();
        try {
            Object[] args = pjp.getArgs();
            Object retVal = pjp.proceed(args);
            return retVal;
        } finally {
            AnalyticService.update(method.getName() + "-time", System.currentTimeMillis() - startTime);
        }
    }
}
