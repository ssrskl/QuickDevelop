package com.maoyan.quickdevelop.admin.controller.system.dqoperlog;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.github.pagehelper.PageInfo;
import com.maoyan.quickdevelop.common.core.AjaxResult;
import com.maoyan.quickdevelop.common.core.domain.DqOperLog;
import com.maoyan.quickdevelop.system.service.IDqOperLogService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 猫颜
 * @date 2021年07月28日 上午8:40
 */
@RestController
@RequestMapping("/dqoperlog")
public class DqOperLogController {
    @Autowired
    private IDqOperLogService iDqOperLogService;

    private PageInfo<DqOperLog> pageInfo;

    @GetMapping("/list")
    @SaCheckPermission(value = "admin-log")
    @ApiOperation(value = "通用查询所有的操作日志")
    public AjaxResult selectDqOperLogs(@RequestParam(defaultValue = "1", name = "pageNum") int pageNum,
                                  @RequestParam(defaultValue = "10", name = "pageSize") int pageSize,
                                  DqOperLog dqOperLog){
        List<DqOperLog> dqOperLogs = iDqOperLogService.selectDqOperLogs(pageNum, pageSize, dqOperLog);
        return AjaxResult.success("查询成功", new PageInfo<>(dqOperLogs));
    }
}