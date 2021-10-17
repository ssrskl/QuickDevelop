package com.maoyan.quickdevelop.admin.controller.system.admin;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.github.pagehelper.PageInfo;
import com.maoyan.quickdevelop.common.utils.StringUtils;
import com.maoyan.quickdevelop.common.constant.HttpStatus;
import com.maoyan.quickdevelop.common.core.AjaxResult;
import com.maoyan.quickdevelop.common.core.domain.DqArticle;
import com.maoyan.quickdevelop.system.service.IDqUserService;
import com.maoyan.quickdevelop.system.service.admin.IAdminDqArticleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @author 猫颜
 * @date 2021/5/29 8:49
 * TODO 文章管理类
 */
@RestController
@RequestMapping("/admin/dqarticle")
public class AdminDqArticleController {

    @Autowired
    private IAdminDqArticleService iAdminDqArticleService;

    @Autowired
    private IDqUserService iUserService;

    private PageInfo<DqArticle> pageInfo;


    /**
     * TODO 文章通用查询
     * @author 猫颜
     * @date  上午9:58
     * @param pageNum 第几页
     * @param pageSize 每页几个
     * @param dqArticle 查询信息
     * @return com.maoyan.quickdevelop.common.core.AjaxResult
     */
    @GetMapping("/list")
    @ApiOperation(value = "通用查询所有的文章")
    public AjaxResult listArticle(@RequestParam(defaultValue = "1") int pageNum,
                                  @RequestParam(defaultValue = "10") int pageSize,
                                  DqArticle dqArticle) throws NoSuchFieldException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        List<DqArticle> dqArticles = iAdminDqArticleService.selectAllDqArticle(pageNum, pageSize, dqArticle);
        if (dqArticles.isEmpty()){
            return AjaxResult.error(HttpStatus.NOT_FOUND, "没有文章");
        }
        return AjaxResult.success("查询成功",new PageInfo<>(dqArticles));

    }
