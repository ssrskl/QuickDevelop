package com.maoyan.quickdevelop.system.service;

import com.maoyan.quickdevelop.common.core.domain.DqArticleTag;

import java.util.List;

/**
 * @author 猫颜
 * @date 2021年07月28日 下午5:04
 */
public interface IDqArticleTagService {

    List<DqArticleTag> selectDqArticleTags(int pageNum, int pageSize, DqArticleTag dqArticleTag);
}
