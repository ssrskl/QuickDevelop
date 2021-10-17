package com.maoyan.quickdevelop.system.service.admin.Impl;

import cn.dev33.satoken.stp.StpUtil;
import com.github.pagehelper.PageHelper;
import com.maoyan.quickdevelop.common.core.domain.DqArticle;
import com.maoyan.quickdevelop.common.core.domain.DqRolePermission;
import com.maoyan.quickdevelop.common.core.domain.DqUser;
import com.maoyan.quickdevelop.common.utils.MyQueryWrapper;
import com.maoyan.quickdevelop.common.utils.annotation.type.QueryType;
import com.maoyan.quickdevelop.system.mapper.DqUserMapper;
import com.maoyan.quickdevelop.system.service.admin.IAdminDqUserService;
import com.maoyan.quickdevelop.system.service.admin.IDqRolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author 猫颜
 * @date 2021年07月18日 上午11:38
 * 类的作用：TODO
 */
@Service
public class AdminDqUserServiceImpl implements IAdminDqUserService {

    @Autowired
    private DqUserMapper dqUserMapper;

    @Autowired
    private IDqRolePermissionService iDqRolePermissionService;

    MyQueryWrapper<DqUser> myQueryWrapper = new MyQueryWrapper<>();

    @Override
    public List<DqUser> selectDqUserList(int pageNum, int pageSize, DqUser dqUser) throws NoSuchFieldException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        PageHelper.startPage(pageNum, pageSize);
        HashMap<String,Object> queryRules = new LinkedHashMap<>();
        queryRules.put("userId", QueryType.EQ);
        queryRules.put("userName",QueryType.LIKE);
        queryRules.put("nickName",QueryType.LIKE);
        queryRules.put("email",QueryType.EQ);
        queryRules.put("phonenumber",QueryType.EQ);
        queryRules.put("role",QueryType.EQ);
        queryRules.put("status",QueryType.EQ);
        myQueryWrapper.queryAll(dqUser,queryRules);
        List<DqUser> dqUsers = dqUserMapper.selectList(myQueryWrapper);
        myQueryWrapper.clear();
        queryRules.clear();
        return dqUsers;
    }
    @Override
    public DqUser selectDqUserById(Long dqUserId){
        DqUser dqUser = dqUserMapper.selectById(dqUserId);
        return dqUser;
    }

    @Override
    public DqUser selectDqUserByUserName(String userName) {
        myQueryWrapper.eq("user_name",userName);
        DqUser dqUser = dqUserMapper.selectOne(myQueryWrapper);
        myQueryWrapper.clear();
        return dqUser;
    }

    @Override
    public int updateDqUser(DqUser dqUser) {
        //dquser中带的有id
        int i = dqUserMapper.updateById(dqUser);
        return i;
    }

    @Override
    public int insertDqUser(DqUser dqUser) {
        int insert = dqUserMapper.insert(dqUser);
        return insert;
    }

    @Override
    public int deleteDqUser() {
        Long dqUserId = StpUtil.getLoginIdAsLong();
        int i = dqUserMapper.deleteById(dqUserId);
        return i;
    }


    @Override
    public String selectNowDqUserPermission() {
            long loginIdAsLong = StpUtil.getLoginIdAsLong();
            //获取当前用户的所有信息
            DqUser dqUser = selectDqUserById(loginIdAsLong);
            String roleName = dqUser.getRole();
            DqRolePermission dqRolePermission = iDqRolePermissionService.selectDqRolePermissionByName(roleName);
            String permissionName = dqRolePermission.getPermissionName();
            return permissionName;
    }


    @Override
    public int deleteDqUserById(Long dqUserId) {
        int i = dqUserMapper.deleteById(dqUserId);
        return i;
    }

    /**
     * 通过ID封号
     * @return
     */
    @Override
    public int banUserById(Long dqUserId){
        DqUser dqUser = selectDqUserById(dqUserId);
        dqUser.setStatus("1");
        int i = updateDqUser(dqUser);
        return i;
    }

    /**
     * 通过ID解封账号
     * @param dqUserId
     * @return
     */
    @Override
    public int unsealUserById(Long dqUserId) {
        DqUser dqUser = selectDqUserById(dqUserId);
        dqUser.setStatus("0");
        int i = updateDqUser(dqUser);
        return i;
    }
}