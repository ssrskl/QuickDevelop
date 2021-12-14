package com.maoyan.quickdevelop.frameworks.aspectj;

import com.maoyan.quickdevelop.frameworks.web.domain.server.Sys;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;


/**
 * 控制层记录处理
 * 
 * @author maoyan
 */
@Aspect
@Component
public class ControllerAspect
{

    /**
     * 拦截Controller
     * 拦截controller包下和子包下的所有方法
     */
    @Pointcut("execution(* com.maoyan.quickdevelop.admin.controller..*.*(..))")
    public void log(){}

    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        /**
         * 拿到request
         */
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();
        //通过JoinPoint拿到访问的类和方法
        String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "."+joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
//        RequestLog requestLog = new RequestLog(url,ip,classMethod,args);
        System.out.println("\n========================================== Start ==========================================");
//        System.out.println(requestLog.toString());
        System.out.println("\033[1;92m"+"URL: \033[0m"+url);
        System.out.println("\033[1;92m"+"HTTP_METHOD: \033[0m"+classMethod);
        System.out.println("\033[1;92m"+"IP: \033[0m"+ip);
        System.out.println("\033[1;92m"+"ARG[]: \033[0m"+Arrays.toString(args));

    }

    @AfterReturning(returning = "result",pointcut = "log()")
    public void doAfterReturn(Object result) {
        System.out.println("返回内容:"+result.toString());
        System.out.println("=========================================== End ===========================================\n");
    }


    private class RequestLog{
        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;

        public RequestLog(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "RequestLog{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }
}
