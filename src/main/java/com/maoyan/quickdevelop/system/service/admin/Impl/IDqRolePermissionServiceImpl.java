package com.maoyan.quickdevelop.system.service.admin.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.maoyan.quickdevelop.common.core.domain.DqRolePermission;
import com.maoyan.quickdevelop.system.mapper.DqRolePermissionMapper;
import com.maoyan.quickdevelop.system.service.admin.IDqRolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 猫颜
 * @date 2021/5/28 19:14
 */
@Service
public class IDqRolePermissionServiceImpl implements IDqRolePermissionService {

    @Autowired
    private DqRolePermissionMapper dqRolePermissionMapper;

    @Override
    public DqRolePermission selectDqRolePermissionById(Long roleId) {
        DqRolePermission dqRolePermission = dqRolePermissionMapper.selectById(roleId);
        return dqRolePermission;
    }

    @Override
    public DqRolePermission selectDqRolePermissionByName(String roleName) {
        QueryWrapper<DqRolePermission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_name",roleName);
        DqRolePermission dqRolePermission = dqRolePermissionMapper.selectOne(queryWrapper);
        return dqRolePermission;
    }
}
