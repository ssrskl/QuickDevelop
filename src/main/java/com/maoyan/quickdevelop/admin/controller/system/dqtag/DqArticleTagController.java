package com.maoyan.quickdevelop.admin.controller.system.dqtag;

import com.github.pagehelper.PageInfo;
import com.maoyan.quickdevelop.admin.controller.system.BaseController;
import com.maoyan.quickdevelop.common.core.AjaxResult;
import com.maoyan.quickdevelop.common.core.domain.DqArticleTag;
import com.maoyan.quickdevelop.system.service.IDqArticleTagService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 猫颜
 * @date 2021年07月28日 下午5:20
 */
@RestController
@RequestMapping("/dqarticletag")
public class DqArticleTagController extends BaseController {

    @Autowired
    private IDqArticleTagService iDqArticleTagService;

    @ApiOperation(value = "查询所有tag")
    @GetMapping("/list")
    public AjaxResult selectDqArticleTags(@RequestParam(defaultValue = "1") int pageNum,
                                 @RequestParam(defaultValue = "10") int pageSize,
                                 DqArticleTag dqArticleTag){
        //System.out.println("ID为："+dqTag.getTagId());
        List<DqArticleTag> dqArticleTags = iDqArticleTagService.selectDqArticleTags(pageNum, pageSize, dqArticleTag);
        return AjaxResult.success("查询成功", new PageInfo<>(dqArticleTags));
    }




}