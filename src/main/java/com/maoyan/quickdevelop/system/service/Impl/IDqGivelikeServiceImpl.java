package com.maoyan.quickdevelop.system.service.Impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.maoyan.quickdevelop.common.utils.StringUtils;
import com.maoyan.quickdevelop.common.constant.HttpStatus;
import com.maoyan.quickdevelop.common.core.domain.DqLikeStatusEnum;
import com.maoyan.quickdevelop.common.core.domain.DqUser;
import com.maoyan.quickdevelop.common.exception.CustomException;
import com.maoyan.quickdevelop.common.utils.redis.RedisKeyUtils;
import com.maoyan.quickdevelop.system.mapper.DqUserMapper;
import com.maoyan.quickdevelop.system.service.IDqGivelikeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author 猫颜
 * @date 2021年07月24日 下午3:19
 * 类的作用：TODO
 */
@Transactional
@Service
@Slf4j
public class IDqGivelikeServiceImpl implements IDqGivelikeService {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private DqUserMapper dqUserMapper;
    QueryWrapper<DqUser> queryWrapper = new QueryWrapper<>();
    String key = "";
    @Override
    public int giveALike(Long dqArticleId) {
        Long dqUserId = StpUtil.getLoginIdAsLong();
        key = RedisKeyUtils.getLikedKey(dqArticleId, dqUserId);
        Object oldObject = redisTemplate.opsForHash().get(RedisKeyUtils.MAP_KEY_USER_LIKED, key);
        if (StringUtils.isNotNull(oldObject)&&(Integer)oldObject==1){
            throw new CustomException("不能重复点赞", HttpStatus.ERROR);
        }
        //设置状态
        redisTemplate.opsForHash().put(RedisKeyUtils.MAP_KEY_USER_LIKED,key, DqLikeStatusEnum.LIKE.getCode());
        //数量增加
        Object countLikes = redisTemplate.opsForHash().get(RedisKeyUtils.MAP_KEY_ARTICLE_LIKED_COUNT,
                dqArticleId);
        if (StringUtils.isNull(countLikes)){
            redisTemplate.opsForHash().put(RedisKeyUtils.MAP_KEY_ARTICLE_LIKED_COUNT,
                    dqArticleId,1);
        }else {
            redisTemplate.opsForHash().increment(RedisKeyUtils.MAP_KEY_ARTICLE_LIKED_COUNT,dqArticleId,1);
        }
        Object object = redisTemplate.opsForHash().get(RedisKeyUtils.MAP_KEY_USER_LIKED, key);
        if (StringUtils.isNull(object)){
            throw new CustomException("点赞失败", HttpStatus.ERROR);
        }
        return DqLikeStatusEnum.LIKE.getCode();
    }

    @Override
    public int unlike(Long dqArticleId) {
        Long dqUserId = StpUtil.getLoginIdAsLong();
        key = RedisKeyUtils.getLikedKey(dqArticleId, dqUserId);
        Object oldStatus = redisTemplate.opsForHash().get(RedisKeyUtils.MAP_KEY_USER_LIKED, key);
        if (StringUtils.isNull(oldStatus)){
            throw new CustomException("此用户还未点赞",HttpStatus.ERROR);
        }
        //点赞过了,取消点赞
//        redisTemplate.opsForHash().put(RedisKeyUtils.MAP_KEY_USER_LIKED,key, DqLikeStatusEnum.UNLIKE.getCode());
        Long delete = redisTemplate.opsForHash().delete(RedisKeyUtils.MAP_KEY_USER_LIKED, key);
        if (delete != 1){
            throw new CustomException("删除失败",HttpStatus.ERROR);
        }
        //删除成功
        redisTemplate.opsForHash().increment(RedisKeyUtils.MAP_KEY_ARTICLE_LIKED_COUNT,dqArticleId,-1);
        return DqLikeStatusEnum.UNLIKE.getCode();
    }

    @Override
    public int checkStatusForDqArticle(Long dqArticleId) {
        Long dqUserId = StpUtil.getLoginIdAsLong();
        key = RedisKeyUtils.getLikedKey(dqArticleId, dqUserId);
        if (redisTemplate.opsForHash().hasKey(RedisKeyUtils.MAP_KEY_USER_LIKED, key)){
            //有点赞
            return DqLikeStatusEnum.LIKE.getCode();
        }else {
            return DqLikeStatusEnum.UNLIKE.getCode();
        }
    }

    @Override
    public int getLikeNumberInDqArticle(Long dqArticleId) {
        Integer countLikes = (Integer)redisTemplate.opsForHash().get(RedisKeyUtils.MAP_KEY_ARTICLE_LIKED_COUNT,
                dqArticleId);
        if (StringUtils.isNull(countLikes)){
            return 0;
        }
        return countLikes;
    }

    @Override
    public List<DqUser> likedDqUserInfoInDqArticle(int pageNum, int pageSize, Long dqArticleId) {
        Set<String> keys = redisTemplate.opsForHash().keys(RedisKeyUtils.MAP_KEY_USER_LIKED);
        //将set转换为stream
        List<Long> dqUserIds = keys.stream()
                .filter(s -> s.toString().startsWith(String.valueOf(dqArticleId)))
                .distinct()
                .map(ss -> ss.split("::"))
                .map(s_dqUserId -> s_dqUserId[1])
                .map(x->Long.valueOf(x))
                .collect(Collectors.toList());
        if (dqUserIds.isEmpty()){
            throw new CustomException("此文章没有点赞",HttpStatus.NOT_FOUND);
        }
        PageHelper.startPage(pageNum, pageSize);
        List<DqUser> dqUsers = dqUserMapper.selectBatchIds(dqUserIds);
        return dqUsers;
    }


}