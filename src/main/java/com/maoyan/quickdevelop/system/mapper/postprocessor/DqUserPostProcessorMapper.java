package com.maoyan.quickdevelop.system.mapper.postprocessor;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maoyan.quickdevelop.common.core.domain.DqUser;
import com.maoyan.quickdevelop.common.core.domain.postprocessor.DqUserPostProcessor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DqUserPostProcessorMapper extends BaseMapper<DqUserPostProcessor> {
  List<DqUserPostProcessor> selectDqUserPostProcesser(@Param(value = "dqUser") DqUser dqUser);
}
