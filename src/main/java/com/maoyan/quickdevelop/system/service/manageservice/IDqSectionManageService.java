package com.maoyan.quickdevelop.system.service.manageservice;

import com.maoyan.quickdevelop.common.core.domain.DqSection;

public interface IDqSectionManageService {
  int updateSection(DqSection dqSection);

  int deleteSection(Long dqSectionId);
}
