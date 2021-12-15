package com.maoyan.quickdevelop.system.service.manageservice.Impl;

import cn.dev33.satoken.stp.StpUtil;
import com.maoyan.quickdevelop.common.constant.HttpStatus;
import com.maoyan.quickdevelop.common.core.domain.DqSection;
import com.maoyan.quickdevelop.common.exception.CustomException;
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
    // 权限校验
//    StpUtil.checkPermissionOr("section-manage-"+dqSection.getSectionId());
    int i = dqSectionMapper.updateById(dqSection);
    if (i <= 0) {
      throw new CustomException("更新失败", HttpStatus.ERROR);
    }
    return i;
  }

  @Override
  public int deleteSection(Long dqSectionId) {
    return 0;
  }
}
