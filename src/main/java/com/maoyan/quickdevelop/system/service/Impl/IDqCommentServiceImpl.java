package com.maoyan.quickdevelop.system.service.Impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.maoyan.quickdevelop.common.constant.HttpStatus;
import com.maoyan.quickdevelop.common.core.domain.DqArticle;
import com.maoyan.quickdevelop.common.core.domain.DqComment;
import com.maoyan.quickdevelop.common.core.domain.DqUser;
import com.maoyan.quickdevelop.common.core.domain.postprocessor.DqCommentPostProcesser;
import com.maoyan.quickdevelop.common.exception.CustomException;
import com.maoyan.quickdevelop.common.utils.DateUtils;
import com.maoyan.quickdevelop.common.utils.MyQueryWrapper;
import com.maoyan.quickdevelop.common.utils.StringUtils;
import com.maoyan.quickdevelop.common.utils.mail.DqMailUtil;
import com.maoyan.quickdevelop.system.domain.DqCommentVO;
import com.maoyan.quickdevelop.system.mapper.DqArticleMapper;
import com.maoyan.quickdevelop.system.mapper.DqCommentMapper;
import com.maoyan.quickdevelop.system.mapper.DqUserMapper;
import com.maoyan.quickdevelop.system.mapper.postprocessor.DqCommentPostProcessorMapper;
import com.maoyan.quickdevelop.system.service.IDqCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;

import java.util.List;

/**
 * @author 猫颜
 * @date 2021/5/31 21:47
 */
@Transactional
@Service
public class IDqCommentServiceImpl implements IDqCommentService {

  @Autowired
  private DqCommentMapper dqCommentMapper;
  @Autowired
  private DqUserMapper dqUserMapper;
  @Autowired
  private DqArticleMapper dqArticleMapper;
  @Autowired
  private DqCommentPostProcessorMapper dqCommentPostProcessorMapper;
  @Autowired
  private DqMailUtil dqMailUtil;
  @Autowired
  private TemplateEngine templateEngine;

  QueryWrapper<DqComment> queryWrapper = new QueryWrapper<>();
  QueryWrapper<DqArticle> articleQueryWrapper = new QueryWrapper<>();
  QueryWrapper<DqUser> userQueryWrapper = new QueryWrapper<>();

  @Override
  public List<DqCommentPostProcesser> commonSelectDqCommentPostProcesser(int pageNum, int pageSize, DqComment dqComment) {
    PageHelper.startPage(pageNum, pageSize);
    List<DqCommentPostProcesser> dqCommentPostProcessers = dqCommentPostProcessorMapper.commonSelectDqCommentPostProcesser(dqComment);
    if (StringUtils.isEmpty(dqCommentPostProcessers)) {
      throw new CustomException("未查询到评论", HttpStatus.NOT_FOUND);
    }
    return dqCommentPostProcessers;
  }

  /**
   * 根据ID查询评论
   *
   * @param dqCommentId
   * @return
   */
  @Override
  public DqComment selectDqCommentById(Long dqCommentId) {
    queryWrapper.eq("comment_id", dqCommentId).eq("status", "0");
    DqComment dqComment = dqCommentMapper.selectOne(queryWrapper);
    queryWrapper.clear();
    if (StringUtils.isNull(dqComment)) {
      throw new CustomException("未查询到评论", HttpStatus.NOT_FOUND);
    }
//        DqComment dqComment = (DqComment) DqStatusDisposrUtils.disposeDqArticle(dqCommentMapper.selectById(dqCommentId));
    return dqComment;
  }

