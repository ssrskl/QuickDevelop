package com.maoyan.quickdevelop.frameworks.aspectj;

import cn.dev33.satoken.stp.StpUtil;
import com.maoyan.quickdevelop.common.core.domain.DqSection;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

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
    // 获得参数
    Object[] args = joinPoint.getArgs();
    System.out.println("进入Section方法");
    for (Object o : args) {
      // 如果属于DqSection，则权限判定
      if (o instanceof DqSection) {
        DqSection dqSection = (DqSection) o;
        System.out.println("开始权限判定");
        StpUtil.checkPermissionOr("admin-section", "section-manage" + dqSection.getSectionId());
      }
    }
  }
}
