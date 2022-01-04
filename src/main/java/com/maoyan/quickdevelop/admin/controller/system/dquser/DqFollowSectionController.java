package com.maoyan.quickdevelop.admin.controller.system.dquser;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.github.pagehelper.PageInfo;
import com.maoyan.quickdevelop.admin.controller.system.BaseController;
import com.maoyan.quickdevelop.common.annotation.Log;
import com.maoyan.quickdevelop.common.constant.HttpStatus;
import com.maoyan.quickdevelop.common.core.AjaxResult;
import com.maoyan.quickdevelop.common.core.domain.postprocessor.DqFollowSectionPostProcesser;
import com.maoyan.quickdevelop.common.core.domain.postprocessor.DqSectionPostProcessor;
import com.maoyan.quickdevelop.common.core.domain.postprocessor.DqUserPostProcessor;
import com.maoyan.quickdevelop.common.enums.BusinessType;
import com.maoyan.quickdevelop.system.service.IDqFollowSectionService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dquser/dqsection")
public class DqFollowSectionController extends BaseController {
  @Autowired
  private IDqFollowSectionService iDqFollowSectionService;

  @ApiOperation(value = "查询指定用户关注的版块")
  @GetMapping("/followed")
  public AjaxResult follow(@RequestParam(defaultValue = "1") int pageNum,
                           @RequestParam(defaultValue = "10") int pageSize,
                           Long dqUserId) {
    List<DqFollowSectionPostProcesser> dqFollowSectionPostProcesserList = iDqFollowSectionService.selectFollowedDqSectionByUserId(pageNum, pageSize, dqUserId);
    PageInfo<DqFollowSectionPostProcesser> pageInfo = new PageInfo<>(dqFollowSectionPostProcesserList);
    return AjaxResult.success("查询成功", pageInfo);
  }

  @ApiOperation(value = "查询指定版块的粉丝")
  @GetMapping("/fans")
  public AjaxResult fans(@RequestParam(defaultValue = "1") int pageNum,
                         @RequestParam(defaultValue = "10") int pageSize,
                         Long dqSectionId) {
    List<DqFollowSectionPostProcesser> dqFollowSectionPostProcesserList = iDqFollowSectionService.selectFollowerBySectionId(pageNum, pageSize, dqSectionId);
    PageInfo<DqFollowSectionPostProcesser> pageInfo = new PageInfo<>(dqFollowSectionPostProcesserList);
    return AjaxResult.success("查询成功", pageInfo);
  }

  @SaCheckLogin
  @ApiOperation(value = "关注指定版块")
  @Log(title = "关注版块", businessType = BusinessType.INSERT)
  @GetMapping("/follow")
  public AjaxResult followDqUser(Long dqSectionId) {
    int i = iDqFollowSectionService.followDqSectionBySectionId(dqSectionId);
    return AjaxResult.success("关注成功", HttpStatus.SUCCESS);
  }

  @SaCheckLogin
  @ApiOperation(value = "取消关注指定版块")
  @Log(title = "取消关注版块", businessType = BusinessType.DELETE)
  @GetMapping("/cancelfollow")
  public AjaxResult cancelFollowDqUser(Long dqSectionId) {
    int i = iDqFollowSectionService.cancelFollowDqSectionBySectionId(dqSectionId);
    return AjaxResult.success("取消关注成功", HttpStatus.SUCCESS);
  }
}