//    /**
//     * 查询所有的文章
//     *
//     * @param pageNum
//     * @param pageSize
//     * @return
//     */
//    @GetMapping("/list")
//    @ApiOperation(value = "查询所有的文章")
//    public AjaxResult list(@RequestParam(defaultValue = "1") int pageNum,
//                           @RequestParam(defaultValue = "10") int pageSize,
//                           DqArticle dqArticle) throws NoSuchFieldException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
//        List<DqArticle> dqArticles = iAdminDqArticleService.selectAllDqArticle(pageNum, pageSize,dqArticle);
//        if (dqArticles.isEmpty()) {
//            return AjaxResult.error(HttpStatus.NOT_FOUND, "没有文章");
//        }
//        //Assert.notNull(dqArticles, "没有文章");
//        pageInfo = new PageInfo<>(dqArticles);
//        return AjaxResult.success("查询成功", pageInfo);
//    }

    /**
     * 根据文章ID查询文章
     * @author 猫颜
     * @date  上午8:55
     * @param dqArticleId 
     * @return com.maoyan.quickdevelop.common.core.AjaxResult
     */
    @GetMapping("/{dqArticleId}")
    @ApiOperation(value = "根据文章ID查询文章")
    public AjaxResult getDqArticleById(@PathVariable Long dqArticleId){
        DqArticle dqArticle = iAdminDqArticleService.selectDqArticleById(dqArticleId);
        if (dqArticle == null){
            return AjaxResult.error(HttpStatus.NOT_FOUND, "没有此文章");
        }
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
    public AjaxResult getByTitle(@RequestParam String articleTitle) {
        DqArticle dqArticle = iAdminDqArticleService.selectDqArticleByTitle(articleTitle);
        if (StringUtils.isNull(dqArticle)) {
            return AjaxResult.error(HttpStatus.NOT_FOUND, "未查询到此文章");
        }
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
    @GetMapping("/list/{typeId}")
    @ApiOperation(value = "根据类型的Id查询文章")
    public AjaxResult getByTypeId(@RequestParam(defaultValue = "1") int pageNum,
                                  @RequestParam(defaultValue = "10") int pageSize,
                                  @PathVariable Long typeId) {
        List<DqArticle> dqArticles = iAdminDqArticleService.selectDqArticlesByTypeId(pageNum, pageSize, typeId);

        Assert.notNull(dqArticles, "没有此类型");
        return AjaxResult.success("查询成功", dqArticles);
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
        List<DqArticle> dqArticles = iAdminDqArticleService.selectDqArticlesByAuthorId(pageNum, pageSize, authorId);
        if (StringUtils.isNull(dqArticles) || dqArticles.size() == 0) {
            return AjaxResult.error(HttpStatus.NOT_FOUND, "此用户没有文章");
        }
        return AjaxResult.success("查询成功", dqArticles);
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
    @ApiOperation(value = "根据作者的昵称查询文章（有待优化）")
    public AjaxResult getByAuthorNickName(@RequestParam(defaultValue = "1") int pageNum,
                                          @RequestParam(defaultValue = "10") int pageSize,
                                          @RequestParam String authorUsername) {
        List<DqArticle> dqArticles = iAdminDqArticleService.selectDqArticlesByAuthorUsername(pageNum, pageSize, authorUsername);
        if (StringUtils.isNull(dqArticles) || dqArticles.size() == 0) {
            return AjaxResult.error(HttpStatus.NOT_FOUND, "此用户没有文章");
        }
        return AjaxResult.success("查询成功", dqArticles);
    }


    /**
     * 根据Id删除文章
     *
     * @param articleId
     * @return
     */
    @SaCheckLogin
    @GetMapping("/delete/{articleId}")
    @ApiOperation(value = "根据ID删除文章")
    public AjaxResult deleteByArticleId(@RequestParam Long articleId) {
        DqArticle dqArticle = iAdminDqArticleService.selectDqArticleById(articleId);
        Long autorId = dqArticle.getAuthorId();
        Assert.isTrue(StpUtil.hasRole("管理员") || StpUtil.getLoginId() == autorId, "不能删除他人的文章");
        int i = iAdminDqArticleService.deleteDqArticleById(articleId);
        if (i > 0) {
            return AjaxResult.success("删除成功", i);
        } else {
            return AjaxResult.error("删除失败", i);
        }
    }

    /**
     * 发表文章
     *
     * @param dqArticleVO
     * @return
     */
//    @SaCheckLogin
//    @PostMapping("/add")
//    @ApiOperation(value = "发表文章")
//    public AjaxResult addArticle(@RequestBody DqArticleVO dqArticleVO) {
//        DqArticle dqArticle = new DqArticle();
//        dqArticle.setArticleTitle(dqArticleVO.getArticletitle());
//        dqArticle.setArticleContent(dqArticleVO.getArticlecontent());
//        dqArticle.setArticleImage(dqArticleVO.getArticleimage());
//        dqArticle.setTypeId(dqArticleVO.getTypeId());
//        dqArticle.setAutorId(Long.parseLong(String.valueOf(StpUtil.getLoginId())));
//        if (StringUtils.isNotEmpty(dqArticleVO.getAuthornickname())) {
//            dqArticle.setAuthorNickname(dqArticleVO.getAuthornickname());
//        } else {
//            DqUser dqUser = iUserService.selectDqUserById(Long.parseLong(String.valueOf(StpUtil.getLoginId())));
//            dqArticle.setAuthorNickname(dqUser.getNickName());
//        }
//        dqArticle.setStatus("0");
//        dqArticle.setCreateTime(DateUtils.getNowDate());
//        dqArticle.setUpdateTime(DateUtils.getNowDate());
//
//
//        int i = iAdminDqArticleService.insertDqArticle(dqArticle);
//        if (i > 0) {
//            return AjaxResult.success("添加成功", i);
//        } else {
//            return AjaxResult.error("添加失败", i);
//        }
//    }

    /**
     * 更新文章
     *
     * @param articleId
     * @param dqArticleVO
     * @return
     */
//    @SaCheckLogin
//    @ApiOperation(value = "更新文章")
//    @PostMapping("/update/{articleId}")
//    public AjaxResult updateArticle(@PathVariable Long articleId,
//                                    @RequestBody DqArticleVO dqArticleVO) {
//        DqArticle dqArticle1 = iAdminDqArticleService.selectDqArticleById(articleId);
//        Long autorId = dqArticle1.getAutorId();
//        Assert.isTrue(StpUtil.hasRole("管理员") || StpUtil.getLoginId() == autorId, "不能修改他人的文章");
//        DqArticle dqArticle = iAdminDqArticleService.selectDqArticleById(articleId);
//        dqArticle.setArticleTitle(dqArticleVO.getArticletitle());
//        dqArticle.setArticleContent(dqArticleVO.getArticlecontent());
//        dqArticle.setArticleImage(dqArticleVO.getArticleimage());
//        dqArticle.setTypeId(dqArticleVO.getTypeId());
//        dqArticle.setAutorId(Long.parseLong(String.valueOf(StpUtil.getLoginId())));
//
//        if (StringUtils.isNotEmpty(dqArticleVO.getAuthornickname())) {
//            dqArticle.setAuthorNickname(dqArticleVO.getAuthornickname());
//        } else {
//            DqUser dqUser = iUserService.selectDqUserById(Long.parseLong(String.valueOf(StpUtil.getLoginId())));
//            dqArticle.setAuthorNickname(dqUser.getNickName());
//        }
//
//        dqArticle.setCreateTime(DateUtils.getNowDate());
//        dqArticle.setUpdateTime(DateUtils.getNowDate());
//
//        int i = iAdminDqArticleService.updateDqArticle(dqArticle);
//        if (i > 0) {
//            return AjaxResult.success("更新成功", i);
//        } else {
//            return AjaxResult.error("更新失败", i);
//        }
//    }

}
