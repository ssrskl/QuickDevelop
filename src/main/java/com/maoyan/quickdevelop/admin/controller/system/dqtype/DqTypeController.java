package com.maoyan.quickdevelop.admin.controller.system.dqtype;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.stp.StpUtil;
import com.github.pagehelper.PageInfo;
import com.maoyan.quickdevelop.admin.controller.system.BaseController;
import com.maoyan.quickdevelop.common.constant.HttpStatus;
import com.maoyan.quickdevelop.common.core.AjaxResult;
import com.maoyan.quickdevelop.common.core.domain.DqType;
import com.maoyan.quickdevelop.common.core.domain.postprocessor.DqArticlePostProcesser;
import com.maoyan.quickdevelop.common.core.domain.postprocessor.DqTypePostProcesser;
import com.maoyan.quickdevelop.system.service.IDqTypeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @author 猫颜
 * @date 2021/5/29 8:07
 * 类型控制类
 * TODO 类型控制类
 */
@RestController
@RequestMapping("/dqtype")
public class DqTypeController extends BaseController {
    @Autowired
    private IDqTypeService iTypeService;

    /**
     * 查询所有的类型
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询所有的类型")
    public AjaxResult list(@RequestParam(defaultValue = "1") int pageNum,
                           @RequestParam(defaultValue = "10") int pageSize,
                           DqType dqType) throws NoSuchFieldException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        List<DqType> dqTypes = iTypeService.selectAllDqTypes(pageNum, pageSize, dqType);
        if (dqTypes.isEmpty()) {
            return AjaxResult.error(HttpStatus.NOT_FOUND, "没有此类型");
        }
        PageInfo<DqType> pageInfo = new PageInfo<>(dqTypes);
        return AjaxResult.success("查询成功", pageInfo);
    }

//    /**
//     * 删除类型
//     *
//     * @param dqTypeId
//     * @return
//     */
//    @SaCheckRole("管理员")
//    @GetMapping("/remove/{dqTypeId}")
//    @ApiOperation(value = "根据ID删除类型")
//    public AjaxResult remove(@PathVariable Long dqTypeId) {
//        int i = iTypeService.deleteDqType(dqTypeId);
//        if (i > 0) {
//            return AjaxResult.success("删除成功", i);
//        } else {
//            return AjaxResult.success("删除失败", i);
//        }
//    }

    /**
     * 通过ID查询类型
     *
     * @param dqTypeId
     * @return
     */
    @GetMapping("/{dqTypeId}")
    @ApiOperation(value = "通过ID查询类型")
    public AjaxResult getInfo(@PathVariable Long dqTypeId) {
        //后面可以根据缓存中先查询
        DqType dqType = iTypeService.selectDqTypeById(dqTypeId);
        if (dqType == null) {
            return AjaxResult.error(HttpStatus.NOT_FOUND, "没有此类型");
        }
        return AjaxResult.success("查询成功", dqType);
    }

//    /**
//     * 添加类别
//     *
//     * @param dqTypeVO
//     * @return
//     */
//    @SaCheckRole("管理员")
//    @PostMapping("/add")
//    @ApiOperation(value = "添加类型")
//    public AjaxResult addType(@RequestBody DqTypeVO dqTypeVO) {
//        DqType dqType = new DqType();
//        dqType.setTypeName(dqTypeVO.getTypename());
//        dqType.setTypeImage(dqTypeVO.getTypeimage());
//        dqType.setIntroduce(dqTypeVO.getIntroduce());
//        dqType.setCreateManId(Long.parseLong(String.valueOf(StpUtil.getLoginId())));
//        dqType.setUpdateManId(Long.parseLong(String.valueOf(StpUtil.getLoginId())));
//        dqType.setCreateTime(DateUtils.getNowDate());
//        dqType.setUpdateTime(DateUtils.getNowDate());
//
//        int i = iTypeService.insertDqType(dqType);
//        if (i > 0) {
//            return AjaxResult.success("添加成功", i);
//        } else {
//            return AjaxResult.error("添加失败", i);
//        }
//    }

//    /**
//     * 修改类型
//     *
//     * @param dqTypeVO
//     * @return
//     */
//    //@SaCheckRole(value = {"管理员","普通用户"},mode = SaMode.OR)
//    @SaCheckRole("管理员")
//    @PostMapping("/update")
//    @ApiOperation(value = "修改类型")
//    public AjaxResult update(@RequestBody DqTypeVO dqTypeVO) {
//        DqType dqType = iTypeService.selectDqTypeByName(dqTypeVO.getTypename());
//        dqType.setTypeName(dqTypeVO.getTypename());
//        dqType.setTypeImage(dqTypeVO.getTypeimage());
//        dqType.setIntroduce(dqTypeVO.getIntroduce());
//        dqType.setUpdateManId(Long.parseLong(String.valueOf(StpUtil.getLoginId())));
//        dqType.setUpdateTime(DateUtils.getNowDate());
//
//        int i = iTypeService.updateDqTypeById(dqType);
//
//        if (i > 0) {
//            return AjaxResult.success("修改成功", i);
//        } else {
//            return AjaxResult.error("修改失败", i);
//        }
//    }

//    @SaCheckRole("管理员")
//    @GetMapping("/ban/{dqTypeId}")
//    @ApiOperation(value = "封禁类型")
//    public AjaxResult ban(@PathVariable Long dqTypeId) {
//        DqType dqType = iTypeService.selectDqTypeById(dqTypeId);
//        dqType.setStatus("1");
//        int i = iTypeService.updateDqTypeById(dqTypeId, dqType);
//
//        if (i > 0) {
//            return AjaxResult.success("封禁成功", i);
//        } else {
//            return AjaxResult.error("封禁失败", i);
//        }
//
//    }
//
//    @SaCheckRole("管理员")
//    @GetMapping("/unseal/{dqTypeId}")
//    @ApiOperation(value = "解封类型")
//    public AjaxResult unseal(@PathVariable Long dqTypeId) {
//        DqType dqType = iTypeService.selectDqTypeById(dqTypeId);
//        dqType.setStatus("0");
//        int i = iTypeService.updateDqTypeById(dqTypeId, dqType);
//
//        if (i > 0) {
//            return AjaxResult.success("解封成功", i);
//        } else {
//            return AjaxResult.error("解封失败", i);
//        }
//
//    }

    /**
     * ========================强化查询===============================
     **/
    @GetMapping("/superlist")
    @ApiOperation(value = "通用查询所有的文章")
    public AjaxResult selectDqTypePostProcesser(@RequestParam(defaultValue = "1", name = "pageNum") int pageNum,
                                                @RequestParam(defaultValue = "10", name = "pageSize") int pageSize,
                                                DqTypePostProcesser dqTypePostProcesser) {
        List<DqTypePostProcesser> dqTypePostProcessers = iTypeService.selectDqTypePostProcessers(pageNum, pageSize, dqTypePostProcesser);
        return AjaxResult.success("查询成功", new PageInfo<>(dqTypePostProcessers));
    }
}