  /**
   * 通过发表评论的用户的ID查找评论
   *
   * @param pageNum
   * @param pageSize
   * @param dqUserId
   * @return
   */
  @Override
  public List<DqComment> selectDqCommentByDqUserId(int pageNum, int pageSize, Long dqUserId) {
    userQueryWrapper.lambda().eq(DqUser::getUserId, dqUserId).eq(DqUser::getStatus, "0");
    DqUser dqUser = dqUserMapper.selectOne(userQueryWrapper);
    userQueryWrapper.clear();
    if (StringUtils.isNull(dqUser)) {
      throw new CustomException("此用户不存在或被封禁", HttpStatus.NOT_FOUND);
    }
    queryWrapper.eq("comment_userid", dqUserId).eq("status", "0");
    PageHelper.startPage(pageNum, pageSize);
    List<DqComment> dqComments = dqCommentMapper.selectList(queryWrapper);
    //清除queryWrapper的数据，防止给其他的带来影响
    queryWrapper.clear();
    if (dqComments.isEmpty()) {
      throw new CustomException("此用户没有发表过评论", HttpStatus.NOT_FOUND);
    }
    return dqComments;
  }

//    /**
//     * 通过用户名查询他所发表的评论
//     * @param pageNum
//     * @param pageSize
//     * @param dqUsername
//     * @return
//     */
//    @Override
//    public List<DqComment> selectDqCommentByDqUsername(int pageNum, int pageSize, String dqUsername) {
//        PageHelper.startPage(pageNum, pageSize);
//        MyQueryWrapper<DqComment> myQueryWrapper = new MyQueryWrapper<>();
//        myQueryWrapper.statuseq("comment_username",dqUsername);
//        List<DqComment> dqComments = dqCommentMapper.selectList(myQueryWrapper);
//        //清除queryWrapper的数据，防止给其他的带来影响
//        myQueryWrapper.clear();
//        return dqComments;
//    }

  /**
   * 通过文章的ID查找所有根评论
   *
   * @param pageNum
   * @param pageSize
   * @param dqArticleId
   * @return
   */
  @Override
  public List<DqComment> selectDqCommentByDqArticleId(int pageNum, int pageSize, Long dqArticleId) {
    articleQueryWrapper.lambda().eq(DqArticle::getArticleId, dqArticleId).eq(DqArticle::getStatus, "0");
    DqArticle dqArticle = dqArticleMapper.selectOne(articleQueryWrapper);
    if (StringUtils.isNull(dqArticle)) {
      articleQueryWrapper.clear();
      throw new CustomException("此文章不存在或被封禁", HttpStatus.NOT_FOUND);
    }
    articleQueryWrapper.clear();
    queryWrapper.lambda().eq(DqComment::getArticleId, dqArticleId).eq(DqComment::getRootId, "0").eq(DqComment::getStatus, "0");
    PageHelper.startPage(pageNum, pageSize);
    List<DqComment> dqComments = dqCommentMapper.selectList(queryWrapper);
    queryWrapper.clear();
    if (dqComments.isEmpty()) {
      throw new CustomException("此文章没有评论", HttpStatus.NOT_FOUND);
    }
    return dqComments;
  }

  /**
   * 根据根评论查询回复（根据id查询根评论下的所有回复）
   *
   * @param pageNum
   * @param pageSize
   * @param rootId
   * @return java.util.List<com.maoyan.quickdevelop.common.core.domain.DqComment>
   * @author 猫颜
   * @date 2021/7/24 上午7:45
   */
  @Override
  public List<DqComment> selectDqCommentsByRootId(int pageNum, int pageSize, Long rootId) {
    queryWrapper.lambda().eq(DqComment::getCommentId, rootId).eq(DqComment::getStatus, "0");
    DqComment rootDqComment = dqCommentMapper.selectOne(queryWrapper);
    queryWrapper.clear();
    if (StringUtils.isNull(rootDqComment)) {
      throw new CustomException("根评论不存在", HttpStatus.NOT_FOUND);
    }

    queryWrapper.lambda().eq(DqComment::getRootId, rootId).eq(DqComment::getStatus, "0");
    PageHelper.startPage(pageNum, pageSize);
    List<DqComment> dqComments = dqCommentMapper.selectList(queryWrapper);
    queryWrapper.clear();
    if (dqComments.isEmpty()) {
      throw new CustomException("此根评论下没有回复", HttpStatus.NOT_FOUND);
    }

    return dqComments;
  }


  @Override
  public List<DqComment> selectDqCommentToUserByToUserId(int pageNum, int pageSize, Long toUserId) {
    MyQueryWrapper<DqComment> myQueryWrapper = new MyQueryWrapper<>();
    myQueryWrapper.statuseq("to_user_id", String.valueOf(toUserId));
    PageHelper.startPage(pageNum, pageSize);
    List<DqComment> dqComments = dqCommentMapper.selectList(myQueryWrapper);
    myQueryWrapper.clear();
    return dqComments;
  }

