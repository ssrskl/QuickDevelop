package com.maoyan.quickdevelop.system.service;

import com.maoyan.quickdevelop.common.core.domain.postprocessor.DqCollectionPostProcesser;

import java.util.List;

public interface IDqCollectionService {
  List<DqCollectionPostProcesser> commonSelectDqCollectionPostProcesser(int pageNum, int pageSize, DqCollectionPostProcesser dqCollectionPostProcesser);
  int collectDqArticleById(Long dqArticleId);
  int cancelCollectDqArticleById(Long dqArticleId);
}
