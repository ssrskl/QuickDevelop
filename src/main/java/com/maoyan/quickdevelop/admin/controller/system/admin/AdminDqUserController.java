package com.maoyan.quickdevelop.admin.controller.system.admin;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.StpUtil;
import com.github.pagehelper.PageInfo;
import com.maoyan.quickdevelop.common.constant.HttpStatus;
import com.maoyan.quickdevelop.common.core.AjaxResult;
import com.maoyan.quickdevelop.common.core.domain.DqUser;
import com.maoyan.quickdevelop.common.utils.DateUtils;
import com.maoyan.quickdevelop.common.utils.ip.IpUtils;
import com.maoyan.quickdevelop.system.domain.DqUserVO;
import com.maoyan.quickdevelop.system.domain.admin.AdminDqArticleVO;
import com.maoyan.quickdevelop.system.domain.admin.AdminDqUserVO;
import com.maoyan.quickdevelop.system.service.admin.IAdminDqArticleService;
import com.maoyan.quickdevelop.system.service.admin.IAdminDqUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @author 猫颜
 * @date 2021年07月18日 上午11:33
 * 类的作用：TODO 管理员的用户控制类
 */
@RestController
@RequestMapping("/admin/dquser")
public class AdminDqUserController {
    @Autowired
    private IAdminDqUserService iAdminDqUserService;


    /**
     * 管理员查询所有的用户
     * @author 猫颜
     * @date  下午3:18
     * @param pageNum
     * @param pageSize
     * @param dqUser
     * @return com.maoyan.quickdevelop.common.core.AjaxResult
     */
    @ApiOperation(value = "查询所有用户")
    @SaCheckPermission(value = {"admin-user"})
    @GetMapping("/list")
    public AjaxResult list(@RequestParam(defaultValue = "1") int pageNum,
                           @RequestParam(defaultValue = "10") int pageSize,
                           DqUser dqUser) throws NoSuchFieldException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        List<DqUser> dqUsers = iAdminDqUserService.selectDqUserList(pageNum, pageSize, dqUser);
        if (dqUsers.isEmpty()){
            return AjaxResult.error(HttpStatus.NOT_FOUND, "未查询到用户");
        }
        PageInfo<DqUser> pageInfo = new PageInfo<>(dqUsers);
        return AjaxResult.success("查询成功", pageInfo);
    }

    /**
     * 删除用户
     * 转移到admin中
     * @param dqUserId
     * @return
     */
    @SaCheckPermission("admin-user")
    @ApiOperation(value = "删除用户")
    @GetMapping("/remove/{dqUserId}")
    public AjaxResult remove(@PathVariable Long dqUserId) {
        int i = iAdminDqUserService.deleteDqUserById(dqUserId);
        if (i > 0) {
            return AjaxResult.success("删除成功", i);
        } else {
            return AjaxResult.success("删除失败", i);
        }
    }

    /**
     * 封号
     * 转移到admin中
     * @param dqUserId
     * @return
     */
    @SaCheckPermission("admin-user")
    @ApiOperation(value = "封号")
    @GetMapping("/ban/{dqUserId}")
    public AjaxResult ban(@PathVariable Long dqUserId) {
        int i = iAdminDqUserService.banUserById(dqUserId);
        if (i > 0) {
            //将其踢下线，并禁止登陆
            StpUtil.logoutByLoginId(dqUserId);
            //封禁账号(-1为永久封禁)
            StpUtil.disable(dqUserId, -1);
            return AjaxResult.success("封禁用户成功", i);
        } else {
            return AjaxResult.success("封禁用户失败", i);
        }
    }

    /**
     * 解封
     *转移到admin中
     * @param dqUserId
     * @return
     */
    @SaCheckPermission("admin-user")
    @ApiOperation(value = "解封")
    @GetMapping("/unseal/{dqUserId}")
    public AjaxResult unseal(@PathVariable Long dqUserId) {
        int i = iAdminDqUserService.unsealUserById(dqUserId);

        if (i > 0) {
            //解封
            StpUtil.untieDisable(dqUserId);
            return AjaxResult.success("解封成功", i);
        } else {
            return AjaxResult.success("解封失败", i);
        }
    }

    @SaCheckPermission("admin-update")
    @ApiOperation(value = "更新用户")
    @PostMapping("/update/{dqUserId}")
    public AjaxResult update(@PathVariable Long dqUserId, @RequestBody AdminDqUserVO adminDqUserVO) {
        //获取用户（后面可以从redis中获取）
        DqUser dqUser = iAdminDqUserService.selectDqUserById(dqUserId);
        //更新用户
        dqUser.setNickName(adminDqUserVO.getNickname());
        dqUser.setEmail(adminDqUserVO.getEmail());
        dqUser.setPhoneNumber(adminDqUserVO.getPhonenumber());
        dqUser.setSex(adminDqUserVO.getSex());
        dqUser.setAvatar(adminDqUserVO.getAvatar());
        //加密密码
        if (dqUser.getUserId() == StpUtil.getLoginId()){
            dqUser.setPassWord(SaSecureUtil.md5(SaSecureUtil.sha1(dqUser.getPassWord())));
        }
        dqUser.setSignature(adminDqUserVO.getSignature());
        dqUser.setStatus(adminDqUserVO.getStatus());
        //如果要改的这个用户没有管理员的权限
        if (!StpUtil.hasPermission(dqUserId,"admin*")){
            dqUser.setRole(adminDqUserVO.getRole());
        }else if (StpUtil.hasPermission("*")){
            dqUser.setRole(adminDqUserVO.getRole());
        }

        dqUser.setLoginIp(IpUtils.getHostIp());
        dqUser.setLoginDate(DateUtils.getNowDate());
        dqUser.setUpdateTime(DateUtils.getNowDate());

        //更新完成用户后要刷新redis缓存
        int i = iAdminDqUserService.updateDqUser(dqUser);
        if (i > 0) {
            return AjaxResult.success("更新成功", i);
        } else {
            return AjaxResult.error("更新失败", i);
        }
    }



}