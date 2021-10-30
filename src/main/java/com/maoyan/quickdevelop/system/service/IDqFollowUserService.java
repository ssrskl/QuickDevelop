package com.maoyan.quickdevelop.system.service;

import com.maoyan.quickdevelop.common.core.domain.postprocessor.DqUserPostProcessor;

import java.util.List;

public interface IDqFollowUserService {
  List<DqUserPostProcessor> selectFollowedDqUserByUserId(int pageNum, int pageSize, Long dqUserId);

  List<DqUserPostProcessor> selectFansByUserId(int pageNum, int pageSize, Long dqUserId);
}
