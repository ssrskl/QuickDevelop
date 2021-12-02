package com.maoyan.quickdevelop.system.service.Impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.maoyan.quickdevelop.common.constant.HttpStatus;
import com.maoyan.quickdevelop.common.core.domain.DqArticle;
import com.maoyan.quickdevelop.common.core.domain.DqCollection;
import com.maoyan.quickdevelop.common.core.domain.postprocessor.DqCollectionPostProcesser;
import com.maoyan.quickdevelop.common.exception.CustomException;
import com.maoyan.quickdevelop.common.utils.StringUtils;
import com.maoyan.quickdevelop.system.mapper.DqArticleMapper;
import com.maoyan.quickdevelop.system.mapper.DqCollectionMapper;
import com.maoyan.quickdevelop.system.mapper.postprocessor.DqCollectionPostProcessorMapper;
import com.maoyan.quickdevelop.system.service.IDqCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class IDqCollectionServiceImpl implements IDqCollectionService {
  @Autowired
  private DqCollectionPostProcessorMapper dqCollectionPostProcessorMapper;
  @Autowired
  private DqCollectionMapper dqCollectionMapper;
  @Autowired
  private DqArticleMapper dqArticleMapper;

  @Override
  public List<DqCollectionPostProcesser> commonSelectDqCollectionPostProcesser(int pageNum, int pageSize, DqCollectionPostProcesser dqCollectionPostProcesser) {
    PageHelper.startPage(pageNum, pageSize);
    List<DqCollectionPostProcesser> dqCollectionPostProcessers = dqCollectionPostProcessorMapper.selectDqCollectionPostProcessor(dqCollectionPostProcesser);
    if (StringUtils.isEmpty(dqCollectionPostProcessers)) {
      throw new CustomException("未查询到收藏信息", HttpStatus.NOT_FOUND);
    }
    return dqCollectionPostProcessers;
  }

  @Override
  public int collectDqArticleById(Long dqArticleId) {
    // 写操作，验证文章是否存在
    DqArticle dqArticle = dqArticleMapper.selectById(dqArticleId);
    if (StringUtils.isNull(dqArticle)) {
      throw new CustomException("此文章不存在", HttpStatus.NOT_FOUND);
    }
    DqCollection dqCollection = new DqCollection();
    dqCollection.setArticleId(dqArticleId);
    dqCollection.setUserId(StpUtil.getLoginIdAsLong());
    int insert = dqCollectionMapper.insert(dqCollection);
    if (insert <= 0) {
      throw new CustomException("收藏失败", HttpStatus.ERROR);
    }
    return 0;
  }

  @Override
  public int cancelCollectDqArticleById(Long dqArticleId) {
    LambdaQueryWrapper<DqCollection> dqCollectionLambdaQueryWrapper = new LambdaQueryWrapper<>();
    dqCollectionLambdaQueryWrapper
            .eq(DqCollection::getArticleId, dqArticleId)
            .eq(DqCollection::getUserId, StpUtil.getLoginIdAsLong());
    DqCollection dqCollection = dqCollectionMapper.selectOne(dqCollectionLambdaQueryWrapper);
    if (StringUtils.isNull(dqCollection)) {
      throw new CustomException("您还没有收藏此文章", HttpStatus.NOT_FOUND);
    }
    int delete = dqCollectionMapper.delete(dqCollectionLambdaQueryWrapper);
    if (delete <= 0) {
      throw new CustomException("取消收藏失败", HttpStatus.ERROR);
    }
    return delete;
  }
}
