package com.maoyan.quickdevelop.system.service.Impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.maoyan.quickdevelop.common.core.domain.DqSectionType;
import com.maoyan.quickdevelop.common.utils.StringUtils;
import com.maoyan.quickdevelop.common.constant.HttpStatus;
import com.maoyan.quickdevelop.common.core.domain.DqArticle;
import com.maoyan.quickdevelop.common.core.domain.DqType;
import com.maoyan.quickdevelop.common.core.domain.DqUser;
import com.maoyan.quickdevelop.common.core.domain.postprocessor.DqArticlePostProcesser;
import com.maoyan.quickdevelop.common.exception.CustomException;
import com.maoyan.quickdevelop.common.utils.DateUtils;
import com.maoyan.quickdevelop.common.utils.MyQueryWrapper;
import com.maoyan.quickdevelop.system.domain.DqArticleVO;
import com.maoyan.quickdevelop.system.mapper.DqArticleMapper;
import com.maoyan.quickdevelop.system.mapper.DqSectionTypeMapper;
import com.maoyan.quickdevelop.system.mapper.DqTypeMapper;
import com.maoyan.quickdevelop.system.mapper.DqUserMapper;
import com.maoyan.quickdevelop.system.mapper.postprocessor.DqArticlePostProcessorMapper;
import com.maoyan.quickdevelop.system.service.IDqArticleService;
import com.maoyan.quickdevelop.system.service.IDqUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 猫颜
 * @date 2021/5/27 22:28
 * 状态校验的方法先islogin，再判断权限或身份
 */
@Transactional
@Service
public class IDqArticleServiceImpl implements IDqArticleService {

  QueryWrapper<DqArticle> queryWrapper = new QueryWrapper<>();
  @Autowired
  private DqArticleMapper dqArticleMapper;
  @Autowired
  private DqArticlePostProcessorMapper dqArticlePostProcessorMapper;
  @Autowired
  private DqTypeMapper dqTypeMapper;
  @Autowired
  private IDqUserService idqUserService;
  @Autowired
  private DqUserMapper dqUserMapper;
  @Autowired
  private DqSectionTypeMapper dqSectionTypeMapper;

  /**
   * 统一的查询方法
   *
   * @param dqArticlePostProcesser
   * @return
   */
  @Override
  public List<DqArticlePostProcesser> commonSelectDqArticlePostProcesser(int pageNum, int pageSize, DqArticlePostProcesser dqArticlePostProcesser) {
    PageHelper.startPage(pageNum, pageSize);
    List<DqArticlePostProcesser> dqArticlePostProcessers = dqArticlePostProcessorMapper.selectAllDqArticlePostProcesser(dqArticlePostProcesser);
    if (dqArticlePostProcessers.isEmpty()) {
      throw new CustomException("未查询到文章", HttpStatus.NOT_FOUND);
    }
    return dqArticlePostProcessers;
  }

  @Override
  public DqArticle selectDqArticleById(Long dqArticleId) {
    queryWrapper.eq("article_id", dqArticleId).eq("status", "0");
//        DqArticle dqArticle = (DqArticle) DqStatusDisposrUtils.disposeDqArticle(dqArticleMapper.selectById(dqArticleId));
    DqArticle dqArticle = dqArticleMapper.selectOne(queryWrapper);
    queryWrapper.clear();
    if (dqArticle == null) {
      throw new CustomException("未查询到此文章", HttpStatus.NOT_FOUND);
    }

    return dqArticle;

  }

  @Override
  public DqArticle selectDqArticleByTitle(String title) {
    MyQueryWrapper<DqArticle> myQueryWrapper = new MyQueryWrapper<>();
    myQueryWrapper.statuseq("article_title", title);
    DqArticle dqArticle = dqArticleMapper.selectOne(myQueryWrapper);
    myQueryWrapper.clear();
    if (dqArticle == null) {
      throw new CustomException("未查询到此文章", HttpStatus.NOT_FOUND);
    }

    return dqArticle;

  }

  @Override
  public List<DqArticle> selectDqArticlesByTypeId(int pageNum, int pageSize, Long typeId) {
    /** 对typeid校验,将Long转换为String **/
    queryWrapper.lambda()
            .eq(DqArticle::getStatus, "0")
            .orderByDesc(DqArticle::getArticleId);
//        MyQueryWrapper<DqArticle> myQueryWrapper = new MyQueryWrapper<>();
//        myQueryWrapper.statuseq("type_id", String.valueOf(typeId));
    DqType dqType = dqTypeMapper.selectById(typeId);
    if (StringUtils.isNull(dqType)) {
      throw new CustomException("此类型不存在", HttpStatus.NOT_FOUND);
    }
    PageHelper.startPage(pageNum, pageSize);
    List<DqArticle> dqArticles = dqArticleMapper.selectList(queryWrapper);
    queryWrapper.clear();
    //清除queryWrapper的数据，防止给其他的带来影响
    return dqArticles;
  }

