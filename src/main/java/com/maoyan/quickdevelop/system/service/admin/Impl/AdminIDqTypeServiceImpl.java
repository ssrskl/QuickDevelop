package com.maoyan.quickdevelop.system.service.admin.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.maoyan.quickdevelop.common.core.domain.DqType;
import com.maoyan.quickdevelop.common.core.domain.DqUser;
import com.maoyan.quickdevelop.common.utils.DqStatusDisposrUtils;
import com.maoyan.quickdevelop.common.utils.MyQueryWrapper;
import com.maoyan.quickdevelop.common.utils.annotation.type.QueryType;
import com.maoyan.quickdevelop.system.mapper.DqTypeMapper;
import com.maoyan.quickdevelop.system.service.IDqTypeService;
import com.maoyan.quickdevelop.system.service.admin.IAdminIDqTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author 猫颜
 * @date 2021/5/28 19:14
 */
@Service
public class AdminIDqTypeServiceImpl implements IAdminIDqTypeService {

    @Autowired
    private DqTypeMapper dqTypeMapper;

    QueryWrapper<DqType> queryWrapper = new QueryWrapper<>();

    MyQueryWrapper<DqType> myQueryWrapper = new MyQueryWrapper<>();


    @Override
    public DqType selectDqTypeById(Long dqTypeId) {
        DqType dqType = dqTypeMapper.selectById(dqTypeId);
        //DqType dqType = (DqType)DqStatusDisposrUtils.disposeDqArticle(dqTypeMapper.selectById(dqTypeId));
        return dqType;
    }

    @Override
    public DqType selectDqTypeByName(String typeName) {
//        myQueryWrapper.statuseq("type_name",typeName);
        myQueryWrapper.eq("type_name",typeName);
        DqType dqType = dqTypeMapper.selectOne(myQueryWrapper);
        myQueryWrapper.clear();
        return dqType;
    }

    @Override
    public int insertDqType(DqType dqType) {
        int insert = dqTypeMapper.insert(dqType);
        return insert;
    }

    @Override
    public List<DqType> selectAllDqTypes(int pageNum, int pageSize,DqType dqType) throws NoSuchFieldException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        PageHelper.startPage(pageNum, pageSize);
        HashMap<String,Object> queryRules = new LinkedHashMap<>();
        queryRules.put("typeId", QueryType.EQ);
        queryRules.put("typeName",QueryType.LIKE);
        queryRules.put("createManId",QueryType.EQ);
        queryRules.put("updateManId",QueryType.EQ);
        queryRules.put("introduce",QueryType.LIKE);
        queryRules.put("status",QueryType.EQ);
        myQueryWrapper.queryAll(dqType,queryRules);
        List<DqType> dqUsers = dqTypeMapper.selectList(myQueryWrapper);
        myQueryWrapper.clear();
        queryRules.clear();
        return dqUsers;
    }

    @Override
    public int deleteDqTypeById(Long dqTypeId) {
        int i = dqTypeMapper.deleteById(dqTypeId);
        return i;
    }

    @Override
    public int updateDqType(DqType dqType) {
        int i = dqTypeMapper.updateById(dqType);
        return i;
    }

}
