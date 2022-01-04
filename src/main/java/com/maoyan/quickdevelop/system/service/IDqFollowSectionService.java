package com.maoyan.quickdevelop.system.service;

import com.maoyan.quickdevelop.common.core.domain.postprocessor.DqFollowSectionPostProcesser;
import com.maoyan.quickdevelop.common.core.domain.postprocessor.DqSectionPostProcessor;
import com.maoyan.quickdevelop.common.core.domain.postprocessor.DqUserPostProcessor;

import java.util.List;

public interface IDqFollowSectionService {
  /**
   * 根据用户ID查询这个用户关注的版块
   * @param pageNum
   * @param pageSize
   * @param dqUserId
   * @return
   */
  List<DqFollowSectionPostProcesser> selectFollowedDqSectionByUserId(int pageNum, int pageSize, Long dqUserId);

  /**
   * 根据版块ID查询关注这个版块的用户
   * @param pageNum
   * @param pageSize
   * @param dqSectionId
   * @return
   */
  List<DqFollowSectionPostProcesser> selectFollowerBySectionId(int pageNum, int pageSize, Long dqSectionId);

  /**
   * 当前用户关注指定版块
   * @param dqSectionId
   * @return
   */
  int followDqSectionBySectionId(Long dqSectionId);

  /**
   * 当前用户取消关注指定版块
   * @param dqSectionId
   * @return
   */
  int cancelFollowDqSectionBySectionId(Long dqSectionId);
}
