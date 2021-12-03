package com.maoyan.quickdevelop.admin.controller.system.dqcomment;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.stp.StpUtil;
import com.github.pagehelper.PageInfo;
import com.maoyan.quickdevelop.admin.controller.system.BaseController;
import com.maoyan.quickdevelop.common.annotation.Log;
import com.maoyan.quickdevelop.common.core.AjaxResult;
import com.maoyan.quickdevelop.common.core.domain.DqComment;
import com.maoyan.quickdevelop.common.core.domain.postprocessor.DqCommentPostProcesser;
import com.maoyan.quickdevelop.common.enums.BusinessType;
import com.maoyan.quickdevelop.system.domain.DqCommentVO;
import com.maoyan.quickdevelop.system.service.IDqArticleService;
import com.maoyan.quickdevelop.system.service.IDqCommentService;
import com.maoyan.quickdevelop.system.service.IDqUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @author 猫颜
 * @date 2021/5/31 22:01
 * 评论控制类
 * TODO 评论控制类
 */
@Validated
@RestController
@RequestMapping("/dqcomment")
public class DqCommentController extends BaseController {

  @Autowired
  private IDqCommentService idqCommentService;
  @Autowired
  private IDqArticleService idqArticleService;
  @Autowired
  private IDqUserService idqUserService;
  private PageInfo<DqComment> pageInfo;

  /**
   * 通用评论查询
   *
   * @param pageNum
   * @param pageSize
   * @param dqComment
   * @return com.maoyan.quickdevelop.common.core.AjaxResult
   * @author 猫颜
   * @date 上午8:41
   */
  @GetMapping("/list")
  @ApiOperation(value = "评论通用查询")
  public AjaxResult listComment(@RequestParam(defaultValue = "1", name = "pageNum") int pageNum,
                                @RequestParam(defaultValue = "10", name = "pageSize") int pageSize,
                                DqComment dqComment) {
    List<DqCommentPostProcesser> dqCommentPostProcessers = idqCommentService.commonSelectDqCommentPostProcesser(pageNum, pageSize, dqComment);
    return AjaxResult.success("查询成功", new PageInfo<>(dqCommentPostProcessers));
  }

//  /**
//   * 通过ID查询评论
//   *
//   * @param dqCommentId
//   * @return
//   */
//  @GetMapping("/{dqCommentId}")
//  @ApiOperation(value = "通过ID查询评论")
//  public AjaxResult getInfo(@PathVariable Long dqCommentId) {
//    //后面可以根据缓存中先查询
//    DqComment dqComment = idqCommentService.selectDqCommentById(dqCommentId);
//    return AjaxResult.success("查询成功", dqComment);
//  }
//
//  /**
//   * 根据文章ID查询所有根评论
//   *
//   * @param pageNum
//   * @param pageSize
//   * @param articleId
//   * @return
//   */
//  @GetMapping("/dqarticle/{articleId}")
//  @ApiOperation(value = "根据文章ID查询所有根评论")
//  public AjaxResult getInfoByArticleId(@RequestParam(defaultValue = "1") int pageNum,
//                                       @RequestParam(defaultValue = "10") int pageSize,
//                                       @PathVariable Long articleId) {
//    List<DqComment> dqComments = idqCommentService.selectDqCommentByDqArticleId(pageNum, pageSize, articleId);
//    pageInfo = new PageInfo<>(dqComments);
//    return AjaxResult.success("查询成功", pageInfo);
//  }

//  /**
//   * 根据根评论查询回复（根据id查询根评论下的所有回复）
//   *
//   * @param pageNum
//   * @param pageSize
//   * @param rootId
//   * @return com.maoyan.quickdevelop.common.core.AjaxResult
//   * @author 猫颜
//   * @date 2021/7/24 上午7:55
//   */
//  @GetMapping("/dqroot/{rootId}")
//  @ApiOperation(value = "根据根评论查询回复（根据id查询根评论下的所有回复）")
//  public AjaxResult getDqCommentsByRootId(@RequestParam(defaultValue = "1") int pageNum,
//                                          @RequestParam(defaultValue = "100") int pageSize,
//                                          @PathVariable Long rootId) {
//    List<DqComment> dqComments = idqCommentService.selectDqCommentsByRootId(pageNum, pageSize, rootId);
//    pageInfo = new PageInfo<>(dqComments);
//    return AjaxResult.success("查询成功", pageInfo);
//  }

//  /**
//   * 查看指定用户发表的所有评论
//   *
//   * @param userId
//   * @return
//   */
//  @GetMapping("/dquser/{userId}")
//  @ApiOperation(value = "根据发表评论的用户的ID来查")
//  public AjaxResult getInfoByUserId(@RequestParam(defaultValue = "1") int pageNum,
//                                    @RequestParam(defaultValue = "10") int pageSize,
//                                    @PathVariable Long userId) {
//    List<DqComment> dqComments = idqCommentService.selectDqCommentByDqUserId(pageNum, pageSize, userId);
//    pageInfo = new PageInfo<>(dqComments);
//    return AjaxResult.success("查询成功", pageInfo);
//  }


//  /**
//   * @return com.maoyan.quickdevelop.common.core.AjaxResult
//   * @ApiOperation(value = "查找所有发给当前登陆用户的评论")
//   * @author 猫颜
//   * @date 下午7:42
//   */
//  @SaCheckLogin
//  @SaCheckPermission(value = "user-query")
//  @GetMapping("/tome")
//  @ApiOperation(value = "查找所有发给当前登陆用户的评论")
//  public AjaxResult getCommentToMe(@RequestParam(defaultValue = "1") int pageNum,
//                                   @RequestParam(defaultValue = "10") int pageSize) {
//    List<DqComment> dqComments = idqCommentService.selectDqCommentToMe(pageNum, pageSize);
//    pageInfo = new PageInfo<>(dqComments);
//    return AjaxResult.success("查询成功", pageInfo);
//  }

