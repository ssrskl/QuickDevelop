package com.maoyan.quickdevelop.system.service;

import com.maoyan.quickdevelop.common.core.domain.DqSectionType;

import java.util.List;

public interface IDqSectionTypeService {
  /**
   * 通用查询版块分类
   *
   * @param dqSectionType
   * @return
   */
  List<DqSectionType> commonSelectSectionType(int pageNum, int pageSize, DqSectionType dqSectionType);
}
