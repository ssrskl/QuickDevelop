package com.maoyan.quickdevelop.admin.controller.monitor;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaCheckRole;
import com.maoyan.quickdevelop.common.core.AjaxResult;
import com.maoyan.quickdevelop.frameworks.web.domain.Server;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 猫颜
 * @date 2021/5/28 11:00
 * 服务器监控
 * TODO 服务器监控类
 */
@Api("服务器监控")
@RestController
@RequestMapping("/monitor")
public class ServerController {

    private static final Logger log = LoggerFactory.getLogger(ServerController.class);

    @SaCheckPermission(value = {"admin*"})
    @GetMapping("/server")
    @ApiOperation(value = "获取服务器数据")
    public AjaxResult getInfo() throws Exception {
        Server server = new Server();
        server.copyTo();
        log.info("服务器数据:"+server);
        return AjaxResult.success(server);
    }
}
