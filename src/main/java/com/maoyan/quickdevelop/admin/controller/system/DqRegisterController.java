package com.maoyan.quickdevelop.admin.controller.system;

import com.maoyan.quickdevelop.common.annotation.Log;
import com.maoyan.quickdevelop.common.core.AjaxResult;
import com.maoyan.quickdevelop.common.enums.BusinessType;
import com.maoyan.quickdevelop.system.domain.vo.RegisterVO;
import com.maoyan.quickdevelop.system.service.IDqRegisterService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 猫颜
 * @date 2021/5/28 11:21
 * 用户注册
 * TODO 用户注册控制类
 */
@RestController
public class DqRegisterController {

  @Autowired
  IDqRegisterService iDqRegisterService;

  @ApiOperation(value = "用户注册")
  @PostMapping("/register")
  @Log(title = "用户注册", businessType = BusinessType.INSERT)
  public AjaxResult register(@Validated @RequestBody RegisterVO registerVO) {
    int i = iDqRegisterService.dqUserRegister(registerVO);
    return AjaxResult.success("注册成功", i);
  }

  @GetMapping("/register/getverificationcode")
  public AjaxResult getVerificationCode(String dqUserMail) {
    // 根据邮箱获取验证码，则注册中不需要发送邮箱验证了.
    int emailVerificationCode = iDqRegisterService.getEmailVerificationCode(dqUserMail);
    return AjaxResult.success("验证码已发送",iDqRegisterService);
  }
}
