package com.maoyan.quickdevelop.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maoyan.quickdevelop.common.core.domain.DqArticle;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author 猫颜
 * @date 2021/5/27 22:25
 */
@Mapper
public interface DqArticleMapper extends BaseMapper<DqArticle> {

}
