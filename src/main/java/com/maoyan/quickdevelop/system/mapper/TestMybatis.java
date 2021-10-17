package com.maoyan.quickdevelop.system.mapper;

import com.maoyan.quickdevelop.common.core.domain.DqArticle;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author 猫颜
 * @date 2021/5/27 21:25
 */
@Mapper
public interface TestMybatis {

    @Select("select * from dq_article")
    public List<DqArticle> sSelectDqArticles();
}
