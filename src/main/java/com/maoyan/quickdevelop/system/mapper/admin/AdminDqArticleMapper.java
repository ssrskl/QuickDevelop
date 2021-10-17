package com.maoyan.quickdevelop.system.mapper.admin;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maoyan.quickdevelop.common.core.domain.DqArticle;
import com.maoyan.quickdevelop.common.core.domain.DqUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 * @author 猫颜
 * @date  2021 7 12 下午3:58
 * @param
 * @return null
 */
@Mapper
public interface AdminDqArticleMapper extends BaseMapper<DqUser> {

    /**
     * 查询文章列表
     * @author 猫颜
     * @date  下午4:03
     * @param dqArticle
     * @param pageNum
     * @param pageSize
     * @return java.util.List<com.maoyan.quickdevelop.common.core.domain.DqArticle>
     */
    public List<DqArticle> selectDqArticleList(@Param("dqArticle") DqArticle dqArticle,
                                               @Param("pageNum") int pageNum,
                                               @Param("pageSize") int pageSize);
    /**
     * 新增文章
     * @author 猫颜
     * @date  上午10:29
     * @param dqArticle
     * @return int
     */
    public int insertDqArticle(@Param("dqArticle") DqArticle dqArticle);
}
