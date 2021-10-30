package com.maoyan.quickdevelop.admin.controller.system;

import cn.dev33.satoken.stp.SaTokenInfo;
import com.maoyan.quickdevelop.common.annotation.Log;
import com.maoyan.quickdevelop.common.core.AjaxResult;
import com.maoyan.quickdevelop.common.core.domain.DqUser;
import com.maoyan.quickdevelop.common.enums.BusinessType;
import com.maoyan.quickdevelop.system.domain.vo.LoginVO;
import com.maoyan.quickdevelop.system.service.IDqLoginService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 猫颜
 * @date 2021/5/28 11:21
 * 用户登陆
 * TODO 用户登陆控制类
 */
@RestController
@RequestMapping("/login")
public class DqLoginController {

    @Autowired
    IDqLoginService iDqLoginService;


    @ApiOperation(value = "用户登陆")
    @PostMapping("")
    public AjaxResult login(@Validated @RequestBody LoginVO loginVO) {
        SaTokenInfo saTokenInfo = iDqLoginService.dqUserLogin(loginVO);
        return AjaxResult.success("登陆成功", saTokenInfo);
    }
    /**
     * Gitee登陆
     * @param code gitee提供的code参数
     * @return
     */
    @GetMapping("/gitee")
    public AjaxResult giteeLogin(String code){
        SaTokenInfo saTokenInfo = iDqLoginService.dqUserGiteeLogin(code);
        return AjaxResult.success(saTokenInfo);
    }
}
