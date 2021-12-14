package com.maoyan.quickdevelop.system.service.manageservice.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.maoyan.quickdevelop.common.constant.HttpStatus;
import com.maoyan.quickdevelop.common.core.domain.DqSchool;
import com.maoyan.quickdevelop.common.exception.CustomException;
import com.maoyan.quickdevelop.common.utils.DateUtils;
import com.maoyan.quickdevelop.common.utils.StringUtils;
import com.maoyan.quickdevelop.system.domain.DqSchoolCreateVO;
import com.maoyan.quickdevelop.system.domain.DqSchoolUpdateVO;
import com.maoyan.quickdevelop.system.mapper.DqSchoolMapper;
import com.maoyan.quickdevelop.system.service.manageservice.IDqSchoolManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 猫颜
 * @date 2021 年 12 月 04 日 上午8:46
 */
@Transactional
@Service
public class IDqSchoolManageServiceImpl implements IDqSchoolManageService {

  @Autowired
  private DqSchoolMapper dqSchoolMapper;

  @Override
  public int createSchool(DqSchoolCreateVO dqSchoolCreateVO) {
    DqSchool dqSchool = new DqSchool();
    dqSchool.setSchoolName(dqSchoolCreateVO.getSchoolName());
    dqSchool.setSchoolIntroduce(dqSchoolCreateVO.getSchoolIntroduce());
    dqSchool.setSchoolBadge(dqSchoolCreateVO.getSchoolBadge());
    dqSchool.setSchoolMotto(dqSchoolCreateVO.getSchoolMotto());
    dqSchool.setSchoolBackground(dqSchoolCreateVO.getSchoolBackground());
    dqSchool.setSchoolBuildDate(dqSchoolCreateVO.getSchoolBuildDate());
    dqSchool.setSchoolLocation(dqSchoolCreateVO.getSchoolLocation());
    dqSchool.setCreateTime(DateUtils.getNowDate());
    dqSchool.setUpdateTime(DateUtils.getNowDate());
    int insert = dqSchoolMapper.insert(dqSchool);
    if (insert <= 0) {
      throw new CustomException("创建学校失败",HttpStatus.ERROR);
    }
    return insert;
  }

  @Override
  public int updateSchool(DqSchoolUpdateVO dqSchoolUpdateVO) {
    DqSchool dqSchool = dqSchoolMapper.selectById(dqSchoolUpdateVO.getSchoolId());
    if (StringUtils.isNull(dqSchool)) {
      throw new CustomException("没有此学校", HttpStatus.NOT_FOUND);
    }
    dqSchool.setSchoolName(dqSchoolUpdateVO.getSchoolName());
    dqSchool.setSchoolIntroduce(dqSchoolUpdateVO.getSchoolIntroduce());
    dqSchool.setSchoolBadge(dqSchoolUpdateVO.getSchoolBadge());
    dqSchool.setSchoolMotto(dqSchoolUpdateVO.getSchoolMotto());
    dqSchool.setSchoolBackground(dqSchoolUpdateVO.getSchoolBackground());
    dqSchool.setSchoolBuildDate(dqSchoolUpdateVO.getSchoolBuildDate());
    dqSchool.setSchoolLocation(dqSchoolUpdateVO.getSchoolLocation());
    dqSchool.setUpdateTime(DateUtils.getNowDate());
    int i = dqSchoolMapper.updateById(dqSchool);
    if (i <= 0) {
      throw new CustomException("修改失败", HttpStatus.ERROR);
    }
    return i;
  }

  @Override
  public int deleteSchool(Long dqSchoolId) {
    DqSchool dqSchool = dqSchoolMapper.selectById(dqSchoolId);
    if (StringUtils.isNull(dqSchool)) {
      throw new CustomException("没有此学校", HttpStatus.NOT_FOUND);
    }
    int i = dqSchoolMapper.deleteById(dqSchoolId);
    if (i<=0){
      throw new CustomException("删除失败",HttpStatus.ERROR);
    }
    return 0;
  }
}
