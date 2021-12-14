package com.maoyan.quickdevelop.system.service.manageservice;

import com.maoyan.quickdevelop.system.domain.DqSchoolCreateVO;
import com.maoyan.quickdevelop.system.domain.DqSchoolUpdateVO;

/**
 * @author 猫颜
 * @date 2021/12/4
 */
public interface IDqSchoolManageService {
  int createSchool(DqSchoolCreateVO dqSchoolCreateVO);

  int updateSchool(DqSchoolUpdateVO dqSchoolUpdateVO);

  int deleteSchool(Long dqSchoolId);
}