  @Override
  public List<DqArticle> selectDqArticlesByAuthorId(int pageNum, int pageSize, Long authorId) {
    /** 对authorid校验,将Long转换为String **/
    MyQueryWrapper<DqArticle> myQueryWrapper = new MyQueryWrapper<>();
    myQueryWrapper.statuseq("author_id", String.valueOf(authorId));
    DqUser dqUser = idqUserService.selectDqUserById(authorId);
    if (StringUtils.isNull(dqUser)) {
      throw new CustomException("此用户不存在", HttpStatus.NOT_FOUND);
    }
    PageHelper.startPage(pageNum, pageSize);
    List<DqArticle> dqArticles = dqArticleMapper.selectList(myQueryWrapper);
    //清除queryWrapper的数据，防止给其他的带来影响
    myQueryWrapper.clear();
    return dqArticles;
  }

  @Override
  public List<DqArticle> selectDqArticlesByAuthorUsername(int pageNum, int pageSize, String authorUsername) {
    /** 对authornickname校验 **/
    DqUser dqUser = idqUserService.selectDqUserByUserName(authorUsername);
    if (StringUtils.isNull(dqUser)) {
      throw new CustomException("此用户不存在", HttpStatus.NOT_FOUND);
    }
    PageHelper.startPage(pageNum, pageSize);
    MyQueryWrapper<DqArticle> myQueryWrapper = new MyQueryWrapper<>();
    myQueryWrapper.statuseq("author_username", authorUsername);
    List<DqArticle> dqArticles = dqArticleMapper.selectList(myQueryWrapper);
    //清除queryWrapper的数据，防止给其他的带来影响
    myQueryWrapper.clear();
    if (dqArticles.isEmpty()) {
      throw new CustomException("此用户没有文章", HttpStatus.NOT_FOUND);
    }

    return dqArticles;
  }


  @Override
  public int publishDqArticle(DqArticle dqArticle) {
    Long autorId = dqArticle.getAuthorId();
    DqUser dqUser = dqUserMapper.selectById(autorId);
//        DqUser dqUser = idqUserService.selectDqUserById(autorId);
    int insert = dqArticleMapper.insert(dqArticle);
    if (insert <= 0) {
      throw new CustomException("添加失败", HttpStatus.ERROR);
    }
    return insert;
  }

  @Override
  public int publishDqArticle(DqArticleVO dqArticleVO) {
    DqArticle dqArticle = new DqArticle();
    LambdaQueryWrapper<DqSectionType> dqSectionTypeLambdaQueryWrapper = new LambdaQueryWrapper<>();
    dqSectionTypeLambdaQueryWrapper.eq(DqSectionType::getSectionTypeId, dqArticleVO.getSectionTypeId());
    DqSectionType dqSectionType = dqSectionTypeMapper.selectOne(dqSectionTypeLambdaQueryWrapper);
    dqArticle.setArticleTitle(dqArticleVO.getArticleTitle());
    dqArticle.setArticleContent(dqArticleVO.getArticleContent());
    dqArticle.setArticleImage(dqArticleVO.getArticleImage());
    dqArticle.setSectionTypeId(dqArticleVO.getSectionTypeId());
    dqArticle.setSectionId(dqSectionType.getSectionId());
    dqArticle.setAuthorId(StpUtil.getLoginIdAsLong());
    dqArticle.setStatus("0");
    dqArticle.setArticleWeight(0L);
    dqArticle.setCreateTime(DateUtil.date());
    dqArticle.setUpdateTime(DateUtil.date());
    int insert = dqArticleMapper.insert(dqArticle);
    if (insert <= 0) {
      throw new CustomException("发表失败",HttpStatus.ERROR);
    }
    return insert;
  }

  @Override
  public int updateDqArticle(DqArticle newdqArticle) {
    // 获取文章ID
    Long articleId = newdqArticle.getArticleId();
    DqArticle dqArticle = dqArticleMapper.selectById(articleId);
    if (dqArticle == null) {
      throw new CustomException("没有此文章", HttpStatus.NOT_FOUND);
    }
    if (StringUtils.equals("0", dqArticle.getStatus())) {
      throw new CustomException("此文章已被封禁无法更改", HttpStatus.FORBIDDEN);
    }
    if (StpUtil.getLoginIdAsLong() != dqArticle.getAuthorId()) {
      throw new CustomException("不能更新其他人的文章", HttpStatus.ERROR);
    }
    dqArticle.setArticleTitle(newdqArticle.getArticleTitle());
    dqArticle.setArticleContent(newdqArticle.getArticleContent());
    dqArticle.setArticleImage(newdqArticle.getArticleImage());
    dqArticle.setUpdateTime(DateUtils.getNowDate());
    int i = dqArticleMapper.updateById(dqArticle);
    if (i <= 0) {
      throw new CustomException("更新失败", HttpStatus.ERROR);
    }
    return i;
  }

  @Override
  public int deleteDqArticleById(Long dqArticleId) {
    DqArticle dqArticle = dqArticleMapper.selectById(dqArticleId);
    if (dqArticle == null) {
      throw new CustomException("未查询到此文章", HttpStatus.NOT_FOUND);
    }
    Long autorId = dqArticle.getAuthorId();
    if (StpUtil.getLoginIdAsLong() != autorId) {
      throw new CustomException("不能删除其他人的文章", HttpStatus.ERROR);
    }
    // 删除文章
    int i = dqArticleMapper.deleteById(dqArticleId);
    // 删除收藏信息
    if (i > 0) {
      return i;
    } else {
      throw new CustomException("删除失败", HttpStatus.ERROR);
    }
  }
}

