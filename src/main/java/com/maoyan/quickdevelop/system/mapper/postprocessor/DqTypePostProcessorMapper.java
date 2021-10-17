package com.maoyan.quickdevelop.system.mapper.postprocessor;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maoyan.quickdevelop.common.core.domain.postprocessor.DqTypePostProcesser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 猫颜
 * @date 2021年08月02日 上午7:48
 */
@Mapper
public interface DqTypePostProcessorMapper extends BaseMapper<DqTypePostProcesser> {
    List<DqTypePostProcesser> selectAllDqTypePostProcesser(@Param("dqTypePostProcesser") DqTypePostProcesser dqTypePostProcesser);
}
