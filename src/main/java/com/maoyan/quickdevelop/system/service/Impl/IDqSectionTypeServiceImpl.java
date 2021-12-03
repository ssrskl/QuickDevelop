package com.maoyan.quickdevelop.system.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.maoyan.quickdevelop.common.constant.HttpStatus;
import com.maoyan.quickdevelop.common.core.domain.DqSectionType;
import com.maoyan.quickdevelop.common.exception.CustomException;
import com.maoyan.quickdevelop.common.utils.StringUtils;
import com.maoyan.quickdevelop.system.mapper.DqSectionTypeMapper;
import com.maoyan.quickdevelop.system.service.IDqSectionTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class IDqSectionTypeServiceImpl implements IDqSectionTypeService {
  @Autowired
  private DqSectionTypeMapper dqSectionTypeMapper;

  @Override
  public List<DqSectionType> commonSelectSectionType(int pageNum, int pageSize, DqSectionType dqSectionType) {
    LambdaQueryWrapper<DqSectionType> dqSectionTypeLambdaQueryWrapper = new LambdaQueryWrapper<>();
    dqSectionTypeLambdaQueryWrapper
            .eq(StringUtils.isNotNull(dqSectionType.getSectionTypeId()),DqSectionType::getSectionTypeId,dqSectionType.getSectionTypeId())
            .eq(StringUtils.isNotEmpty(dqSectionType.getSectionTypeName()), DqSectionType::getSectionTypeName, dqSectionType.getSectionTypeName())
            .eq(StringUtils.isNotNull(dqSectionType.getSectionId()), DqSectionType::getSectionId, dqSectionType.getSectionId())
            .eq(StringUtils.isNotNull(dqSectionType.getSectionTypeMold()), DqSectionType::getSectionTypeMold, dqSectionType.getSectionTypeMold());
    PageHelper.startPage(pageNum, pageSize);
    List<DqSectionType> dqSectionTypes = dqSectionTypeMapper.selectList(dqSectionTypeLambdaQueryWrapper);
    if (StringUtils.isEmpty(dqSectionTypes)) {
      throw new CustomException("未查询到分类", HttpStatus.NOT_FOUND);
    }
    return dqSectionTypes;
  }
}
