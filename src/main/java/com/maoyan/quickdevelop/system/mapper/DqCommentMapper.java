package com.maoyan.quickdevelop.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maoyan.quickdevelop.common.core.domain.DqArticle;
import com.maoyan.quickdevelop.common.core.domain.DqComment;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 猫颜
 * @date 2021/5/27 22:25
 */
@Mapper
public interface DqCommentMapper extends BaseMapper<DqComment> {

}
