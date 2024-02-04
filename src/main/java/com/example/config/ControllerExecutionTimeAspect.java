package com.example.config;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.deleteWhitespace;

@Aspect
@Component
@Slf4j
public class ControllerExecutionTimeAspect {


    // 定义切入点，拦截所有Controller的方法
    @Pointcut("execution(* com.example.controller..*.*(..))")
    public void pointcut() {
    }

    // 环绕通知，用于计算方法的执行时间
    @Around("pointcut()")
    public Object pointcut(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Signature signature = joinPoint.getSignature();
        Class clazz = signature.getDeclaringType();
        // 获取方法参数
        MethodSignature methodSignature = (MethodSignature) signature;
        String[] returnNameArr = methodSignature.getReturnType().getName().split("\\.");
        //方法名
        String method = signature.getName();
        //行数
        int num = lineNum("public" + returnNameArr[returnNameArr.length - 1] + method + "(", fileName(clazz));

        Object result = joinPoint.proceed(); // 执行Controller方法

        long elapsedTime = System.currentTimeMillis() - start;
        StringBuffer logInfo = new StringBuffer();
        // 记录类名和方法名
        logInfo.append("| Class Method   : ").append(signature.getDeclaringTypeName()).append(".").append(method).append("(").append(clazz.getSimpleName()).append(".java:")
                .append(num).append(")");
        log.info("{} took {} ms to execute.", logInfo, elapsedTime);

        return result;
    }

    @Around("pointcut()")
    public Object pointcut2(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Signature signature = joinPoint.getSignature();
        //方法名
        String method = signature.getName();

        Object result = joinPoint.proceed(); // 执行Controller方法

        long elapsedTime = System.currentTimeMillis() - start;
        StringBuffer logInfo = new StringBuffer();
        // 记录类名和方法名
        logInfo.append("| 2-Class Method   : ").append(signature.getDeclaringTypeName()).append(".").append(method);
        log.info("{} took {} ms to execute.", logInfo, elapsedTime);

        return result;
    }



    /**
     * @Description: 返回方法在该类的行数
     * @Param: codeFragment 方法名 fileName 类的文件名
     * @return: 所在行数
     */
    private static int lineNum(String codeFragment,String fileName) {
        int lineNum = 1;
        Path path = Paths.get(fileName);
        try {
            List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                lineNum = i + 1;
                if (deleteWhitespace(line).toLowerCase().startsWith(codeFragment.toLowerCase())) {
                    break;
                }
            }
        } catch (Exception e2) {
            log.error(e2.getMessage());
        }
        return lineNum;
    }

    /**
     * @Description: 获取类名
     * @Param: @clazz 类的字节码
     * @return: 返回文件名
     */
    private static String fileName(@SuppressWarnings("rawtypes") Class clazz) {
        StringBuilder classFile = new StringBuilder(System.getProperty("user.dir")).append(File.separator).append("src")
                .append(File.separator).append("main").append(File.separator).append("java");
        for (String temp : clazz.getName().split("\\.")) {
            classFile.append(File.separator).append(temp);
        }
        return classFile.append(".java").toString();
    }

    /**
     * 获取参数名和参数值
     * @param joinPoint
     * @return 返回JSON结构字符串
     */
    public String getParam(JoinPoint joinPoint) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        Object[] values = joinPoint.getArgs();
        String[] names = ((CodeSignature) joinPoint.getSignature()).getParameterNames();
        for (int i = 0; i < names.length; i++) {
            map.put(names[i], values[i]);
        }
        return JSONObject.toJSONString(map);
    }
}
