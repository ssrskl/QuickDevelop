package com.maoyan.quickdevelop.system.service.Impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.maoyan.quickdevelop.common.constant.HttpStatus;
import com.maoyan.quickdevelop.common.core.domain.DqArticle;
import com.maoyan.quickdevelop.common.core.domain.DqType;
import com.maoyan.quickdevelop.common.core.domain.dqabstract.DqStatusDispose;
import com.maoyan.quickdevelop.common.core.domain.postprocessor.DqTypePostProcesser;
import com.maoyan.quickdevelop.common.exception.CustomException;
import com.maoyan.quickdevelop.common.utils.DqStatusDisposrUtils;
import com.maoyan.quickdevelop.common.utils.MyQueryWrapper;
import com.maoyan.quickdevelop.common.utils.annotation.type.QueryType;
import com.maoyan.quickdevelop.system.mapper.DqTypeMapper;
import com.maoyan.quickdevelop.system.mapper.postprocessor.DqTypePostProcessorMapper;
import com.maoyan.quickdevelop.system.service.IDqTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author 猫颜
 * @date 2021/5/28 19:14
 */
@Transactional
@Service
public class IDqTypeServiceImpl implements IDqTypeService {

    @Autowired
    private DqTypeMapper dqTypeMapper;
    @Autowired
    private DqTypePostProcessorMapper dqTypePostProcessorMapper;
    QueryWrapper<DqType> queryWrapper = new QueryWrapper<>();




    @Override
    public DqType selectDqTypeById(Long dqTypeId) {
        DqType dqType = (DqType)DqStatusDisposrUtils.disposeDqArticle(dqTypeMapper.selectById(dqTypeId));
        return dqType;
    }

    @Override
    public DqType selectDqTypeByName(String typeName) {
        MyQueryWrapper<DqType> myQueryWrapper = new MyQueryWrapper<>();
        myQueryWrapper.statuseq("type_name",typeName);
        DqType dqType = dqTypeMapper.selectOne(myQueryWrapper);
        myQueryWrapper.clear();
        return dqType;
    }

//    @Override
//    public int insertDqType(DqType dqType) {
//        int insert = dqTypeMapper.insert(dqType);
//        return insert;
//    }

    @Override
    public List<DqType> selectAllDqTypes(int pageNum, int pageSize,DqType dqType) throws NoSuchFieldException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        MyQueryWrapper<DqType> myQueryWrapper = new MyQueryWrapper<>();
        HashMap<String,Object> queryRules = new LinkedHashMap<>();
        queryRules.put("typeId", QueryType.EQ);
        queryRules.put("typeName",QueryType.LIKE);
        queryRules.put("createManId",QueryType.EQ);
        queryRules.put("updateManId",QueryType.EQ);
        queryRules.put("introduce",QueryType.LIKE);
        queryRules.put("status",QueryType.EQ);
        dqType.setStatus("0");
        myQueryWrapper.queryAll(dqType,queryRules);
        PageHelper.startPage(pageNum, pageSize);
        List<DqType> dqUsers = dqTypeMapper.selectList(myQueryWrapper);
        myQueryWrapper.clear();
        queryRules.clear();
        return dqUsers;
    }

    @Override
    public List<DqTypePostProcesser> selectDqTypePostProcessers(int pageNum, int pageSize, DqTypePostProcesser dqTypePostProcesser) {
        PageHelper.startPage(pageNum, pageSize);
        List<DqTypePostProcesser> dqTypePostProcessers = dqTypePostProcessorMapper.selectAllDqTypePostProcesser(dqTypePostProcesser);
        if (dqTypePostProcessers.isEmpty()) {
            throw new CustomException("未查询到类型", HttpStatus.NOT_FOUND);
        }
        return dqTypePostProcessers;
    }

//    @Override
//    public int deleteDqTypeById(Long dqTypeId) {
//        int i = dqTypeMapper.deleteById(dqTypeId);
//        return i;
//    }
//
//    @Override
//    public int updateDqTypeById(Long dqTypeId, DqType dqType) {
//        int i = dqTypeMapper.updateById(dqType);
//        return i;
//    }
}
