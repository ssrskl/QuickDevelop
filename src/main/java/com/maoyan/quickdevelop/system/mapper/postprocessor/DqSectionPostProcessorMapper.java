package com.maoyan.quickdevelop.system.mapper.postprocessor;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maoyan.quickdevelop.common.core.domain.DqSection;
import com.maoyan.quickdevelop.common.core.domain.DqUser;
import com.maoyan.quickdevelop.common.core.domain.postprocessor.DqSectionPostProcessor;
import com.maoyan.quickdevelop.common.core.domain.postprocessor.DqUserPostProcessor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DqSectionPostProcessorMapper extends BaseMapper<DqSectionPostProcessor> {
  List<DqSectionPostProcessor> selectDqSectionPostProcesser(@Param(value = "dqSection")DqSection dqSection);
//  List<DqUserPostProcessor> selectDqUserPostProcesser(@Param(value = "dqUser") DqUser dqUser);
}
