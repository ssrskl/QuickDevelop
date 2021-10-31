package com.maoyan.quickdevelop.admin.controller.system.dqsection;

import com.github.pagehelper.PageInfo;
import com.maoyan.quickdevelop.admin.controller.system.BaseController;
import com.maoyan.quickdevelop.common.core.AjaxResult;
import com.maoyan.quickdevelop.common.core.domain.DqSection;
import com.maoyan.quickdevelop.common.core.domain.postprocessor.DqSectionPostProcessor;
import com.maoyan.quickdevelop.common.core.domain.postprocessor.DqUserPostProcessor;
import com.maoyan.quickdevelop.system.service.IDqSectionService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dqsection")
public class DqSectionController extends BaseController {
  @Autowired
  private IDqSectionService iDqSectionService;

  @ApiOperation(value = "通用查询版块")
  @GetMapping("/list")
  public AjaxResult follow(@RequestParam(defaultValue = "1") int pageNum,
                           @RequestParam(defaultValue = "10") int pageSize,
                           DqSection dqSection) {
    List<DqSectionPostProcessor> dqSectionPostProcessors = iDqSectionService.commonSelectDqSectionPostProcessor(pageNum, pageSize, dqSection);
    PageInfo<DqSectionPostProcessor> pageInfo = new PageInfo<>(dqSectionPostProcessors);
    return AjaxResult.success("查询成功", pageInfo);
  }
}
