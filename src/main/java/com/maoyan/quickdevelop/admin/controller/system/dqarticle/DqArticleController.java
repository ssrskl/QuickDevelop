package com.maoyan.quickdevelop.admin.controller.system.dqarticle;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.stp.StpUtil;
import com.github.pagehelper.PageInfo;
import com.maoyan.quickdevelop.admin.controller.system.BaseController;
import com.maoyan.quickdevelop.common.utils.StringUtils;
import com.maoyan.quickdevelop.common.annotation.Log;
import com.maoyan.quickdevelop.common.constant.HttpStatus;
import com.maoyan.quickdevelop.common.core.AjaxResult;
import com.maoyan.quickdevelop.common.core.domain.DqArticle;
import com.maoyan.quickdevelop.common.core.domain.postprocessor.DqArticlePostProcesser;
import com.maoyan.quickdevelop.common.enums.BusinessType;
import com.maoyan.quickdevelop.common.utils.DateUtils;
import com.maoyan.quickdevelop.system.domain.DqArticleVO;
import com.maoyan.quickdevelop.system.service.IDqArticleService;
import com.maoyan.quickdevelop.system.service.IDqUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 猫颜
 * @date 2021/5/29 8:49
 * TODO 文章管理类
 */
@RestController
@RequestMapping("/dqarticle")
public class DqArticleController extends BaseController {
  @Autowired
  private IDqArticleService iArticleService;
  @Autowired
  private IDqUserService iUserService;


  /**
   * TODO 文章通用查询
   *
   * @param pageNum   第几页
   * @param pageSize  每页几个
   * @param dqArticle 查询信息
   * @return com.maoyan.quickdevelop.common.core.AjaxResult
   * @author 猫颜
   * @date 上午9:58
   */
  @GetMapping("/list")
  @ApiOperation(value = "通用查询所有的文章")
  public AjaxResult listArticle(@RequestParam(defaultValue = "1", name = "pageNum") int pageNum,
                                @RequestParam(defaultValue = "10", name = "pageSize") int pageSize,
                                DqArticle dqArticle) {
    List<DqArticle> dqArticles = iArticleService.selectDqArticle(pageNum, pageSize, dqArticle);
    return AjaxResult.success("查询成功", new PageInfo<>(dqArticles));

  }
  /**
   * 根据文章ID查询文章
   *
   * @param dqArticleId
   * @return com.maoyan.quickdevelop.common.core.AjaxResult
   * @author 猫颜
   * @date 上午8:55
   */
  @GetMapping("/{dqArticleId}")
  @ApiOperation(value = "根据文章ID查询文章")
  public AjaxResult getDqArticleById(@PathVariable Long dqArticleId) {
    DqArticle dqArticle = iArticleService.selectDqArticleById(dqArticleId);
    return AjaxResult.success("查询成功", dqArticle);
  }

  /**
   * 根据标题查询文章
   *
   * @param articleTitle
   * @return
   */
  @GetMapping("/title")
  @ApiOperation(value = "根据标题查询文章")
  public AjaxResult getByTitle(String articleTitle) {
    DqArticle dqArticle = iArticleService.selectDqArticleByTitle(articleTitle);
    //Assert.notNull(dqArticle, "没有此标题");
    return AjaxResult.success("查询成功", dqArticle);
  }

  /**
   * 根据类型ID查询文章
   *
   * @param pageNum
   * @param pageSize
   * @param typeId
   * @return
   */
  @GetMapping("/type/{typeId}")
  @ApiOperation(value = "根据类型的Id查询文章")
  public AjaxResult getByTypeId(@RequestParam(defaultValue = "1") int pageNum,
                                @RequestParam(defaultValue = "10") int pageSize,
                                @PathVariable Long typeId) {
    List<DqArticle> dqArticles = iArticleService.selectDqArticlesByTypeId(pageNum, pageSize, typeId);
    return AjaxResult.success("查询成功", new PageInfo<>(dqArticles));
  }

  /**
   * 根据作者Id查询文章
   *
   * @param pageNum
   * @param pageSize
   * @param authorId
   * @return
   */
  @GetMapping("/author/{authorId}")
  @ApiOperation(value = "根据作者的ID查询文章")
  public AjaxResult getByAuthorId(@RequestParam(defaultValue = "1") int pageNum,
                                  @RequestParam(defaultValue = "10") int pageSize,
                                  @PathVariable Long authorId) {
    List<DqArticle> dqArticles = iArticleService.selectDqArticlesByAuthorId(pageNum, pageSize, authorId);
    if (StringUtils.isNull(dqArticles) || dqArticles.size() == 0) {
      return AjaxResult.error(HttpStatus.NOT_FOUND, "此用户没有文章");
    }
    return AjaxResult.success("查询成功", new PageInfo<>(dqArticles));
  }

