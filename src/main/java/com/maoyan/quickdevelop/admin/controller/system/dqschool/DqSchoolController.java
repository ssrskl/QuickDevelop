package com.maoyan.quickdevelop.admin.controller.system.dqschool;

import com.github.pagehelper.PageInfo;
import com.maoyan.quickdevelop.admin.controller.system.BaseController;
import com.maoyan.quickdevelop.common.core.AjaxResult;
import com.maoyan.quickdevelop.common.core.domain.DqSchool;
import com.maoyan.quickdevelop.system.service.IDqSchoolService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dqschool")
public class DqSchoolController extends BaseController {
  @Autowired
  private IDqSchoolService iDqSchoolService;

  @ApiOperation(value = "通用查询学校")
  @GetMapping("/list")
  public AjaxResult commonSelectDqSchool(@RequestParam(defaultValue = "1") int pageNum,
                                         @RequestParam(defaultValue = "10") int pageSize,
                                         DqSchool dqSchool){
    List<DqSchool> dqSchools = iDqSchoolService.commonSelectDqSchool(pageNum, pageSize, dqSchool);
    return AjaxResult.success("查询成功",new PageInfo<>(dqSchools));
  }
  @ApiOperation(value = "查询指定用户的学校")
  @GetMapping(value = "/dquser")
  public AjaxResult selectDqSchoolByUserId(Long dqUserId){
    DqSchool dqSchool = iDqSchoolService.selectDqSchoolByUserId(dqUserId);
    return AjaxResult.success("查询成功",dqSchool);
  }
  @ApiOperation(value = "设置当前用户的学校")
  @GetMapping(value = "/set")
  public AjaxResult setSchoolForDqUser(Long dqSchoolId){
    int i = iDqSchoolService.setSchoolForDqUser(dqSchoolId);
    return AjaxResult.success("设置成功",i);
  }
}
