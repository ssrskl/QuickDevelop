package com.maoyan.quickdevelop.admin.controller.system.dquser;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.github.pagehelper.PageInfo;
import com.maoyan.quickdevelop.admin.controller.system.BaseController;
import com.maoyan.quickdevelop.common.annotation.Log;
import com.maoyan.quickdevelop.common.constant.HttpStatus;
import com.maoyan.quickdevelop.common.core.AjaxResult;
import com.maoyan.quickdevelop.common.core.domain.postprocessor.DqUserPostProcessor;
import com.maoyan.quickdevelop.common.enums.BusinessType;
import com.maoyan.quickdevelop.system.service.IDqFollowUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dquser/dquser")
public class DqFollowUserController extends BaseController {
  @Autowired
  private IDqFollowUserService iDqFollowUserService;

  @ApiOperation(value = "查询指定用户关注的用户")
  @GetMapping("/followed")
  public AjaxResult follow(@RequestParam(defaultValue = "1") int pageNum,
                           @RequestParam(defaultValue = "10") int pageSize,
                           Long dqUserId) {
    List<DqUserPostProcessor> dqUserPostProcessors = iDqFollowUserService.selectFollowedDqUserByUserId(pageNum, pageSize, dqUserId);
    PageInfo<DqUserPostProcessor> pageInfo = new PageInfo<>(dqUserPostProcessors);
    return AjaxResult.success("查询成功", pageInfo);
  }
  @ApiOperation(value = "查询指定用户的粉丝")
  @GetMapping("/fans")
  public AjaxResult fans(@RequestParam(defaultValue = "1") int pageNum,
                           @RequestParam(defaultValue = "10") int pageSize,
                           Long dqUserId) {
    List<DqUserPostProcessor> dqUserPostProcessors = iDqFollowUserService.selectFansByUserId(pageNum, pageSize, dqUserId);
    PageInfo<DqUserPostProcessor> pageInfo = new PageInfo<>(dqUserPostProcessors);
    return AjaxResult.success("查询成功", pageInfo);
  }
  @SaCheckLogin
  @ApiOperation(value = "关注指定用户")
  @Log(title = "关注用户", businessType = BusinessType.INSERT)
  @GetMapping("/follow")
  public AjaxResult followDqUser(Long dqUserId){
    int i = iDqFollowUserService.followDqUserByUserId(dqUserId);
    return AjaxResult.success("关注成功",HttpStatus.SUCCESS);
  }
  @SaCheckLogin
  @ApiOperation(value = "取消关注指定用户")
  @Log(title = "取消关注用户", businessType = BusinessType.DELETE)
  @GetMapping("/cancelfollow")
  public AjaxResult cancelFollowDqUser(Long dqUserId){
    int i = iDqFollowUserService.cancelFollowDqUserByUserId(dqUserId);
    return AjaxResult.success("取消关注成功",HttpStatus.SUCCESS);
  }
}
