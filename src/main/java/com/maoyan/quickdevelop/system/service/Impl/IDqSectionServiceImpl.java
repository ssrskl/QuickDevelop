package com.maoyan.quickdevelop.system.service.Impl;

import com.github.pagehelper.PageHelper;
import com.maoyan.quickdevelop.common.constant.HttpStatus;
import com.maoyan.quickdevelop.common.core.domain.DqSection;
import com.maoyan.quickdevelop.common.core.domain.postprocessor.DqSectionPostProcessor;
import com.maoyan.quickdevelop.common.exception.CustomException;
import com.maoyan.quickdevelop.common.utils.StringUtils;
import com.maoyan.quickdevelop.system.mapper.postprocessor.DqSectionPostProcessorMapper;
import com.maoyan.quickdevelop.system.service.IDqSectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class IDqSectionServiceImpl implements IDqSectionService {
  @Autowired
  private DqSectionPostProcessorMapper dqSectionPostProcessorMapper;

  @Override
  public List<DqSectionPostProcessor> commonSelectDqSectionPostProcessor(int pageNum, int pageSize, DqSection dqSection) {
    PageHelper.startPage(pageNum, pageSize);
    List<DqSectionPostProcessor> dqSectionPostProcessors = dqSectionPostProcessorMapper.selectDqSectionPostProcesser(dqSection);
    if (StringUtils.isEmpty(dqSectionPostProcessors)) {
      throw new CustomException("未查询到版块", HttpStatus.NOT_FOUND);
    }
    return dqSectionPostProcessors;
  }
}
