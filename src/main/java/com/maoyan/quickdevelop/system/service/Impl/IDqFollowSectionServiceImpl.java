package com.maoyan.quickdevelop.system.service.Impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.maoyan.quickdevelop.common.constant.HttpStatus;
import com.maoyan.quickdevelop.common.core.domain.DqFollowSection;
import com.maoyan.quickdevelop.common.core.domain.postprocessor.DqFollowSectionPostProcesser;
import com.maoyan.quickdevelop.common.core.domain.postprocessor.DqSectionPostProcessor;
import com.maoyan.quickdevelop.common.core.domain.postprocessor.DqUserPostProcessor;
import com.maoyan.quickdevelop.common.exception.CustomException;
import com.maoyan.quickdevelop.common.utils.DateUtils;
import com.maoyan.quickdevelop.common.utils.StringUtils;
import com.maoyan.quickdevelop.system.mapper.DqFollowSectionMapper;
import com.maoyan.quickdevelop.system.mapper.postprocessor.DqFollowSectionPostProcessorMapper;
import com.maoyan.quickdevelop.system.service.IDqFollowSectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class IDqFollowSectionServiceImpl implements IDqFollowSectionService {

  @Autowired
  private DqFollowSectionPostProcessorMapper dqFollowSectionPostProcessorMapper;
  @Autowired
  private DqFollowSectionMapper dqFollowSectionMapper;

  @Override
  public List<DqFollowSectionPostProcesser> selectFollowedDqSectionByUserId(int pageNum, int pageSize, Long dqUserId) {
    PageHelper.startPage(pageNum, pageSize);
    List<DqFollowSectionPostProcesser> dqFollowSectionPostProcesserList = dqFollowSectionPostProcessorMapper.selectFollowedSectionByUserId(dqUserId);
    if (StringUtils.isEmpty(dqFollowSectionPostProcesserList)) {
      throw new CustomException("该用户没有关注版块", HttpStatus.NOT_FOUND);
    }
    return dqFollowSectionPostProcesserList;
  }

  @Override
  public List<DqFollowSectionPostProcesser> selectFollowerBySectionId(int pageNum, int pageSize, Long dqSectionId) {
    PageHelper.startPage(pageNum, pageSize);
    List<DqFollowSectionPostProcesser> dqFollowSectionPostProcesserList = dqFollowSectionPostProcessorMapper.selectGiveFollowDqUserBySectionId(dqSectionId);
    if (StringUtils.isEmpty(dqFollowSectionPostProcesserList)) {
      throw new CustomException("该版块还没有关注者", HttpStatus.NOT_FOUND);
    }
    return dqFollowSectionPostProcesserList;
  }

  @Override
  public int followDqSectionBySectionId(Long dqSectionId) {
    // 检查是否已经关注

    DqFollowSection dqFollowSection = new DqFollowSection();
    dqFollowSection.setFollowedDqsectionId(dqSectionId);
    dqFollowSection.setGiveFollowDqUserId(StpUtil.getLoginIdAsLong());
    dqFollowSection.setCreateTime(DateUtils.getNowDate());
    dqFollowSection.setUpdateTime(DateUtil.date());
    int insert = dqFollowSectionMapper.insert(dqFollowSection);
    if (insert <= 0) {
      throw new CustomException("关注失败", HttpStatus.SUCCESS);
    }
    return insert;
  }

  @Override
  public int cancelFollowDqSectionBySectionId(Long dqSectionId) {
    LambdaQueryWrapper<DqFollowSection> dqFollowSectionLambdaQueryWrapper = new LambdaQueryWrapper<>();
    dqFollowSectionLambdaQueryWrapper.eq(DqFollowSection::getFollowedDqsectionId, dqSectionId)
        .eq(DqFollowSection::getGiveFollowDqUserId, StpUtil.getLoginIdAsLong());
    int delete = dqFollowSectionMapper.delete(dqFollowSectionLambdaQueryWrapper);
    if (delete <= 0) {
      throw new CustomException("取消关注失败", HttpStatus.ERROR);
    }
    return delete;
  }
}
