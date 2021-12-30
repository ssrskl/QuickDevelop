package com.maoyan.quickdevelop.system.service.manageservice;

import com.maoyan.quickdevelop.common.core.domain.DqSection;
import com.maoyan.quickdevelop.system.domain.DqSectionTypeVO;

public interface IDqSectionManageService {
  int updateSection(DqSection dqSection);

  int deleteSection(Long dqSectionId);

  int addSectionType(DqSectionTypeVO dqSectionTypeVO);
}
