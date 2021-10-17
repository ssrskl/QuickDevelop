package com.maoyan.quickdevelop.system.service;

import com.maoyan.quickdevelop.common.core.domain.DqType;
import com.maoyan.quickdevelop.common.core.domain.DqUser;
import com.maoyan.quickdevelop.common.core.domain.postprocessor.DqArticlePostProcesser;
import com.maoyan.quickdevelop.common.core.domain.postprocessor.DqTypePostProcesser;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @author 猫颜
 * @date 2021/5/27 22:26
 * 类别
 */

public interface IDqTypeService {

    /**
     * 根据ID查询类型
     * @param dqTypeId
     * @return
     */
    public DqType selectDqTypeById(Long dqTypeId);

    /**
     * 根据名称查询类别
     * @param typeName
     * @return
     */
    public DqType selectDqTypeByName(String typeName);

//    /**
//     * 添加一个类型
//     * @param dqType
//     * @return
//     */
//    public int insertDqType(DqType dqType);

    /**
     * 获取全部的类型
     * @param pageNum 查询的页数
     * @param pageSize 每页的大小
     * @return
     */
    public List<DqType> selectAllDqTypes(int pageNum, int pageSize,DqType dqType) throws NoSuchFieldException, InvocationTargetException, NoSuchMethodException, IllegalAccessException;

//    /**
//     * 根据Id删除类型
//     * @param dqTypeId
//     * @return
//     */
//    public int deleteDqTypeById(Long dqTypeId);
//
//
//    /**
//     * 根据ID更改类型
//     * @param dqTypeId
//     * @param dqType
//     * @return
//     */
//    public int updateDqTypeById(Long dqTypeId, DqType dqType);

    /**==========================================增强版==============================================**/
    public List<DqTypePostProcesser> selectDqTypePostProcessers(int pageNum, int pageSize, DqTypePostProcesser dqTypePostProcesser);
}