  /**
   * 通过作者昵称查询(昵称重复问题，我暂时懒得解决，对，我懒)(此处已经换成了根据作者的用户名，不再是昵称了)
   *
   * @param pageNum
   * @param pageSize
   * @param authorUsername
   * @return
   */
  @GetMapping("/author")
  @ApiOperation(value = "根据作者的用户名查询文章")
  public AjaxResult getByAuthorUserName(@RequestParam(defaultValue = "1") int pageNum,
                                        @RequestParam(defaultValue = "10") int pageSize,
                                        String authorUsername) {
    List<DqArticle> dqArticles = iArticleService.selectDqArticlesByAuthorUsername(pageNum, pageSize, authorUsername);
    if (StringUtils.isNull(dqArticles) || dqArticles.size() == 0) {
      return AjaxResult.error(HttpStatus.NOT_FOUND, "此用户没有文章");
    }
    return AjaxResult.success("查询成功", new PageInfo<>(dqArticles));
  }


  /**
   * 根据Id删除文章
   *
   * @param articleId
   * @return
   */
  @SaCheckLogin
  @SaCheckPermission(value = "user-delete")
  @GetMapping("/delete/{articleId}")
  @ApiOperation(value = "根据ID删除文章")
  public AjaxResult deleteByArticleId(@PathVariable Long articleId) {
//        文章作者ID
    int i = iArticleService.deleteDqArticleById(articleId);
    return AjaxResult.success("删除成功", i);
  }

  /***
   * @Author: 猫颜
   * @Description: 发表文章
   * @DateTime: 上午11:37 2021/7/30
   * @Params:
   * @param dqArticleVO
   * @Return
   * @return com.maoyan.quickdevelop.common.core.AjaxResult
   */
  @SaCheckLogin
  @SaCheckPermission(value = "user-add")
  @PostMapping("/add")
  @Log(title = "文章操作", businessType = BusinessType.INSERT)
  @ApiOperation(value = "发表文章")
  public AjaxResult addArticle(@RequestBody DqArticleVO dqArticleVO) {
    DqArticle dqArticle = new DqArticle();
    dqArticle.setArticleTitle(dqArticleVO.getArticleTitle());
    dqArticle.setArticleContent(dqArticleVO.getArticleContent());
    dqArticle.setArticleImage(dqArticleVO.getArticleImage());
    dqArticle.setAuthorId(StpUtil.getLoginIdAsLong());
    dqArticle.setStatus("0");
    dqArticle.setCreateTime(DateUtils.getNowDate());
    dqArticle.setUpdateTime(DateUtils.getNowDate());
    int i = iArticleService.insertDqArticle(dqArticle);
    return AjaxResult.success("添加成功", i);
  }

  /**
   * 更新文章
   *
   * @param dqArticle
   * @return com.maoyan.quickdevelop.common.core.AjaxResult
   * @author 猫颜
   * @date 上午10:23
   */
  @SaCheckLogin
  @SaCheckPermission(value = "user-update")
  @ApiOperation(value = "更新文章")
  @Log(title = "文章操作", businessType = BusinessType.UPDATE)
  @PostMapping("/update")
  public AjaxResult updateArticle(@RequestBody DqArticle dqArticle) {
    int i = iArticleService.updateDqArticle(dqArticle);
    return AjaxResult.success("更新成功", i);
  }

  /**
   * ========================强化查询===============================
   **/
  @GetMapping("/superlist")
  @ApiOperation(value = "通用查询所有的文章")
  public AjaxResult selectDqArticlePostProcesser(@RequestParam(defaultValue = "1", name = "pageNum") int pageNum,
                                                 @RequestParam(defaultValue = "10", name = "pageSize") int pageSize,
                                                 DqArticlePostProcesser dqArticlePostProcesser) {
    //参数接到了
//        System.out.println("页数为："+pageNum);
//        System.out.println("每页为："+pageSize);
    List<DqArticlePostProcesser> dqArticlePostProcessers = iArticleService.selectDqArticlePostProcessers(pageNum, pageSize, dqArticlePostProcesser);
    return AjaxResult.success("查询成功", new PageInfo<>(dqArticlePostProcessers));
  }

}