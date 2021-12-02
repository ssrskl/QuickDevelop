package com.maoyan.quickdevelop.admin.controller.system.dqcollection;

import com.github.pagehelper.PageInfo;
import com.maoyan.quickdevelop.admin.controller.system.BaseController;
import com.maoyan.quickdevelop.common.constant.HttpStatus;
import com.maoyan.quickdevelop.common.core.AjaxResult;
import com.maoyan.quickdevelop.common.core.domain.postprocessor.DqCollectionPostProcesser;
import com.maoyan.quickdevelop.system.service.IDqCollectionService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dqcollection")
public class DqCollectionController extends BaseController {
  @Autowired
  private IDqCollectionService iDqCollectionService;

  @GetMapping("/list")
  @ApiOperation(value = "通用查询所有的收藏")
  public AjaxResult listArticle(@RequestParam(defaultValue = "1", name = "pageNum") int pageNum,
                                @RequestParam(defaultValue = "10", name = "pageSize") int pageSize,
                                DqCollectionPostProcesser dqCollectionPostProcesser) {
    List<DqCollectionPostProcesser> dqCollectionPostProcessers = iDqCollectionService.commonSelectDqCollectionPostProcesser(pageNum, pageSize, dqCollectionPostProcesser);
    return AjaxResult.success("查询成功", new PageInfo<>(dqCollectionPostProcessers));
  }

  @ApiOperation(value = "根据ID收藏文章")
  @GetMapping(value = "/collect")
  public AjaxResult collectDqArticleById(Long dqArticleId) {
    int i = iDqCollectionService.collectDqArticleById(dqArticleId);
    return AjaxResult.success("收藏成功", HttpStatus.SUCCESS);
  }

  @ApiOperation(value = "根据ID取消收藏文章")
  @GetMapping(value = "/cancelcollect")
  public AjaxResult cancelCollectDqArticleById(Long dqArticleId) {
    int i = iDqCollectionService.cancelCollectDqArticleById(dqArticleId);
    return AjaxResult.success("取消收藏成功", HttpStatus.SUCCESS);
  }
}
