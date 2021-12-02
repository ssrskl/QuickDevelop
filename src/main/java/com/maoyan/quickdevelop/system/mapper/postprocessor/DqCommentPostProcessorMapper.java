package com.maoyan.quickdevelop.system.mapper.postprocessor;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maoyan.quickdevelop.common.core.domain.DqComment;
import com.maoyan.quickdevelop.common.core.domain.postprocessor.DqCommentPostProcesser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 猫颜
 * @date 2021年08月03日 上午11:48
 */
@Mapper
public interface DqCommentPostProcessorMapper extends BaseMapper<DqCommentPostProcesser> {
  List<DqCommentPostProcesser> commonSelectDqCommentPostProcesser(@Param("dqComment") DqComment dqComment);
}
