package com.maoyan.quickdevelop.admin.controller.manage.dqschool;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import com.maoyan.quickdevelop.common.annotation.Log;
import com.maoyan.quickdevelop.common.core.AjaxResult;
import com.maoyan.quickdevelop.common.enums.BusinessType;
import com.maoyan.quickdevelop.system.domain.DqSchoolCreateVO;
import com.maoyan.quickdevelop.system.domain.DqSchoolUpdateVO;
import com.maoyan.quickdevelop.system.service.manageservice.IDqSchoolManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * @author 猫颜
 * @date 2021 年 12 月 04 日 上午8:36
 */
@SaCheckPermission(value = {"admin-school"},mode = SaMode.OR)
@RestController
@RequestMapping(value = "/dqschoolmanage")
@Validated
public class DqSchoolManageController {
  @Autowired
  private IDqSchoolManageService iDqSchoolManageService;

  @SaCheckPermission(value = "")
  @PostMapping(value = "/create")
  @Log(title = "创建学校", businessType = BusinessType.INSERT)
  public AjaxResult createSchool(@Validated @RequestBody DqSchoolCreateVO dqSchoolVO) {
    int school = iDqSchoolManageService.createSchool(dqSchoolVO);
    return AjaxResult.success("创建成功", school);
  }

  @SaCheckLogin
  @PostMapping(value = "/update")
  @Log(title = "更新学校",businessType = BusinessType.UPDATE)
  public AjaxResult updateSchool(@Validated @RequestBody DqSchoolUpdateVO dqSchoolUpdateVO) {
    int i = iDqSchoolManageService.updateSchool(dqSchoolUpdateVO);
    return AjaxResult.success("更新成功",i);
  }
  @SaCheckLogin
  @PostMapping(value = "/delete")
  @Log(title = "删除学校",businessType = BusinessType.DELETE)
  public AjaxResult deleteSchool(@Validated @NotNull Long dqSchoolId) {
    int i = iDqSchoolManageService.deleteSchool(dqSchoolId);
    return AjaxResult.success("更新成功",i);
  }
}
