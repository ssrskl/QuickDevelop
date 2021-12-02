package com.maoyan.quickdevelop.system.mapper.postprocessor;

import com.maoyan.quickdevelop.common.core.domain.postprocessor.DqCollectionPostProcesser;
import com.maoyan.quickdevelop.common.core.domain.postprocessor.DqSectionPostProcessor;
import com.maoyan.quickdevelop.common.core.domain.postprocessor.DqUserPostProcessor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DqCollectionPostProcessorMapper {
  public List<DqCollectionPostProcesser> selectDqCollectionPostProcessor(@Param(value = "dqCollectionPostProcesser") DqCollectionPostProcesser dqCollectionPostProcesser);
}
