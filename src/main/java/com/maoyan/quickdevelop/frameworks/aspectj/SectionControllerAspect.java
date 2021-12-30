package com.maoyan.quickdevelop.frameworks.aspectj;

import cn.dev33.satoken.stp.StpUtil;
import com.maoyan.quickdevelop.common.annotation.Log;
import com.maoyan.quickdevelop.common.annotation.SectionPower;
import com.maoyan.quickdevelop.common.core.domain.DqSection;
import com.maoyan.quickdevelop.system.domain.DqSectionTypeVO;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author 猫颜
 * @date 2021 年 12 月 04 日 下午5:00
 */
@Aspect
@Component
public class SectionControllerAspect {
  /**
   * 拦截Section控制类的所有方法
   */
  @Pointcut("execution(* com.maoyan.quickdevelop.admin.controller.manage.dqsection..*.*(..))")
  public void sectionControllerAOP() {
  }

  @Before("sectionControllerAOP()")
  public void doBefore(JoinPoint joinPoint) {
    // 获取注解
//    SectionPower sectionPower = null;
//    try {
//      sectionPower = getAnnotationSectionpower(joinPoint);
//      if (sectionPower == null){
//        return;
//      }else {
//        // 此方法需要权限
//        StpUtil.checkPermissionOr("admin-section","section-mangae-"+);
//      }
//    } catch (Exception e) {
//      e.printStackTrace();
//    }
    // 获得参数
    Object[] args = joinPoint.getArgs();
//    System.out.println("进入Section方法");
    for (Object o : args) {
      // 如果属于DqSection，则权限判定
      if (o instanceof DqSection) {
        DqSection dqSection = (DqSection) o;
//        System.out.println("开始权限判定");
//        System.out.println("板块ID"+dqSection.toString());
        StpUtil.checkPermissionOr("section-manage-" + dqSection.getSectionId(),"admin-section");
      }
      if (o instanceof DqSectionTypeVO) {
        DqSectionTypeVO dqSectionTypeVO = (DqSectionTypeVO) o;
//        System.out.println("开始权限判定");
//        System.out.println("板块ID"+dqSection.toString());
        StpUtil.checkPermissionOr("section-manage-" + dqSectionTypeVO.getSectionId(),"admin-section");
      }
    }
  }
  /**
   * 是否存在注解，如果存在就获取
   */
  private SectionPower getAnnotationSectionpower(JoinPoint joinPoint) throws Exception {
    Signature signature = joinPoint.getSignature();
    MethodSignature methodSignature = (MethodSignature) signature;
    Method method = methodSignature.getMethod();
    if (method != null) {
      return method.getAnnotation(SectionPower.class);
    }
    return null;
  }
}
