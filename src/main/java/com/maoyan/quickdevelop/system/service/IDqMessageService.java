package com.maoyan.quickdevelop.system.service;

import com.maoyan.quickdevelop.common.core.domain.DqMessage;

import java.util.List;

public interface IDqMessageService {
  public List<DqMessage> selectDqMessages(int pageNum, int pageSize);
  public List<DqMessage> selectNowDqUserDqMessages(int pageNum, int pageSize);
  public Integer updateNowDqUserMessageIsReadById(Long messageId);
  public Integer sendMessageNowDqUser(Long reUserId, String messageType, Long messageArticleId, Long messageCommentId);
}