  /**
   * 查询给当前用户的评论或回复
   *
   * @return
   */
  @Override
  public List<DqComment> selectDqCommentToMe(int pageNum, int pageSize) {
    DqUser dqUser = dqUserMapper.selectOne(userQueryWrapper);
    userQueryWrapper.clear();
    if (StringUtils.isNull(dqUser)) {
      throw new CustomException("此用户不存在或被封禁", HttpStatus.NOT_FOUND);
    }
    PageHelper.startPage(pageNum, pageSize);
    queryWrapper.lambda().eq(DqComment::getToUserId, StpUtil.getLoginIdAsLong());
    List<DqComment> dqComments = dqCommentMapper.selectList(queryWrapper);
    queryWrapper.clear();
    return dqComments;
  }


  /**
   * 发表评论
   *
   * @param dqCommentVO
   * @return int
   * @author 猫颜
   * @date 上午9:17
   */
  @Override
  public int publishDqComment(DqCommentVO dqCommentVO) {
    // 通过ID获取文章信息
    DqArticle dqArticle = dqArticleMapper.selectById(dqCommentVO.getArticleId());
    // 判断文章是否存在
    if (dqArticle == null) {
      throw new CustomException("此文章不存在", HttpStatus.ERROR);
    }
    if (StringUtils.equals("0", dqArticle.getStatus())) {
      throw new CustomException("该文章已被锁定", HttpStatus.ERROR);
    }
    // 构建评论实体类
    DqComment newDqComment = new DqComment();
    newDqComment.setContent(dqCommentVO.getContent());
    newDqComment.setCommentUserId(StpUtil.getLoginIdAsLong());
    newDqComment.setArticleId(dqCommentVO.getArticleId());
    newDqComment.setReplyId(dqCommentVO.getReplyId());
    newDqComment.setStatus("1");
    newDqComment.setDeleteFlag(0L);
    newDqComment.setCreateTime(DateUtils.getNowDate());
    int insert = 0;
    // 判断是评论还是回复
    if (dqCommentVO.getReplyId() == 0) {
      // 为评论
      newDqComment.setRootId(0L);
      newDqComment.setToUserId(dqArticle.getAuthorId());
      insert = dqCommentMapper.insert(newDqComment);

    } else {
      // 为回复,获得上级回复
      LambdaQueryWrapper<DqComment> dqCommentLambdaQueryWrapper = new LambdaQueryWrapper<>();
      dqCommentLambdaQueryWrapper.eq(DqComment::getCommentId, dqCommentVO.getReplyId());
      DqComment previousDqComment = dqCommentMapper.selectOne(dqCommentLambdaQueryWrapper);
      if (StringUtils.isNull(previousDqComment)) {
        throw new CustomException("回复的评论不存在", HttpStatus.NOT_FOUND);
      }
      newDqComment.setToUserId(previousDqComment.getCommentUserId());
      newDqComment.setArticleId(previousDqComment.getArticleId());
      newDqComment.setRootId(previousDqComment.getRootId());
      insert = dqCommentMapper.insert(newDqComment);
    }
    return insert;
//    // 获取上级评论
//
//    /** 参数补充**/
//    DqUser dqUser = dqUserMapper.selectById(StpUtil.getLoginIdAsLong());
////    dqComment.setCommentUsername(dqUser.getUserName());
////    dqComment.setCommentUserNickName(dqUser.getNickName());
////    dqComment.setCommentUserAvatar(dqUser.getAvatar());
//    dqComment.setCreateTime(DateUtils.getNowDate());
//    // 判断是评论还是回复
//    if (dqComment.getReplyId() != 0) {
//      //回复
////      dqComment.setCommentType("2");
//      //根据回复来查原来的评论或回复
//      // MyQueryWrapper<DqComment> myQueryWrapper = new MyQueryWrapper<>();
//      // myQueryWrapper.eq("comment_id",dqComment.getReplyId());
//      // 查询当前回复所回复的回复ID
//      LambdaQueryWrapper<DqComment> myQueryWrapper = new LambdaQueryWrapper<>();
//      myQueryWrapper.eq(DqComment::getCommentId, dqComment.getReplyId());
//      DqComment oldDqComments = dqCommentMapper.selectOne(myQueryWrapper);
//      if (oldDqComments == null) {
//        throw new CustomException("此评论不存在", HttpStatus.ERROR);
//      }
//      dqComment.setToUserId(oldDqComments.getCommentUserId());
//      DqUser toUser = dqUserMapper.selectById(dqComment.getToUserId());
////      dqComment.setToUsername(toUser.getUserName());
////      dqComment.setToNickname(toUser.getNickName());
////      dqComment.setToUserAvatar(toUser.getAvatar());
//      // 判断所回复的回复是否为评论
//      if (oldDqComments.getRootId() == 0) {
//        dqComment.setRootId(oldDqComments.getCommentId());
//      } else {
//        dqComment.setRootId(oldDqComments.getRootId());
//      }
//      myQueryWrapper.clear();
//    } else {
//      //评论
////      dqComment.setCommentType("1");
//      dqComment.setToUserId(dqArticle.getAuthorId());
//      DqUser toUser = dqUserMapper.selectById(dqArticle.getAuthorId());
////      dqComment.setToUsername(toUser.getUserName());
////      dqComment.setToNickname(toUser.getNickName());
////      dqComment.setToUserAvatar(toUser.getAvatar());
//      dqComment.setRootId(0L);
//    }
//    int insert = dqCommentMapper.insert(dqComment);
//    if (insert <= 0) {
//      throw new CustomException("发表评论失败", HttpStatus.ERROR);
//    }
//    // 发表评论成功
//    // 设置邮箱(发送邮件可以放到消息队列中)
//    DqUser toDqUser = dqUserMapper.selectById(dqComment.getToUserId());
//    // 邮件正文
//    Context context = new Context();
////    context.setVariable("toDqUser",toDqUser.getNickName());
////    context.setVariable("username",dqUser.getNickName());
//    context.setVariable("replyContent",dqComment.getContent());
//    context.setVariable("articleId",dqArticle.getArticleId());
//    String emailContent = templateEngine.process("emailTemplate",context);
//    try {
//      dqMailUtil.sendMailByThymeleaf("有人回复了您",toDqUser.getEmail(),
//          emailContent);
//    } catch (MessagingException e) {
//      throw new CustomException("邮件发送失败",HttpStatus.ERROR);
//    }
  }

//    @Override
//    public List<DqComment> selectAllDqComments(int pageNum, int pageSize) {
//        PageHelper.startPage(pageNum, pageSize);
//        myQueryWrapper.statuseq();
//        List<DqComment> dqComments = dqCommentMapper.selectList(myQueryWrapper);
//
//        //清除queryWrapper的数据，防止给其他的带来影响
//        myQueryWrapper.clear();
//
//        return dqComments;
//    }

