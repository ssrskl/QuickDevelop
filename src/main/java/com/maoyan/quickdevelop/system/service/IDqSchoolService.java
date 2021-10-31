package com.maoyan.quickdevelop.system.service;


import com.maoyan.quickdevelop.common.core.domain.DqSchool;

import java.util.List;

public interface IDqSchoolService {
  /**
   * 通用查询学校
   * @param pageNum
   * @param pageSize
   * @param dqSchool
   * @return
   */
  List<DqSchool> commonSelectDqSchool(int pageNum, int pageSize, DqSchool dqSchool);

  /**
   * 查询指定用户所在的学校
   * @param dqUserId
   * @return
   */
  DqSchool selectDqSchoolByUserId(Long dqUserId);

  /**
   * 设置当前用户所在的学校
   * @param dqSchoolId
   * @return
   */
  int setSchoolForDqUser(Long dqSchoolId);
}
