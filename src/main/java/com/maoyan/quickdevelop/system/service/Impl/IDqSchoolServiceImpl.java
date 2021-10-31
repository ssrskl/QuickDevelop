package com.maoyan.quickdevelop.system.service.Impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.maoyan.quickdevelop.common.constant.HttpStatus;
import com.maoyan.quickdevelop.common.core.domain.DqSchool;
import com.maoyan.quickdevelop.common.core.domain.DqUser;
import com.maoyan.quickdevelop.common.exception.CustomException;
import com.maoyan.quickdevelop.common.utils.StringUtils;
import com.maoyan.quickdevelop.system.mapper.DqSchoolMapper;
import com.maoyan.quickdevelop.system.mapper.DqUserMapper;
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
  @Autowired
  private DqUserMapper dqUserMapper;

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
    // 获取用户
    LambdaQueryWrapper<DqUser> dqUserLambdaQueryWrapper = new LambdaQueryWrapper<>();
    dqUserLambdaQueryWrapper.eq(DqUser::getUserId, dqUserId);
    DqUser dqUser = dqUserMapper.selectOne(dqUserLambdaQueryWrapper);
    if (StringUtils.isNull(dqUser)) {
      throw new CustomException("该用户不存在", HttpStatus.NOT_FOUND);
    }
    if (StringUtils.equals(dqUser.getSchoolId().toString(), "0")) {
      throw new CustomException("该用户未设置学校", HttpStatus.NOT_FOUND);
    }
    // 获取学校
    LambdaQueryWrapper<DqSchool> dqSchoolLambdaQueryWrapper = new LambdaQueryWrapper<>();
    dqSchoolLambdaQueryWrapper.eq(DqSchool::getSchoolId, dqUser.getSchoolId());
    DqSchool dqSchool = dqSchoolMapper.selectOne(dqSchoolLambdaQueryWrapper);
    if (StringUtils.isNull(dqSchool)) {
      throw new CustomException("此学校不存在", HttpStatus.NOT_FOUND);
    }
    return dqSchool;
  }

  @Override
  public int setSchoolForDqUser(Long dqSchoolId) {
    DqUser dqUser = new DqUser();
    dqUser.setUserId(StpUtil.getLoginIdAsLong());
    dqUser.setSchoolId(dqSchoolId);
    int i = dqUserMapper.updateById(dqUser);
    if (i <= 0) {
      throw new CustomException("设置失败", HttpStatus.ERROR);
    }
    return i;
  }
}
