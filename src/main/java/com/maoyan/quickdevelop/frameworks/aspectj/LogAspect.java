package com.maoyan.quickdevelop.frameworks.aspectj;

import cn.dev33.satoken.stp.StpUtil;
import com.maoyan.quickdevelop.common.constant.HttpStatus;
import com.maoyan.quickdevelop.common.exception.CustomException;
import com.maoyan.quickdevelop.common.utils.StringUtils;
import com.maoyan.quickdevelop.common.annotation.Log;
import com.maoyan.quickdevelop.common.core.domain.DqOperLog;
import com.maoyan.quickdevelop.common.core.domain.DqUser;
import com.maoyan.quickdevelop.common.utils.DateUtils;
import com.maoyan.quickdevelop.common.utils.ServletUtils;
import com.maoyan.quickdevelop.common.utils.ip.IpUtils;
import com.maoyan.quickdevelop.system.service.IDqOperLogService;
import com.maoyan.quickdevelop.system.service.IDqUserService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerMapping;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;


/**
 * 操作日志记录处理(写操作)
 *
 * @author maoyan
 */
@Aspect
@Component
@Slf4j
public class LogAspect {

  @Autowired
  private IDqUserService iDqUserService;

  @Autowired
  private IDqOperLogService iDqOperLogService;

  /**
   * 拦截注解下的方法
   */
  @Pointcut("@annotation(com.maoyan.quickdevelop.common.annotation.Log)")
  public void logPointCut() {
  }

  @Before("logPointCut()")
  public void doBefore(JoinPoint joinPoint) {
    handleDqUserStatus();
  }

  /***
   * @Author: 猫颜
   * @Description: 处理完方法后执行
   * @DateTime: 下午6:11 2021/7/27
   * @Params:
   * @param joinPoint
   * @param jsonResult
   * @Return
   */
  @AfterReturning(pointcut = "logPointCut()", returning = "jsonResult")
  public void doAfterReturning(JoinPoint joinPoint, Object jsonResult) {
    handleLog(joinPoint, null, jsonResult);
  }

  /***
   * @Author: 猫颜
   * @Description: 拦截异常操作
   * @DateTime: 下午4:24 2021/7/28
   * @Params:
   * @param joinPoint
   * @param e
   * @Return
   */
  @AfterThrowing(value = "logPointCut()", throwing = "e")
  public void doAfterThrowing(JoinPoint joinPoint, Exception e) {
    log.error("拦截异常");
    handleLog(joinPoint, e, null);
  }

  /**
   * 对写操作的用户状态进行验证
   */
  protected void handleDqUserStatus() {
    long loginIdAsLong = StpUtil.getLoginIdAsLong();
    DqUser dqUser = iDqUserService.selectDqUserById(loginIdAsLong);
    boolean dqUserIsNull = Optional.ofNullable(dqUser).isPresent();
    if (!dqUserIsNull){
      throw new CustomException("您已经被封禁", HttpStatus.FORBIDDEN);
    }
  }

  protected void handleLog(final JoinPoint joinPoint, final Exception e, Object jsonResult) {
    DqUser nowDqUser = new DqUser();
    try {
      // 获得注解
      Log controllerLog = getAnnotationLog(joinPoint);
      if (controllerLog == null) {
        return;
      }
      if (!StringUtils.equals(controllerLog.title(), "用户注册")) {
        // 获取当前的用户
        nowDqUser = iDqUserService.selectDqUserById(StpUtil.getLoginIdAsLong());
      } else {
        nowDqUser.setUserName("用户注册");
      }

      // *========数据库日志=========*//
      DqOperLog operLog = new DqOperLog();
      operLog.setStatus("0");
      // 请求的地址
      String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
      operLog.setOperIp(ip);
      // 返回参数
      operLog.setJsonResult(joinPoint.toLongString());

      operLog.setOperUrl(ServletUtils.getRequest().getRequestURI());
      if (nowDqUser != null) {
        operLog.setOperUserName(nowDqUser.getUserName());
      }

      if (e != null) {
        operLog.setStatus("1");
        operLog.setErrorMsg(StringUtils.substring(e.getMessage(), 0, 255));
      }
      // 设置方法名称
      String className = joinPoint.getTarget().getClass().getName();
      String methodName = joinPoint.getSignature().getName();
      operLog.setMethod(className + "." + methodName + "()");
      // 设置请求方式
      operLog.setRequestMethod(ServletUtils.getRequest().getMethod());
      operLog.setOperTime(DateUtils.getNowDate());
      // 处理设置注解上的参数
      getControllerMethodDescription(joinPoint, controllerLog, operLog);
      // 保存数据库
      iDqOperLogService.insertDqOperLog(operLog);
    } catch (Exception exp) {
      // 记录本地异常日志
      log.error("==前置通知异常==");
      log.error("异常信息:{}", exp.getMessage());
      exp.printStackTrace();
    }


  }

  /**
   * 获取注解中对方法的描述信息 用于Controller层注解
   *
   * @param log     日志
   * @param operLog 操作日志
   * @throws Exception
   */
  public void getControllerMethodDescription(JoinPoint joinPoint, Log log, DqOperLog operLog) throws Exception {
    // 设置action动作
    operLog.setBusinessType(log.businessType().ordinal());
    // 设置标题
    operLog.setTitle(log.title());
    // 是否需要保存request，参数和值
    if (log.isSaveRequestData()) {
      // 获取参数的信息，传入到数据库中。
      setRequestValue(joinPoint, operLog);
    }
  }

  /**
   * 获取请求的参数，放到log中
   *
   * @param operLog 操作日志
   * @throws Exception 异常
   */
  private void setRequestValue(JoinPoint joinPoint, DqOperLog operLog) throws Exception {
    String requestMethod = operLog.getRequestMethod();
    if (HttpMethod.PUT.name().equals(requestMethod) || HttpMethod.POST.name().equals(requestMethod)) {
      String params = Arrays.asList(joinPoint.getArgs()).toString();
      operLog.setOperParam(StringUtils.substring(params, 0, 255));
    } else {
      Map<?, ?> paramsMap = (Map<?, ?>) ServletUtils.getRequest().getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
      operLog.setOperParam(StringUtils.substring(paramsMap.toString(), 0, 255));
    }
  }


  /**
   * 是否存在注解，如果存在就获取
   */
  private Log getAnnotationLog(JoinPoint joinPoint) throws Exception {
    Signature signature = joinPoint.getSignature();
    MethodSignature methodSignature = (MethodSignature) signature;
    Method method = methodSignature.getMethod();

    if (method != null) {
      return method.getAnnotation(Log.class);
    }
    return null;
  }

}
