package com.maoyan.quickdevelop.system.service;

import com.maoyan.quickdevelop.common.core.domain.DqTag;

import java.util.List;

/**
 * @author 猫颜
 * @date 2021年07月28日 下午5:04
 */
public interface IDqTagService {
    /***
     * @Author: 猫颜
     * @Description: 通用查询
     * @DateTime: 下午5:05 2021/7/28
     * @Params:
     * @param dqTag
     * @Return
     * @return java.util.List<com.maoyan.quickdevelop.common.core.domain.DqTag.java>
     */
    public List<DqTag> selectDqTags(int pageNum, int pageSize, DqTag dqTag);

    public DqTag selectDqTagById(Long dqTagId);

    public DqTag selectDqTagByTagName(String dqTagName);
}
