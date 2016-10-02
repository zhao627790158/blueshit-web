package cn.blueshit.sharding.db;

import cn.blueshit.sharding.db.annotation.DoRoute;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by zhaoheng on 2016/5/20.
 */
@Aspect
public class DBRouterInterceptor {

    private static final Logger log = LoggerFactory.getLogger(DBRouterInterceptor.class);


    private final static Set<Class<?>> SINGLE_PARAM = new HashSet<Class<?>>();

    static {
        SINGLE_PARAM.add(int.class);
        SINGLE_PARAM.add(Integer.class);

        SINGLE_PARAM.add(long.class);
        SINGLE_PARAM.add(Long.class);

        SINGLE_PARAM.add(short.class);
        SINGLE_PARAM.add(Short.class);

        SINGLE_PARAM.add(byte.class);
        SINGLE_PARAM.add(Byte.class);

        SINGLE_PARAM.add(float.class);
        SINGLE_PARAM.add(Float.class);

        SINGLE_PARAM.add(double.class);
        SINGLE_PARAM.add(Double.class);

        SINGLE_PARAM.add(boolean.class);
        SINGLE_PARAM.add(Boolean.class);

        SINGLE_PARAM.add(char.class);
        SINGLE_PARAM.add(Character.class);

        SINGLE_PARAM.add(String.class);
    }


    @Resource
    private DbRouter dbRouter;

    @Pointcut("@annotation(cn.blueshit.sharding.db.annotation.DoRoute)")
    public void aopPoint() {
    }


    @Before(value = "aopPoint()")
    public Object doRoute(JoinPoint jp) throws Throwable {
        //获取目标对象的方法
        Object targer = jp.getTarget();//拦截的实体类
        String methodName = jp.getSignature().getName();//拦截的方法名称
        Object[] args = jp.getArgs();//拦截的方法参数
        //拦截方法的参数类型
        Class[] parameterTypes = ((MethodSignature) jp.getSignature()).getMethod().getParameterTypes();
        //根据类型和方法名 反射得到方法
        Method method = targer.getClass().getMethod(methodName, parameterTypes);
        DoRoute doRoute = method.getAnnotation(DoRoute.class);
        //只支持一个字段路由
        String routeField = doRoute.routeFiled();
        if (args != null && args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                if (SINGLE_PARAM.contains(args[i].getClass())) {
                    log.error("不支持的基本数据类型,请检查配置");
                    //不支持基本数据类型
                    continue;
                }
                //查看传递的参数是否包含路由字段,反射得到对象a的b字段的值-->如: user:{id:10,name:'test'}作为参数 routeField="id"->就会得到 10
                String routeFieldValue = BeanUtils.getProperty(args[i], routeField);
                if (StringUtils.isNotBlank(routeFieldValue)) {
                    if ("payIdxxxx".equals(routeField)) {
                        //根据业务拓展
                        dbRouter.doRouteByPayId(routeFieldValue);
                        break;
                    } else {
                        dbRouter.doRoute(routeFieldValue);
                        break;
                    }
                }
            }
        }
        return true;
    }

    @AfterReturning(value = "aopPoint()")
    public void after() {
        DbContextHolder.clearAll();
    }


}
