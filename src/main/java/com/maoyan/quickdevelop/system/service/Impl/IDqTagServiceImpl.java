package com.maoyan.quickdevelop.system.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.maoyan.quickdevelop.common.utils.StringUtils;
import com.maoyan.quickdevelop.common.constant.HttpStatus;
import com.maoyan.quickdevelop.common.core.domain.DqTag;
import com.maoyan.quickdevelop.common.exception.CustomException;
import com.maoyan.quickdevelop.system.mapper.DqTagMapper;
import com.maoyan.quickdevelop.system.service.IDqTagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 猫颜
 * @date 2021年07月28日 下午5:07
 */
@Service
@Slf4j
public class IDqTagServiceImpl implements IDqTagService {

    @Autowired
    private DqTagMapper dqTagMapper;

    @Override
    public List<DqTag> selectDqTags(int pageNum, int pageSize, DqTag dqTag) {
//        不执行这一次会出错
        LambdaQueryWrapper<DqTag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotNull(dqTag.getTagId())&&StringUtils.isNotEmpty(dqTag.getTagId().toString()), DqTag::getTagId, dqTag.getTagId())
                    .like(StringUtils.isNotNull(dqTag.getTagName())&&StringUtils.isNotEmpty(dqTag.getTagName()), DqTag::getTagName, dqTag.getTagName());
        PageHelper.startPage(pageNum, pageSize);
        List<DqTag> dqTags = dqTagMapper.selectList(queryWrapper);
        queryWrapper.clear();
        return dqTags;
    }

    @Override
    public DqTag selectDqTagById(Long dqTagId) {
        DqTag dqTag = dqTagMapper.selectById(dqTagId);
        if (StringUtils.isNull(dqTag)){
            throw new CustomException("此标签不存在", HttpStatus.NOT_FOUND);
        }
        return dqTag;
    }

    @Override
    public DqTag selectDqTagByTagName(String dqTagName) {
        LambdaQueryWrapper<DqTag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DqTag::getTagName,dqTagName);
        DqTag dqTag = dqTagMapper.selectOne(queryWrapper);
        queryWrapper.clear();
        if (StringUtils.isNull(dqTag)){
            throw new CustomException("此标签不存在", HttpStatus.NOT_FOUND);
        }
        return dqTag;
    }
}