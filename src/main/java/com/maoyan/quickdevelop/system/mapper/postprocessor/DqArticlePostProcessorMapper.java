package com.maoyan.quickdevelop.system.mapper.postprocessor;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maoyan.quickdevelop.common.core.domain.postprocessor.DqArticlePostProcesser;
import com.maoyan.quickdevelop.system.domain.queryvo.DqArticleQueryVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 猫颜
 * @date 2021年08月01日 上午7:14
 */
@Mapper
public interface DqArticlePostProcessorMapper extends BaseMapper<DqArticlePostProcesser> {
    //    @Select(value = "select * ,(select count(*) from dq_comment where article_id = dq_article.article_id) as comment_num from dq_article inner join dq_user du on dq_article.author_id = du.user_id inner join dq_type dt on dq_article.type_id = dt.type_id where dq_article.status=0 and dt.status=0 and du.status=0;")
    public List<DqArticlePostProcesser> selectAllDqArticlePostProcesser(@Param("dqArticleQueryVO") DqArticleQueryVO dqArticleQueryVO);
}
