package com.maoyan.quickdevelop.admin.controller.system.dqsection;

import com.github.pagehelper.PageInfo;
import com.maoyan.quickdevelop.admin.controller.system.BaseController;
import com.maoyan.quickdevelop.common.core.AjaxResult;
import com.maoyan.quickdevelop.common.core.domain.DqSectionType;
import com.maoyan.quickdevelop.system.service.IDqSectionTypeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dqsection/type")
public class DqSectionTypeController extends BaseController {
  @Autowired
  private IDqSectionTypeService iDqSectionTypeService;

  @ApiOperation(value = "通用查询版块分类")
  @GetMapping("/list")
  public AjaxResult follow(@RequestParam(defaultValue = "1") int pageNum,
                           @RequestParam(defaultValue = "10") int pageSize,
                           DqSectionType dqSectionType) {
    List<DqSectionType> dqSectionTypes = iDqSectionTypeService.commonSelectSectionType(pageNum, pageSize, dqSectionType);
    PageInfo<DqSectionType> pageInfo = new PageInfo<>(dqSectionTypes);
    return AjaxResult.success("查询成功", pageInfo);
  }

}
