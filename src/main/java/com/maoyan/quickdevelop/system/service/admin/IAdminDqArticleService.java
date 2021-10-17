package com.maoyan.quickdevelop.system.service.admin;

import com.maoyan.quickdevelop.common.core.domain.DqArticle;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface IAdminDqArticleService {
    /** 查询 **/

    /**
     * 统一的查询方法
     * @param dqArticle
     * @return
     */
    public List<DqArticle> selectAllDqArticle(int pageNum, int pageSize,DqArticle dqArticle) throws NoSuchFieldException, InvocationTargetException, NoSuchMethodException, IllegalAccessException;

    /**
     * 通过ID查询
     * @param dqArticleId
     * @return
     */
    public DqArticle selectDqArticleById(Long dqArticleId);

    /**
     * 通过标题查询
     * @param title
     * @return
     */
    public DqArticle selectDqArticleByTitle(String title);

    /**
     * 通过类型ID查询
     * @param typeId
     * @return
     */
    public List<DqArticle> selectDqArticlesByTypeId(int pageNum, int pageSize,Long typeId);

    /**
     * 通过作者ID查询
     * @param authorId
     * @return
     */
    public List<DqArticle> selectDqArticlesByAuthorId(int pageNum, int pageSize,Long authorId);

    /**
     * 通过作者用户名查询
     * @param authorUsername
     * @return
     */
    public List<DqArticle> selectDqArticlesByAuthorUsername(int pageNum, int pageSize,String authorUsername);

    /** 增加 **/
    public int insertDqArticle(DqArticle dqArticle);

    /** 修改 **/
    public int updateDqArticle(DqArticle dqArticle);
    /** 删除 **/
    public int deleteDqArticleById(Long articleId);
}
