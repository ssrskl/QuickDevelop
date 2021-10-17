package com.maoyan.quickdevelop.system.service.Impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.maoyan.quickdevelop.common.utils.StringUtils;
import com.maoyan.quickdevelop.common.constant.HttpStatus;
import com.maoyan.quickdevelop.common.core.domain.DqArticle;
import com.maoyan.quickdevelop.common.core.domain.DqComment;
import com.maoyan.quickdevelop.common.core.domain.DqUser;
import com.maoyan.quickdevelop.common.exception.CustomException;
import com.maoyan.quickdevelop.system.mapper.DqArticleMapper;
import com.maoyan.quickdevelop.system.mapper.DqCommentMapper;
import com.maoyan.quickdevelop.system.mapper.DqTypeMapper;
import com.maoyan.quickdevelop.system.mapper.DqUserMapper;
import com.maoyan.quickdevelop.system.service.IDqNumberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 猫颜
 * @date 2021年07月24日 上午8:50
 * 类的作用：TODO
 */
@Transactional
@Service
@Slf4j
public class IDqNumberServiceImpl implements IDqNumberService {
    @Autowired
    private DqUserMapper dqUserMapper;
    @Autowired
    private DqCommentMapper dqCommentMapper;
    @Autowired
    private DqTypeMapper dqTypeMapper;
    @Autowired
    private DqArticleMapper dqArticleMapper;

    QueryWrapper<DqComment> queryWrapper = new QueryWrapper<>();
    QueryWrapper<DqArticle> articleQueryWrapper = new QueryWrapper<>();
    @Override
    public int allDqArticleNumber() {
        Integer integer = dqArticleMapper.selectCount(null);
        return integer;
    }

    @Override
    public int allCommentNumberInDqArticleById(Long dqArticleId) {
        articleQueryWrapper.lambda().eq(DqArticle::getArticleId,dqArticleId);
        DqArticle dqArticle = dqArticleMapper.selectOne(articleQueryWrapper);
        articleQueryWrapper.clear();
        if (StringUtils.isNull(dqArticle)){
            throw new CustomException("此文章不存在", HttpStatus.NOT_FOUND);
        }
        //获取文章下所有评论和回复的数量（要保证评论表中article_id不能为空）
        queryWrapper.lambda().eq(DqComment::getArticleId,dqArticleId);
        Integer integer = dqCommentMapper.selectCount(queryWrapper);
        queryWrapper.clear();
        return integer;
    }

    @Override
    public int allSonCommentNumInDqCommentByRootId(Long rootId) {
        queryWrapper.lambda().eq(DqComment::getCommentId,rootId);
        DqComment dqComment = dqCommentMapper.selectOne(queryWrapper);
        if (StringUtils.isNull(dqComment)){
            throw new CustomException("根评论不存在", HttpStatus.NOT_FOUND);
        }
        queryWrapper.clear();
        queryWrapper.lambda().eq(DqComment::getRootId,rootId);
        Integer integer = dqCommentMapper.selectCount(queryWrapper);
        queryWrapper.clear();
        return integer;
    }

    @Override
    public int allTypeNumber() {
        Integer integer = dqTypeMapper.selectCount(null);
        return integer;
    }

    @Override
    public int allUsersNum() {
        Integer integer = dqUserMapper.selectCount(null);
        return integer;
    }

    @Override
    public int dqArticleNumByAuthorId(Long dqUserId) {
        LambdaQueryWrapper<DqArticle> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DqArticle::getAuthorId, dqUserId);
        Integer integer = dqArticleMapper.selectCount(queryWrapper);
        queryWrapper.clear();
        return integer;
    }

    @Override
    public int dqCommentNumByAuthorId(Long dqUserId) {
        LambdaQueryWrapper<DqComment> queryWrapper = new LambdaQueryWrapper<>();
        DqUser dqUser = dqUserMapper.selectById(dqUserId);
        if (StringUtils.isNull(dqUser)){
            throw new CustomException("此用户不存在");
        }
        queryWrapper.eq(DqComment::getCommentUserId,dqUserId);
        Integer integer = dqCommentMapper.selectCount(queryWrapper);
        return integer;
    }

    @Override
    public int dqArticleNumByNowDqUser() {
        LambdaQueryWrapper<DqArticle> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DqArticle::getAuthorId,StpUtil.getLoginIdAsLong());
        Integer integer = dqArticleMapper.selectCount(queryWrapper);
        return integer;
    }

    @Override
    public int dqCommentNumByNowDqUser() {
        LambdaQueryWrapper<DqComment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DqComment::getCommentUserId,StpUtil.getLoginIdAsLong());
        Integer integer = dqCommentMapper.selectCount(queryWrapper);
        return integer;
    }


}