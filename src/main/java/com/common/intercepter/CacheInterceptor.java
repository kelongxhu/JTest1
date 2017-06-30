package com.common.intercepter;

import com.alibaba.fastjson.JSON;
import com.common.annotation.Cacheable;
import com.google.common.base.Joiner;
import com.service.CacheService;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 缓存拦截器
 *
 */
@Component
@Aspect
public class CacheInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(CacheInterceptor.class);

    private static final String NULL_VALUE = "__NULL__";

    private static final long DEFAULT_EXPIRE_SECOND = 5 * 60L;

    @Autowired
    private CacheService cacheService;

    private KeyGenerator keyGenerator = new SimpleKeyGenerator();

    public CacheInterceptor() {
    }

    @Pointcut("@annotation(com.common.annotation.Cacheable)")
    private void pointcut() {
    }

    @Around("pointcut()")
    private Object process(ProceedingJoinPoint joinPoint) throws Throwable {
        if (!(joinPoint.getSignature() instanceof MethodSignature)) {
            return joinPoint.proceed();
        }
        MethodSignature methodSign = (MethodSignature) joinPoint.getSignature();
        Cacheable cacheAnno = getCacheAnnotatio(methodSign);
        if (cacheAnno == null) {
            return joinPoint.proceed();
        }
        return invokeCache(cacheAnno, joinPoint);
    }

    @Around(value = "@annotation(com.common.annotation.CacheEvict)")
    public Object evict(ProceedingJoinPoint pjp) throws Throwable {
        return pjp.proceed();
    }

    private Object invokeCache(Cacheable cacheable, ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSign = (MethodSignature) joinPoint.getSignature();
        Method method = methodSign.getMethod();
        String prefix = cacheable.prefix();
        String key = cacheable.key();
        String rkey = null;
        if (StringUtils.isNotBlank(key)) {
            key = parseKey(key, method, joinPoint.getArgs());
            rkey = Joiner.on("_").join(prefix, key);
        } else {
            keyFor(joinPoint);
        }
        String rvalue = cacheService.getValue(rkey);
        Object value = null;
        if (StringUtils.isBlank(rvalue)) {
            long expire = cacheable.expire() >= 0 ? cacheable.expire() : DEFAULT_EXPIRE_SECOND;
            value = joinPoint.proceed();
            cacheService.setValue(rkey, value == null ? NULL_VALUE : JSON.toJSONString(value), expire);
        } else {
            if (NULL_VALUE.equals(rvalue)) {
                return null;
            }
            Class<?> returnType = methodSign.getReturnType();
            if (List.class.isAssignableFrom(returnType)) {
                value = JSON.parseArray(rvalue, cacheable.type());
            } else {
                value = JSON.parseObject(rvalue, returnType);
            }
        }
        return value;
    }

    private Cacheable getCacheAnnotatio(MethodSignature signature) {
        return signature.getMethod().getAnnotation(Cacheable.class);
    }

    private String keyFor(ProceedingJoinPoint joinPoint) {
        MethodSignature methodSign = (MethodSignature) joinPoint.getSignature();
        Class targetClazz = joinPoint.getTarget().getClass();
        StringBuilder buff = new StringBuilder();
        buff.append(targetClazz.getName()).append(":").append(methodSign.getName());
        Class<?>[] parameterTypes = methodSign.getParameterTypes();
        if (parameterTypes != null && parameterTypes.length > 0) {
            Object[] args = joinPoint.getArgs();
            for (int i = 0; i < parameterTypes.length; i++) {
                buff.append(":").append(parameterTypes[i].getName()).append(":").append(ObjectUtils.nullSafeHashCode(args[i]));
            }
        }
        return buff.toString();
    }

    /**
     *	获取缓存的key
     *	key 定义在注解上，支持SPEL表达式
     * @param pjp
     * @return
     */
    private String parseKey(String key, Method method, Object[] args) {

        //获取被拦截方法参数名列表(使用Spring支持类库)
        LocalVariableTableParameterNameDiscoverer u =
                new LocalVariableTableParameterNameDiscoverer();
        String[] paraNameArr = u.getParameterNames(method);

        //使用SPEL进行key的解析
        ExpressionParser parser = new SpelExpressionParser();
        //SPEL上下文
        StandardEvaluationContext context = new StandardEvaluationContext();
        //把方法参数放入SPEL上下文中
        for (int i = 0; i < paraNameArr.length; i++) {
            context.setVariable(paraNameArr[i], args[i]);
        }
        return parser.parseExpression(key).getValue(context, String.class);
    }

}
