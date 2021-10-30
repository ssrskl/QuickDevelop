package com.maoyan.quickdevelop.admin.controller.system.dquser;

import com.github.pagehelper.PageInfo;
import com.maoyan.quickdevelop.admin.controller.system.BaseController;
import com.maoyan.quickdevelop.common.constant.HttpStatus;
import com.maoyan.quickdevelop.common.core.AjaxResult;
import com.maoyan.quickdevelop.common.core.domain.DqUser;
import com.maoyan.quickdevelop.common.core.domain.postprocessor.DqUserPostProcessor;
import com.maoyan.quickdevelop.system.service.IDqFollowUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dquser")
public class DqFollowUserController extends BaseController {
  @Autowired
  private IDqFollowUserService iDqFollowUserService;

  @ApiOperation(value = "查询指定用户关注的用户")
  @GetMapping("/follow")
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
}
