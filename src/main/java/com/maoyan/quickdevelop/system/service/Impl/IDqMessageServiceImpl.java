package com.maoyan.quickdevelop.system.service.Impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.maoyan.quickdevelop.common.utils.StringUtils;
import com.maoyan.quickdevelop.common.constant.HttpStatus;
import com.maoyan.quickdevelop.common.core.domain.DqMessage;
import com.maoyan.quickdevelop.common.exception.CustomException;
import com.maoyan.quickdevelop.system.mapper.DqMessageMapper;
import com.maoyan.quickdevelop.system.service.IDqMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
@Slf4j
public class IDqMessageServiceImpl implements IDqMessageService {
  @Autowired
  private DqMessageMapper dqMessageMapper;

  @Override
  public List<DqMessage> selectDqMessages(int pageNum, int pageSize) {
    PageHelper.startPage(pageNum, pageSize);
    List<DqMessage> dqMessages = dqMessageMapper.selectList(null);
    if (dqMessages.isEmpty()) {
      throw new CustomException("还没有任何消息哦!", HttpStatus.NOT_FOUND);
    }
    return dqMessages;
  }

  @Override
  public List<DqMessage> selectNowDqUserDqMessages(int pageNum, int pageSize) {
    PageHelper.startPage(pageNum, pageSize);
    LambdaQueryWrapper<DqMessage> dqMessageLambdaQueryWrapper = new LambdaQueryWrapper<>();
    dqMessageLambdaQueryWrapper.eq(DqMessage::getReUserId,StpUtil.getLoginIdAsLong());
    List<DqMessage> dqMessages = dqMessageMapper.selectList(dqMessageLambdaQueryWrapper);
    if (dqMessages.isEmpty()) {
      throw new CustomException("未查询到此用户的消息", HttpStatus.NOT_FOUND);
    }
    return dqMessages;
  }

  @Override
  public Integer updateNowDqUserMessageIsReadById(Long messageId) {
    // 判断消息的接收者是否是当前用户
    LambdaQueryWrapper<DqMessage> dqMessageLambdaQueryWrapper = new LambdaQueryWrapper<>();
    dqMessageLambdaQueryWrapper.eq(DqMessage::getMessageId, messageId);
    DqMessage dqMessage = dqMessageMapper.selectOne(dqMessageLambdaQueryWrapper);
    dqMessageLambdaQueryWrapper.clear();
    if (StringUtils.isNull(dqMessage)) {
      throw new CustomException("此消息不存在", HttpStatus.NOT_FOUND);
    }
    //  当前用户为接收方
    if (dqMessage.getReUserId() != StpUtil.getLoginIdAsLong()) {
      throw new CustomException("只能修改当前用户信息的状态", HttpStatus.FORBIDDEN);
    }
    // 修改信息的状态
    dqMessage.setMessageIsRead("1");
    int update = dqMessageMapper.update(dqMessage, null);
    if (update <= 0) {
      throw new CustomException("修改失败", HttpStatus.ERROR);
    }
    return update;
  }

  /**
   * 当前用户给指定用户发送消息
   *
   * @param reUserId
   * @return
   */
  @Override
  public Integer sendMessageNowDqUser(Long reUserId, String messageType, Long messageArticleId, Long messageCommentId) {
    DqMessage dqMessage = new DqMessage();
    dqMessage.setSeUserId(StpUtil.getLoginIdAsLong());
    dqMessage.setReUserId(reUserId);
    dqMessage.setMessageType(messageType);
    dqMessage.setMessageArticleId(messageArticleId);
    if (StringUtils.contains(messageType, "2-")) {
      dqMessage.setMessageCommentId(messageCommentId);
    }
    int insert = dqMessageMapper.insert(dqMessage);
    if (insert <= 0) {
      throw new CustomException("发送失败失败", HttpStatus.ERROR);
    }
    return insert;
  }
}
