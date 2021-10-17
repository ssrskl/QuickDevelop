package com.maoyan.quickdevelop.admin.controller.system.dqlike;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.github.pagehelper.PageInfo;
import com.maoyan.quickdevelop.common.core.AjaxResult;
import com.maoyan.quickdevelop.common.core.domain.DqUser;
import com.maoyan.quickdevelop.system.service.IDqGivelikeService;
import io.swagger.annotations.ApiOperation;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 猫颜
 * @date 2021年07月24日 下午3:52
 * 类的作用：TODO
 */
@RestController
@RequestMapping("/dqlike")
public class DqLikeController {

    @Autowired
    private IDqGivelikeService iDqGivelikeService;

    /***
     * @Author: 猫颜
     * @Description: 给文章点赞
     * @DateTime: 下午5:32 2021/7/24
     * @Params:
     * @param dqArticleId
     * @Return
     * @return com.maoyan.quickdevelop.common.core.AjaxResult
     */
    @SaCheckLogin
    @GetMapping("/like/{dqArticleId}")
    @ApiOperation(value = "给文章点赞")
    public AjaxResult like(@PathVariable @NonNull Long dqArticleId) {
        int i = iDqGivelikeService.giveALike(dqArticleId);
        return AjaxResult.success("点赞成功", i);
    }

    @SaCheckLogin
    @GetMapping("/unlike/{dqArticleId}")
    @ApiOperation(value = "给文章取消点赞")
    public AjaxResult unlike(@PathVariable @NonNull Long dqArticleId) {
        int i = iDqGivelikeService.unlike(dqArticleId);
        return AjaxResult.success("取消点赞成功", i);
    }

    /***
     * @Author: 猫颜
     * @Description: 当前用户对指定文章的点赞状态
     * @DateTime: 下午5:48 2021/7/24
     * @Params:
     * @param dqArticleId
     * @Return
     * @return com.maoyan.quickdevelop.common.core.AjaxResult
     */
    @SaCheckLogin
    @GetMapping("/status/{dqArticleId}")
    @ApiOperation(value = "当前用户对指定文章的点赞状态")
    public AjaxResult checkStatusForDqArticle(@PathVariable @NonNull Long dqArticleId) {
        int i = iDqGivelikeService.checkStatusForDqArticle(dqArticleId);
        return AjaxResult.success("查询成功", i);
    }

    @GetMapping("/getlikes/{dqArticleId}")
    @ApiOperation(value = "获取指定文章的点赞数量")
    public AjaxResult getLikeNumberInDqArticle(@PathVariable @NonNull Long dqArticleId) {
        int i = iDqGivelikeService.getLikeNumberInDqArticle(dqArticleId);
        return AjaxResult.success("查询成功", i);
    }

    /***
     * @Author: 猫颜
     * @Description: 获取点赞的所有用户
     * @DateTime: 上午8:41 2021/7/25
     * @Params:
     * @param dqArticleId
     * @Return
     * @return com.maoyan.quickdevelop.common.core.AjaxResult
     */
    @GetMapping("/getlikeusers/{dqArticleId}")
    @ApiOperation(value = "获取点赞的所有用户")
    public AjaxResult likedDqUserInfoInDqArticle(@RequestParam(defaultValue = "1", name = "pageNum") int pageNum,
                                                 @RequestParam(defaultValue = "10", name = "pageSize") int pageSize,
                                                 @PathVariable @NonNull Long dqArticleId) {
        List<DqUser> dqUsers = iDqGivelikeService.likedDqUserInfoInDqArticle(pageNum, pageSize, dqArticleId);
        return AjaxResult.success("查询成功", new PageInfo<>(dqUsers));
    }
}