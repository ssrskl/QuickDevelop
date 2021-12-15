package com.maoyan.quickdevelop.admin.controller.system.dquser;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.toolkit.ParameterUtils;
import com.github.pagehelper.PageInfo;
import com.maoyan.quickdevelop.admin.controller.system.BaseController;
import com.maoyan.quickdevelop.common.annotation.Log;
import com.maoyan.quickdevelop.common.constant.HttpStatus;
import com.maoyan.quickdevelop.common.core.AjaxResult;
import com.maoyan.quickdevelop.common.core.domain.DqUser;
import com.maoyan.quickdevelop.common.core.domain.postprocessor.DqUserPostProcessor;
import com.maoyan.quickdevelop.common.enums.BusinessType;
import com.maoyan.quickdevelop.system.service.IDqUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @author 猫颜
 * @date 2021/5/28 14:48
 * 用户控制类
 * TODO 用户控制类
 */
@RestController
@RequestMapping("/dquser")
public class DqUserController extends BaseController {

  @Autowired
  private IDqUserService iUserService;

  /**
   * @param pageNum
   * @param pageSize
   * @param dqUser
   * @return com.maoyan.quickdevelop.common.core.AjaxResult
   * @author 猫颜
   * @date 上午11:33
   */
  @ApiOperation(value = "查询所有用户")
  @GetMapping("/list")
  public AjaxResult list(@RequestParam(defaultValue = "1") int pageNum,
                         @RequestParam(defaultValue = "10") int pageSize,
                         DqUser dqUser) {
    List<DqUserPostProcessor> dqUsers = iUserService.commonSelectDqUsers(pageNum, pageSize, dqUser);
    PageInfo<DqUserPostProcessor> pageInfo = new PageInfo<>(dqUsers);
    return AjaxResult.success("查询成功", pageInfo);
  }

  /**
   * 更新用户
   *
   * @param newDqUser
   * @return
   */
  @SaCheckLogin
  @SaCheckPermission(value = "user-update")
  @ApiOperation(value = "更新用户自己")
  @Log(title = "用户操作", businessType = BusinessType.UPDATE)
  @PostMapping("/update")
  public AjaxResult update(@RequestBody DqUser newDqUser) {
    //更新完成用户后要刷新redis缓存
    int i = iUserService.updateDqUserSelf(newDqUser);
    return AjaxResult.success("更新成功", i);
  }

  /**
   * 通过ID查询用户
   *
   * @param dqUserId
   * @return
   */
  @ApiOperation(value = "通过ID查询用户")
  @GetMapping("/{dqUserId}")
  public AjaxResult getInfo(@PathVariable Long dqUserId) {
    //后面可以根据缓存中先查询
    DqUserPostProcessor dqUserById_server = iUserService.getDqUserById_Server(dqUserId);
    return AjaxResult.success("查询成功", dqUserById_server);
  }

  /**
   * 登出
   *
   * @return
   */
  @SaCheckLogin
  @ApiOperation(value = "登出账号")
  @GetMapping("/logout")
  public AjaxResult logout() {
    if (StpUtil.isLogin()) {
      StpUtil.logout();
      return AjaxResult.success("登出账号成功!");
    } else {
      return AjaxResult.error("您还未登陆!");
    }
  }

//    @GetMapping("/test")
//    public AjaxResult testMapper() {
//        DqUser dqUserById = iUserService.getDqUserById(1L);
//        return AjaxResult.success("查询成功", dqUserById);
//    }


  /**
   * 获取当前登陆的用户的信息
   *
   * @return com.maoyan.quickdevelop.common.core.AjaxResult
   * @author 猫颜
   * @date 下午7:06
   */
  @SaCheckLogin
  @GetMapping("/getnowuser")
  @ApiOperation(value = "获取当前登陆的用户")
  public AjaxResult getNowUser() {
    long nowUserId = StpUtil.getLoginIdAsLong();
    DqUserPostProcessor nowDqUserPostProcessor = iUserService.getDqUserById_Server(nowUserId);
    return AjaxResult.success("查询成功", nowDqUserPostProcessor);
  }

  @SaCheckLogin
  @SaCheckPermission(value = "user-delete")
  @GetMapping("/delete")
  @ApiOperation(value = "注销自己的用户（删除自己）")
  public AjaxResult deleteDqUser() {
    int i = iUserService.deleteDqUserMySelf();
    if (i > 0) {
      return AjaxResult.success("注销成功", i);
    } else {
      return AjaxResult.error("注销失败", i);
    }
  }
}
