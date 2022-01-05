package com.maoyan.quickdevelop.system.service;

import com.maoyan.quickdevelop.common.core.domain.postprocessor.DqFollowDqUserPostProcesser;
import com.maoyan.quickdevelop.common.core.domain.postprocessor.DqUserPostProcessor;

import java.util.List;

public interface IDqFollowUserService {
  /**
   * 查询指定用户关注的用户
   *
   * @param pageNum
   * @param pageSize
   * @param dqUserId
   * @return
   */
  List<DqFollowDqUserPostProcesser> selectFollowedDqUserByUserId(int pageNum, int pageSize, Long dqUserId);

  /**
   * 查询指定用户的粉丝信息
   *
   * @param pageNum
   * @param pageSize
   * @param dqUserId
   * @return
   */
  List<DqFollowDqUserPostProcesser> selectFansByUserId(int pageNum, int pageSize, Long dqUserId);

  /**
   * 当前用户关注指定用户
   *
   * @param dqUserId
   * @return
   */
  int followDqUserByUserId(Long dqUserId);

  /**
   * 当前用户取消关注指定用户
   *
   * @param dqUserId
   * @return
   */
  int cancelFollowDqUserByUserId(Long dqUserId);
}
