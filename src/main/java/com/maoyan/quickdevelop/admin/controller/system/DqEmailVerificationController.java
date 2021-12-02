package com.maoyan.quickdevelop.admin.controller.system;

import com.maoyan.quickdevelop.common.core.AjaxResult;
import com.maoyan.quickdevelop.system.service.IDqUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 猫颜
 * @date 2021 年 12 月 02 日 下午10:54
 */
@RestController
public class DqEmailVerificationController {
  private IDqUserService iDqUserService;

  @GetMapping("/verification/{emailVerificationCode}")
  public AjaxResult verification(@PathVariable String emailVerificationCode) {
    int i = iDqUserService.emailVerification(emailVerificationCode);
    return AjaxResult.success("验证成功", i);
  }
}
