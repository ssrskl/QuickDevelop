package com.maoyan.quickdevelop.system.service.Impl;

import cn.dev33.satoken.stp.StpInterface;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.maoyan.quickdevelop.common.core.domain.DqRolePermission;
import com.maoyan.quickdevelop.common.core.domain.DqUser;
import com.maoyan.quickdevelop.common.core.domain.DqUserRole;
import com.maoyan.quickdevelop.system.mapper.DqRolePermissionMapper;
import com.maoyan.quickdevelop.system.mapper.DqUserRoleMapper;
import com.maoyan.quickdevelop.system.service.IDqUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author 猫颜
 * @date 2021/5/27 18:15
 * 自定义权限验证接口
 * 权限校验就是采用下面两个方法，来实现自己的字段来权限认证
 */
@Component // 保证此类被SpringBoot扫描，完成sa-token的自定义权限验证扩展,保证被扫描到
public class IStpInterfaceImpl implements StpInterface {

    @Autowired
    private IDqUserService iUserService;
    @Autowired
    private DqUserRoleMapper dqUserRoleMapper;
    @Autowired
    private DqRolePermissionMapper dqRolePermissionMapper;
    /**
     * 获取权限列表
     * @param loginId
     * @param loginKey
     * @return
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginKey) {
        /**
         * 干他喵的，我懂了，这里loginId，是本用户的id，注解中自动传参数的，没必要自己再写获取当前用户的权限了
         */
//        List<String> allPermissionList = Arrays.asList("user-query","user-query","user-update","user-delete",
//                "user-comment-add","user-comment-delete",
//                "user-ordinary",
//                "admin-article","admin-type","admin-power","admin-user","admin-comment",
//                "admin-ordinary",
//                "super-admin");
//        List<String> userPermissionList = Arrays.asList("user-query","user-query","user-update","user-delete",
//                "user-comment-add","user-comment-delete",
//                "user-ordinary");
        List<String> dqUserPermissions = dqRolePermissionMapper.selectPermissionNameById(Long.parseLong(String.valueOf(loginId)));
//        List<String> list = new ArrayList<String>();
//        //先将其转为String再转Long,直接(Long) loginId会报错，为啥，还得搜搜
//        DqUser dqUser = iUserService.selectDqUserById(Long.parseLong(String.valueOf(loginId)));
//        String roleName = dqUser.getRole();
//        DqRolePermission dqRolePermission = idqRolePermissionService.selectDqRolePermissionByName(roleName);
//        String permissions = dqRolePermission.getPermissionName();
//        //将权限分割
//        String[] permissions2 = permissions.split(",");
//        for (String permission : permissions2) {
//            list.add(permission);
//        }
//        if (Arrays.asList(permissions2).contains("super-admin")){
//            list.add("*");
//        }
//        if (Arrays.asList(permissions2).contains("admin-ordinary")){
//            list.add("user*");
//            list.add("admin*");
//        }
//        if (Arrays.asList(permissions2).contains("user-ordinary")){
//            list.add("user*");
//        }

//        List<String> list = new ArrayList<String>();
//        String permissions = iUserService.selectNowDqUserPermission();
//        //将权限分割
//        String[] permissions2 = permissions.split(",");
//        for (String permission : permissions2) {
//            list.add(permission);
//        }
        return dqUserPermissions;
    }

    /**
     * 获取角色列表
     * @param loginId
     * @param loginKey
     * @return
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginKey) {
        // satoken的id就是当前用户的ID,通过这个Id直接查询角色
        List<String> dqUserRoles = dqUserRoleMapper.selectRoleNameById(Long.parseLong(String.valueOf(loginId)));
//        List<String> list = new ArrayList<String>();
//        DqUser dqUser = iUserService.selectDqUserById(Long.parseLong(String.valueOf(loginId)));
//        String role = dqUser.getRole();
//        list.add(role);
        return dqUserRoles;
    }

}
