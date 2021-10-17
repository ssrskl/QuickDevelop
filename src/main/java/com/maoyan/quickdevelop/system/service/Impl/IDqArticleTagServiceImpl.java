package com.maoyan.quickdevelop.system.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.maoyan.quickdevelop.common.utils.StringUtils;
import com.maoyan.quickdevelop.common.constant.HttpStatus;
import com.maoyan.quickdevelop.common.core.domain.DqArticleTag;
import com.maoyan.quickdevelop.common.exception.CustomException;
import com.maoyan.quickdevelop.system.mapper.DqArticleTagMapper;
import com.maoyan.quickdevelop.system.service.IDqArticleTagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 猫颜
 * @date 2021年07月28日 下午7:40
 */
@Service
@Slf4j
@Transactional
public class IDqArticleTagServiceImpl implements IDqArticleTagService {

    @Autowired
    private DqArticleTagMapper dqArticleTagMapper;


    @Override
    public List<DqArticleTag> selectDqArticleTags(int pageNum, int pageSize, DqArticleTag dqArticleTag) {
        QueryWrapper<DqArticleTag> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(StringUtils.isNotNull(dqArticleTag.getArticleTagId()),DqArticleTag::getArticleTagId,dqArticleTag.getArticleTagId())
                .eq(StringUtils.isNotNull(dqArticleTag.getArticleId()),DqArticleTag::getArticleId,dqArticleTag.getArticleId())
                .eq(StringUtils.isNotNull(dqArticleTag.getTagId()),DqArticleTag::getTagId,dqArticleTag.getTagId());
        PageHelper.startPage(pageNum, pageSize);
        List<DqArticleTag> dqArticleTags = dqArticleTagMapper.selectList(queryWrapper);
        queryWrapper.clear();
        if (dqArticleTags.isEmpty()){
            throw new CustomException("未查询到", HttpStatus.NOT_FOUND);
        }
        return dqArticleTags;
    }


}