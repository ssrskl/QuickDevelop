package com.maoyan.quickdevelop.system.service.manageservice.Impl;

import com.maoyan.quickdevelop.common.core.domain.DqSection;
import com.maoyan.quickdevelop.system.mapper.DqSectionMapper;
import com.maoyan.quickdevelop.system.service.manageservice.IDqSectionManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 猫颜
 * @date 2021 年 12 月 04 日 下午5:13
 */
@Transactional
@Service
public class IDqSectionManageServiceImpl implements IDqSectionManageService {
  @Autowired
  private DqSectionMapper dqSectionMapper;
  @Override
  public int updateSection(DqSection dqSection) {
    // 去除参数
    return 0;
  }

  @Override
  public int deleteSection(Long dqSectionId) {
    return 0;
  }
}
