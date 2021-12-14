package com.maoyan.quickdevelop.admin.controller.manage.dqsection;

import com.maoyan.quickdevelop.common.annotation.Log;
import com.maoyan.quickdevelop.common.constant.HttpStatus;
import com.maoyan.quickdevelop.common.core.AjaxResult;
import com.maoyan.quickdevelop.common.core.domain.DqSection;
import com.maoyan.quickdevelop.common.enums.BusinessType;
import com.maoyan.quickdevelop.system.service.manageservice.IDqSectionManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 猫颜
 * @date 2021 年 12 月 04 日 上午8:29
 */

@Validated
@RestController
@RequestMapping(value = "/dqsectionmanage")
public class DqSectionManageController {
  @Autowired
  private IDqSectionManageService iDqSectionManageService;

  /**
   * 采用AOP鉴权
   *
   * @return
   */
  @Log(title = "版块修改", businessType = BusinessType.UPDATE)
  @PostMapping(value = "/update")
  public AjaxResult updateSection(DqSection dqSection) {
    int i = iDqSectionManageService.updateSection(dqSection);
    return AjaxResult.success("更新成功", i);
  }
}
