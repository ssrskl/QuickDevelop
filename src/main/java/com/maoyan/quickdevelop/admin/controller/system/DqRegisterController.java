package com.maoyan.quickdevelop.admin.controller.system;

import com.maoyan.quickdevelop.common.annotation.Log;
import com.maoyan.quickdevelop.common.core.AjaxResult;
import com.maoyan.quickdevelop.common.core.domain.DqUser;
import com.maoyan.quickdevelop.common.core.domain.domainvo.DqUserRegisterVO;
import com.maoyan.quickdevelop.common.enums.BusinessType;
import com.maoyan.quickdevelop.system.domain.vo.RegisterVO;
import com.maoyan.quickdevelop.system.service.IDqRegisterService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
  public AjaxResult register(@Validated @RequestBody DqUserRegisterVO dqUserRegisterVO) {
    int i = iDqRegisterService.dqUserRegister(dqUserRegisterVO);
    //注册成功返回值为1，失败为0
    if (i > 0) {
      return AjaxResult.success("注册成功", i);
    } else {
      return AjaxResult.error("注册失败", i);
    }
  }
}
