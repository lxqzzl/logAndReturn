package com.test.ge.common.aspect;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSONObject;

/**
 * aop 日志打印
 *
 * @author lxq
 * @since 2021.01.08
 */

@Aspect
@Component
public class LogAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 环绕通知
	 * @param joinPoint 连接点
	 * @return 切入点返回值
	 * @throws Throwable 异常信息
	 */
    @Around("@within(org.springframework.web.bind.annotation.RestController) || @annotation(org.springframework.web.bind.annotation.RestController)")
    public Object apiLog(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes != null ? attributes.getRequest() : null;
        // 开始打印相关参数
        logger.info("========================================== 请求开始 ==========================================");
        // 打印请求内容
        logger.info("请求URL         : {}", request.getRequestURL().toString());
        logger.info("请求类型         : {}", request.getMethod());
        logger.info("请求方法         : {}.{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
        logger.info("发起请求的IP     : {}", request.getRemoteAddr());
        logger.info("请求参数         : {}", JSONObject.toJSON(joinPoint.getArgs()));

        // 打印返回内容
        logger.info("返回参数         : {}", JSONObject.toJSON(result));

        // 执行耗时
        logger.info("请求耗时         : {} ms", System.currentTimeMillis() - startTime);

        // 结束打印相关参数
        logger.info("=========================================== 请求结束 ===========================================");
        // 每个请求之间空一行
        logger.info("");

        return result;
    }

    @Around("@within(org.springframework.stereotype.Service) || @annotation(org.springframework.stereotype.Service)")
    public Object serviceLog(ProceedingJoinPoint joinPoint) throws Throwable {
        long serverStartTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();

        // 开始打印相关参数
        logger.info("========================================== 请求开始 ==========================================");
        // 打印请求内容
        logger.info("请求方法         : {}.{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
        logger.info("请求参数         : {}", JSONObject.toJSON(joinPoint.getArgs()));

        // 打印返回内容
        logger.info("返回参数         : {}", JSONObject.toJSON(result));

        // 执行耗时
        logger.info("请求耗时         : {} ms", System.currentTimeMillis() - serverStartTime);

        // 结束打印相关参数
        logger.info("=========================================== 请求结束 ===========================================");
        // 每个请求之间空一行
        logger.info("");

        return result;
    }

    @Around("@within(org.springframework.web.bind.annotation.ExceptionHandler) || @annotation(org.springframework.web.bind.annotation.ExceptionHandler)")
    public Object errorLog(ProceedingJoinPoint joinPoint) throws Throwable {
        long serverStartTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        JSONObject globalErrorResult = JSONObject.parseObject(JSONObject.toJSONString(result));
        // 开始打印相关参数
        logger.error("========================================== 发生错误 ==========================================");
        // 打印请求内容
        logger.error("错误类型         : {}", globalErrorResult.getString("exceptionName"));
        logger.error("错误信息         : {}", globalErrorResult.getString("exceptionMsg"));

        // 打印返回内容
        logger.error("返回结果        : {}", JSONObject.toJSON(result));

        // 执行耗时
        logger.error("请求耗时         : {} ms", System.currentTimeMillis() - serverStartTime);

        // 结束打印相关参数
        logger.error("=========================================== 结束 ===========================================");
        // 每个请求之间空一行
        logger.error("");

        return result;
    }
}
