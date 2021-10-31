package com.maoyan.quickdevelop.system.service;

import com.maoyan.quickdevelop.common.core.domain.DqSection;
import com.maoyan.quickdevelop.common.core.domain.postprocessor.DqSectionPostProcessor;

import java.util.List;

public interface IDqSectionService {
  /**
   * 通用查询版块
   * @param dqSection
   * @return
   */
  List<DqSectionPostProcessor> commonSelectDqSectionPostProcessor(int pageNum, int pageSize,DqSection dqSection);
}