  /**
   * 添加评论
   *
   * @param dqCommentVO
   * @return
   */
  @Log(title = "发表评论或回复", businessType = BusinessType.INSERT)
  @SaCheckLogin
  @SaCheckPermission(value = "user-add")
  @PostMapping("/add")
  @ApiOperation(value = "发表评论或回复")
  public AjaxResult addType(@Validated @RequestBody DqCommentVO dqCommentVO) {
    int i = idqCommentService.publishDqComment(dqCommentVO);
    return AjaxResult.success("发表评论成功", i);
  }

  /**
   * 删除评论(直接删除的话，后面还会有很多的评论等等，所以采用逻辑删除)
   *
   * @param dqCommentId
   * @return
   */
  @Log(title = "删除评论或回复", businessType = BusinessType.DELETE)
  @SaCheckLogin
  @SaCheckPermission(value = "user-delete")
  @GetMapping("/remove")
  @ApiOperation(value = "根据ID删除评论")
  public AjaxResult remove(@NotNull Long dqCommentId) {
    int i = idqCommentService.deleteDqCommentById(dqCommentId);
    return AjaxResult.success("删除评论成功", i);
  }
  /**
   * 评论无法修改（其实是我懒得添加的）
   */

  //管理员会用
//    /**
//     * 查询所有的评论
//     *
//     * @param pageNum
//     * @param pageSize
//     * @return
//     */
//    @GetMapping("/list")
//    @ApiOperation(value = "查询所有的评论")
//    public AjaxResult list(@RequestParam(defaultValue = "1") int pageNum,
//                           @RequestParam(defaultValue = "10") int pageSize) {
//        List<DqComment> dqComments = idqCommentService.selectAllDqComments(pageNum, pageSize);
//        Assert.notNull(dqComments, "没有类型");
//        PageInfo<DqComment> pageInfo = new PageInfo<>(dqComments);
//
//        return AjaxResult.success("查询成功", pageInfo);
//    }
}