  /**
   * (直接删除的话，后面还会有很多的评论等等，所以采用逻辑删除)
   *
   * @param dqCommentId
   * @return
   */
  @Override
  public int deleteDqCommentById(Long dqCommentId) {
    // 查询评论或者回复
    LambdaQueryWrapper<DqComment> dqCommentLambdaQueryWrapper = new LambdaQueryWrapper<>();
    dqCommentLambdaQueryWrapper.eq(DqComment::getCommentId, dqCommentId)
        .eq(DqComment::getDeleteFlag, 0);
    DqComment dqComment = dqCommentMapper.selectById(dqCommentId);
    // 不存在或者被删除
    if (StringUtils.isNull(dqComment)) {
      throw new CustomException("此评论不存在", HttpStatus.ERROR);
    }
    // 判断是否是自己的评论
    Long dqCommentUserId = dqComment.getCommentUserId();
    if (StpUtil.getLoginIdAsLong() != dqCommentUserId) {
      throw new CustomException("不能删除他人的评论", HttpStatus.FORBIDDEN);
    }
    // 删除评论，修改删除标志
    dqComment.setDeleteFlag(dqCommentId);
    dqComment.setStatus("0");
    int i = dqCommentMapper.updateById(dqComment);
//    int i = dqCommentMapper.deleteById(dqCommentId);
    if (i <= 0) {
      throw new CustomException("删除失败", HttpStatus.FORBIDDEN);
    }
    return i;
  }

//    @Override
//    public int updateDqCommentById(Long dqCommentId, DqComment dqComment) {
//        int i = dqCommentMapper.updateById(dqComment);
//        return i;
//    }

}
