package com.maoyan.quickdevelop.admin.controller.system;

import com.maoyan.quickdevelop.common.core.AjaxResult;
import com.maoyan.quickdevelop.system.service.IDqNumberService;
import io.swagger.annotations.ApiOperation;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 猫颜
 * @date 2021年07月24日 上午9:04
 * 类的作用：TODO DqClass数量查询工具类
 */
@RestController
@RequestMapping("/number")
public class DqClassNumController {
    @Autowired
    private IDqNumberService iDqNumberService;

    @GetMapping("/allarticles")
    @ApiOperation(value = "查询所有文章的数量")
    public AjaxResult allDqArticleNumber(){
        int i = iDqNumberService.allDqArticleNumber();
        return AjaxResult.success("查询成功", i);
    }

    @GetMapping("/dqarticle/{dqArticleId}")
    @ApiOperation(value = "查询指定文章下所有评论和回复的数量")
    public AjaxResult allCommentNumberInDqArticleById(@PathVariable @NonNull Long dqArticleId){
        int i = iDqNumberService.allCommentNumberInDqArticleById(dqArticleId);
        return AjaxResult.success("查询成功", i);
    }

    @GetMapping("/dqcomment/{rootId}")
    @ApiOperation(value = "查询在指定根评论下的回复的数量")
    public AjaxResult allSonCommentNumInDqCommentByRootId(@PathVariable @NonNull Long rootId){
        int i = iDqNumberService.allSonCommentNumInDqCommentByRootId(rootId);
        return AjaxResult.success("查询成功", i);
    }

    @GetMapping("/alldqtypes")
    @ApiOperation(value = "获取所有类型的数量")
    public AjaxResult allTypeNumber(){
        int i = iDqNumberService.allTypeNumber();
        return AjaxResult.success("查询成功", i);
    }

    @GetMapping("/alldqusers")
    @ApiOperation(value = "获取所有用户的数量")
    public AjaxResult allUsersNum(){
        int i = iDqNumberService.allUsersNum();
        return AjaxResult.success("查询成功", i);
    }

    @GetMapping("/dqarticlenumbyuserid/{dqUserId}")
    @ApiOperation(value = "根据作者的ID查询他的文章数量")
    public AjaxResult dqArticleNumByAuthorId(@PathVariable Long dqUserId){
        int i = iDqNumberService.dqArticleNumByAuthorId(dqUserId);
        return AjaxResult.success("查询成功", i);
    }

    @GetMapping("/dqcommentnumbyuserid/{dqUserId}")
    @ApiOperation(value = "根据作者的ID查询他的评论数量")
    public AjaxResult dqCommentNumByAuthorId(@PathVariable Long dqUserId){
        int i = iDqNumberService.dqCommentNumByAuthorId(dqUserId);
        return AjaxResult.success("查询成功", i);
    }


    @GetMapping("/dqarticlenumbynowuser")
    @ApiOperation(value = "获取当前用户的文章数量")
    public AjaxResult dqArticleNumByNowDqUser(){
        int i = iDqNumberService.dqArticleNumByNowDqUser();
        return AjaxResult.success("查询成功", i);
    }

    @GetMapping("/dqcommentnumbynowuser")
    @ApiOperation(value = "获取当前用户的评论数量")
    public AjaxResult dqCommentNumByNowDqUser(){
        int i = iDqNumberService.dqCommentNumByNowDqUser();
        return AjaxResult.success("查询成功", i);
    }



}