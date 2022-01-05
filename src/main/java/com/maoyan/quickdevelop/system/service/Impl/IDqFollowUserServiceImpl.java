package com.maoyan.quickdevelop.system.service.Impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.maoyan.quickdevelop.common.constant.HttpStatus;
import com.maoyan.quickdevelop.common.core.domain.DqFollowUser;
import com.maoyan.quickdevelop.common.core.domain.DqUser;
import com.maoyan.quickdevelop.common.core.domain.postprocessor.DqFollowDqUserPostProcesser;
import com.maoyan.quickdevelop.common.core.domain.postprocessor.DqUserPostProcessor;
import com.maoyan.quickdevelop.common.exception.CustomException;
import com.maoyan.quickdevelop.common.utils.DateUtils;
import com.maoyan.quickdevelop.common.utils.StringUtils;
import com.maoyan.quickdevelop.system.mapper.DqFollowUserMapper;
import com.maoyan.quickdevelop.system.mapper.postprocessor.DqFollowUserPostProcessorMapper;
import com.maoyan.quickdevelop.system.service.IDqFollowUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class IDqFollowUserServiceImpl implements IDqFollowUserService {
  @Autowired
  private DqFollowUserPostProcessorMapper dqFollowUserPostProcessorMapper;
  @Autowired
  private DqFollowUserMapper dqFollowUserMapper;

  @Override
  public List<DqFollowDqUserPostProcesser> selectFollowedDqUserByUserId(int pageNum, int pageSize, Long dqUserId) {
    PageHelper.startPage(pageNum, pageSize);
    List<DqFollowDqUserPostProcesser> dqUserPostProcessors = dqFollowUserPostProcessorMapper.selectFollowedDqUserByUserId(dqUserId);
    if (StringUtils.isEmpty(dqUserPostProcessors)) {
      throw new CustomException("该用户没有关注任何人", HttpStatus.NOT_FOUND);
    }
    return dqUserPostProcessors;
  }

  @Override
  public List<DqFollowDqUserPostProcesser> selectFansByUserId(int pageNum, int pageSize, Long dqUserId) {
    List<DqFollowDqUserPostProcesser> dqUserPostProcessors = dqFollowUserPostProcessorMapper.selectGiveFollowDqUserByUserId(dqUserId);
    if (StringUtils.isEmpty(dqUserPostProcessors)) {
      throw new CustomException("该用户没有粉丝", HttpStatus.NOT_FOUND);
    }
    return dqUserPostProcessors;
  }

  @Override
  public int followDqUserByUserId(Long dqUserId) {
    if (StringUtils.equals(dqUserId.toString(), StpUtil.getLoginIdAsString())) {
      throw new CustomException("不能够关注自己哦!", HttpStatus.ERROR);
    }
    DqFollowUser dqFollowUser = new DqFollowUser();
    dqFollowUser.setFollowedDqUserId(StpUtil.getLoginIdAsLong());
    dqFollowUser.setFollowedDqUserId(dqUserId);
    dqFollowUser.setCreateTime(DateUtils.getNowDate());
    dqFollowUser.setUpdateTime(DateUtil.date());
    int insert = dqFollowUserMapper.insert(dqFollowUser);
    if (insert <= 0) {
      throw new CustomException("关注失败", HttpStatus.ERROR);
    }
    return insert;
  }

  @Override
  public int cancelFollowDqUserByUserId(Long dqUserId) {
    LambdaQueryWrapper<DqFollowUser> dqFollowUserLambdaQueryWrapper = new LambdaQueryWrapper<>();
    dqFollowUserLambdaQueryWrapper.eq(DqFollowUser::getFollowedDqUserId, dqUserId)
            .eq(DqFollowUser::getGiveFollowDqUserId, StpUtil.getLoginIdAsLong());
    int delete = dqFollowUserMapper.delete(dqFollowUserLambdaQueryWrapper);
    if (delete <= 0) {
      throw new CustomException("取消关注失败", HttpStatus.ERROR);
    }
    return delete;
  }
}
