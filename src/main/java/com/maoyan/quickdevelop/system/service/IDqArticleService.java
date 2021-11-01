package com.maoyan.quickdevelop.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.maoyan.quickdevelop.common.core.domain.DqArticle;
import com.maoyan.quickdevelop.common.core.domain.DqType;
import com.maoyan.quickdevelop.common.core.domain.postprocessor.DqArticlePostProcesser;
import com.maoyan.quickdevelop.system.domain.DqArticleVO;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @author 猫颜
 * @date 2021/5/27 22:26
 * 文章
 */

public interface IDqArticleService {

  /** 查询 **/
  /**
   * 统一的查询方法
   *
   * @param dqArticlePostProcesser
   * @return
   */
  public List<DqArticlePostProcesser> commonSelectDqArticlePostProcesser(int pageNum, int pageSize, DqArticlePostProcesser dqArticlePostProcesser);

  /**
   * 通过ID查询
   *
   * @param dqArticleId
   * @return
   */
  public DqArticle selectDqArticleById(Long dqArticleId);

  /**
   * 通过标题查询
   *
   * @param title
   * @return
   */
  public DqArticle selectDqArticleByTitle(String title);

  /**
   * 通过类型ID查询
   *
   * @param typeId
   * @return
   */
  public List<DqArticle> selectDqArticlesByTypeId(int pageNum, int pageSize, Long typeId);

  /**
   * 通过作者ID查询
   *
   * @param authorId
   * @return
   */
  public List<DqArticle> selectDqArticlesByAuthorId(int pageNum, int pageSize, Long authorId);

  /**
   * 通过作者用户名查询
   *
   * @param authorUsername
   * @return
   */
  public List<DqArticle> selectDqArticlesByAuthorUsername(int pageNum, int pageSize, String authorUsername);

  /**
   * 增加
   **/
  public int publishDqArticle(DqArticle dqArticle);
  public int publishDqArticle(DqArticleVO dqArticleVO);

  /**
   * 修改
   **/
  public int updateDqArticle(DqArticle dqArticle);

  /**
   * 删除
   **/
  public int deleteDqArticleById(Long articleId);
  /*********************************************/

}
