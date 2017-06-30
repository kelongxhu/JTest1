package com.common.validate;

import com.common.annotation.Validate;
import com.common.exception.BusinessException;
import com.common.exception.OpenErrorCode;
import com.util.ProxyUtil;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.validation.ConstraintViolation;
import javax.validation.UnexpectedTypeException;
import javax.validation.ValidatorFactory;
import javax.validation.executable.ExecutableValidator;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;

/**
 */
public class ValidateArgsAOP {

    private final Logger LOGGER = Logger.getLogger(getClass());

    private static final String VALUE_RESULT_TEMPLATE = ", 当前值为[%s]";

    private ValidatorFactory validatorFactory;

    private static final String UN_EXPECTED_TYPE_ERROR_MSG = "不匹配的校验参数类型,exception:[%s]";

    /**
     * 校验切面方法
     *
     * @param pjp
     * @return
     * @throws Throwable
     */
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        Signature signature = pjp.getSignature();
        if (signature instanceof MethodSignature) {    //判断是否是此签名方法的子接口或者实现
            MethodSignature method = (MethodSignature) pjp.getSignature();
            Set<ConstraintViolation<Object>> constraintViolations = null; //定义校验结果集
            BindingResult bindingResult = null;
            try {
                Validate validate = method.getMethod().getAnnotation(Validate.class);
                if (validate != null) {
                    // 处理方法型传参校验
                    constraintViolations = validateMethodParameters(pjp.getTarget(), method.getMethod(), pjp.getArgs());
                } else {
                    // 处理实体型传参校验
                    Annotation[][] annotations = method.getMethod().getParameterAnnotations();
                    for (int i = 0, size = annotations.length; i < size; i++) {    //通过参数顺序遍历注解
                        Annotation[] parameterAnnotations = annotations[i];
                        for (Annotation annotation : parameterAnnotations) {    //遍历单个参数的注解
                            Class<?> clazz = ProxyUtil.getJDKProxyUserClass(annotation);    //获取原始类
                            if (clazz != null && (clazz.isAssignableFrom(Validate.class))) {
                                // 只检验声明了Validate Annotation的参数
                                Object[] params = pjp.getArgs();
                                Object param = params[i];
                                constraintViolations = validateEntityParameters(param);
                            }
                        }
                    }
                }
                Class[] classes = method.getMethod().getParameterTypes();
                for (int i = 0; i < classes.length; i++) {
                    Class clazz = classes[i];
                    if (clazz != null && clazz.isAssignableFrom(BindingResult.class)) {
                        bindingResult = (BindingResult) pjp.getArgs()[i];
                        break;
                    }
                }
            } catch (UnexpectedTypeException e) {
                String errorMsg = String.format(UN_EXPECTED_TYPE_ERROR_MSG, e.getMessage());
                LOGGER.error(errorMsg);
                throw new BusinessException(OpenErrorCode.PARAM_INVALID.getErrorCode(), OpenErrorCode.PARAM_INVALID.getErrorMsg());
            }
            if (!CollectionUtils.isEmpty(constraintViolations)) {
                String validateResult = getValidResult(pjp, constraintViolations);
                LOGGER.error(validateResult);
                throw new BusinessException(OpenErrorCode.PARAM_INVALID.getErrorCode(), OpenErrorCode.PARAM_INVALID.getErrorMsg(), validateResult);
            }
            if (bindingResult != null && bindingResult.hasErrors()) {
                FieldError fieldError = (FieldError) bindingResult.getAllErrors().get(0);
                String errorMsg = fieldError.getField() + String.format(VALUE_RESULT_TEMPLATE, fieldError.getRejectedValue());
                LOGGER.error(errorMsg);
                throw new BusinessException(OpenErrorCode.PARAM_INVALID.getErrorCode(), OpenErrorCode.PARAM_INVALID.getErrorMsg(), errorMsg);
            }
        }
        return pjp.proceed();//执行该方法
    }

    /**
     * 封装校验结果
     *
     * @param constraintViolations
     * @return
     */
    private String getValidResult(ProceedingJoinPoint pjp, Set<ConstraintViolation<Object>> constraintViolations) {
        StringBuilder msg = new StringBuilder();
        String result = "";
        for (ConstraintViolation<Object> constraintViolation : constraintViolations) {
            result = constraintViolation.getPropertyPath() + " " + constraintViolation.getMessage() + String.format(VALUE_RESULT_TEMPLATE, constraintViolation.getInvalidValue());
            break;
        }
        for (ConstraintViolation<Object> constraintViolation : constraintViolations) {
            msg.append("method:[").append(pjp.getSignature().toString())
                    .append("] parameter:[").append(constraintViolation.getPropertyPath())
                    .append("] value:[").append(constraintViolation.getInvalidValue()).append("] ")
                    .append("]").append("message:[").append(constraintViolation.getMessage()).append("],");
        }
        if (msg.length() > 0) {
            msg.deleteCharAt(msg.length() - 1);
            LOGGER.error(msg.toString());
        }
        return result;
    }

    /**
     * 验证通过RequestParameter()方式传递的参数
     *
     * @param object
     * @param method
     * @param args
     */
    private Set<ConstraintViolation<Object>> validateMethodParameters(Object object, Method method, Object[] args) {
        ExecutableValidator executableValidator = validatorFactory.getValidator().forExecutables();
        return executableValidator.validateParameters(object, method, args);
    }

    /**
     * 验证通过实体Bean方式传递的参数
     *
     * @param object
     * @return
     */
    private Set<ConstraintViolation<Object>> validateEntityParameters(Object object) {
        return validatorFactory.getValidator().validate(object);
    }

    public ValidatorFactory getValidatorFactory() {
        return validatorFactory;
    }

    public void setValidatorFactory(ValidatorFactory validatorFactory) {
        this.validatorFactory = validatorFactory;
    }
}
