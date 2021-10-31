package com.maoyan.quickdevelop.system.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.maoyan.quickdevelop.common.constant.HttpStatus;
import com.maoyan.quickdevelop.common.core.domain.DqSchool;
import com.maoyan.quickdevelop.common.exception.CustomException;
import com.maoyan.quickdevelop.common.utils.StringUtils;
import com.maoyan.quickdevelop.system.mapper.DqSchoolMapper;
import com.maoyan.quickdevelop.system.service.IDqSchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class IDqSchoolServiceImpl implements IDqSchoolService {
  @Autowired
  private DqSchoolMapper dqSchoolMapper;

  @Override
  public List<DqSchool> commonSelectDqSchool(int pageNum, int pageSize, DqSchool dqSchool) {
    PageHelper.startPage(pageNum, pageSize);
    LambdaQueryWrapper<DqSchool> dqSchoolLambdaQueryWrapper = new LambdaQueryWrapper<>();
    dqSchoolLambdaQueryWrapper.like(StringUtils.isNotEmpty(dqSchool.getSchoolName()), DqSchool::getSchoolName, dqSchool.getSchoolName());
    List<DqSchool> dqSchools = dqSchoolMapper.selectList(dqSchoolLambdaQueryWrapper);
    if (StringUtils.isEmpty(dqSchools)) {
      throw new CustomException("未查询到学校", HttpStatus.NOT_FOUND);
    }
    return dqSchools;
  }

  @Override
  public DqSchool selectDqSchoolByUserId(Long dqUserId) {
    
    return null;
  }

  @Override
  public int setSchoolForDqUser(Long dqSchoolId) {
    return 0;
  }
}
