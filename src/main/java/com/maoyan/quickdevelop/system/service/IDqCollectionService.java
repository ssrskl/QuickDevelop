package com.maoyan.quickdevelop.system.service;

import com.maoyan.quickdevelop.common.core.domain.postprocessor.DqCollectionPostProcesser;
import com.maoyan.quickdevelop.system.domain.queryvo.DqArticleQueryVO;
import com.maoyan.quickdevelop.system.domain.queryvo.DqCollectionQueryVO;

import java.util.List;

public interface IDqCollectionService {
  List<DqCollectionPostProcesser> commonSelectDqCollectionPostProcesser(int pageNum, int pageSize, DqCollectionQueryVO dqCollectionQueryVO);
  int collectDqArticleById(Long dqArticleId);
  int cancelCollectDqArticleById(Long dqArticleId);
}
