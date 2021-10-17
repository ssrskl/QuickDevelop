package com.maoyan.quickdevelop.system.service.admin.Impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.maoyan.quickdevelop.common.core.domain.DqComment;
import com.maoyan.quickdevelop.common.core.domain.dqabstract.DqStatusDispose;
import com.maoyan.quickdevelop.common.utils.DqStatusDisposrUtils;
import com.maoyan.quickdevelop.common.utils.MyQueryWrapper;
import com.maoyan.quickdevelop.system.mapper.DqCommentMapper;
import com.maoyan.quickdevelop.system.service.IDqCommentService;
import com.maoyan.quickdevelop.system.service.admin.AdminDqCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 猫颜
 * @date 2021/5/31 21:47
 */
@Service
public class AdminDqCommentServiceImpl implements AdminDqCommentService {

    @Autowired
    private DqCommentMapper dqCommentMapper;

    QueryWrapper<DqComment> queryWrapper = new QueryWrapper<>();

    MyQueryWrapper<DqComment> myQueryWrapper = new MyQueryWrapper<>();

    QueryWrapper<? extends DqStatusDispose> queryWrapper1 = new QueryWrapper<>();

    DqStatusDisposrUtils dqStatusDisposrUtils = new DqStatusDisposrUtils();

    /**
     * 根据ID查询评论
     * @param dqCommentId
     * @return
     */
    @Override
    public DqComment selectDqCommentById(Long dqCommentId) {
        DqComment dqComment = (DqComment) DqStatusDisposrUtils.disposeDqArticle(dqCommentMapper.selectById(dqCommentId));
        return dqComment;
    }

    /**
     * 通过发表评论的用户的ID查找评论
     * @param pageNum
     * @param pageSize
     * @param dqUserId
     * @return
     */
    @Override
    public List<DqComment> selectDqCommentByDqUserId(int pageNum, int pageSize, Long dqUserId) {
        PageHelper.startPage(pageNum, pageSize);

        myQueryWrapper.statuseq("comment_userid",String.valueOf(dqUserId));
        List<DqComment> dqComments = dqCommentMapper.selectList(myQueryWrapper);

        //清除queryWrapper的数据，防止给其他的带来影响
        myQueryWrapper.clear();

        return dqComments;
    }

    /**
     * 通过用户名查询他所发表的评论
     * @param pageNum
     * @param pageSize
     * @param dqUsername
     * @return
     */
    @Override
    public List<DqComment> selectDqCommentByDqUsername(int pageNum, int pageSize, String dqUsername) {
        PageHelper.startPage(pageNum, pageSize);
        myQueryWrapper.statuseq("comment_username",dqUsername);
        List<DqComment> dqComments = dqCommentMapper.selectList(myQueryWrapper);
        //清除queryWrapper的数据，防止给其他的带来影响
        myQueryWrapper.clear();
        return dqComments;
    }

    /**
     * 通过文章的ID查找评论
     * @param pageNum
     * @param pageSize
     * @param dqArticleId
     * @return
     */
    @Override
    public List<DqComment> selectDqCommentByDqArticleId(int pageNum, int pageSize, Long dqArticleId) {
        PageHelper.startPage(pageNum, pageSize);
        myQueryWrapper.statuseq("article_id",String.valueOf(dqArticleId));
        List<DqComment> dqComments = dqCommentMapper.selectList(myQueryWrapper);

        //清除queryWrapper的数据，防止给其他的带来影响

        myQueryWrapper.clear();

        return dqComments;
    }

    @Override
    public List<DqComment> selectDqCommentToUserByToUserId(int pageNum, int pageSize,Long toUserId){
        PageHelper.startPage(pageNum, pageSize);
        myQueryWrapper.statuseq("to_user_id", String.valueOf(toUserId));

        List<DqComment> dqComments = dqCommentMapper.selectList(myQueryWrapper);
        myQueryWrapper.clear();
        return dqComments;
    }
    /**
     * 查询给当前用户的评论或回复
     * @return
     */
    @Override
    public List<DqComment> selectDqCommentToMe(int pageNum, int pageSize){
        List<DqComment> dqComments = selectDqCommentToUserByToUserId(pageNum, pageSize, StpUtil.getLoginIdAsLong());
        return dqComments;
    }


    @Override
    public int insertDqComment(DqComment dqComment) {
        int insert = dqCommentMapper.insert(dqComment);
        return insert;
    }

    @Override
    public List<DqComment> selectAllDqComments(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        myQueryWrapper.statuseq();
        List<DqComment> dqComments = dqCommentMapper.selectList(myQueryWrapper);

        //清除queryWrapper的数据，防止给其他的带来影响
        myQueryWrapper.clear();

        return dqComments;
    }

    @Override
    public int deleteDqCommentById(Long dqCommentId) {
        int i = dqCommentMapper.deleteById(dqCommentId);
        return i;
    }

    @Override
    public int updateDqCommentById(Long dqCommentId, DqComment dqComment) {
        int i = dqCommentMapper.updateById(dqComment);
        return i;
    }

}
