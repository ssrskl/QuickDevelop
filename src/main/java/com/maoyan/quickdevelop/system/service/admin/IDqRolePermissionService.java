package com.maoyan.quickdevelop.system.service.admin;

import com.maoyan.quickdevelop.common.core.domain.DqRolePermission;
import com.maoyan.quickdevelop.common.core.domain.DqType;

import java.util.List;

/**
 * @author 猫颜
 * @date 2021/5/27 22:26
 * 类别
 */

public interface IDqRolePermissionService {

    /**
     * 根据角色ID查
     * @param roleId
     * @return
     */
    public DqRolePermission selectDqRolePermissionById(Long roleId);


    /**
     * 根据角色名称查
     * @param roleName
     * @return
     */
    public DqRolePermission selectDqRolePermissionByName(String roleName);
}
