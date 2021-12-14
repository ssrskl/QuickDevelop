package com.maoyan.quickdevelop.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maoyan.quickdevelop.common.core.domain.DqRolePermission;
import com.maoyan.quickdevelop.common.core.domain.DqUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author 猫颜
 * @date 2021/5/27 22:25
 */
@Mapper
public interface DqRolePermissionMapper extends BaseMapper<DqRolePermission> {
  @Select("SELECT permission_name\n" +
          "FROM dq_role_permission,\n" +
          "     dq_user_role\n" +
          "WHERE dq_user_role.user_id = #{dqUserId}\n" +
          "  AND dq_role_permission.role_name = dq_user_role.role_name")
  List<String> selectPermissionNameById(@Param(value = "dqUserId") Long dqUserId);
//  @Select("select community.dq_role_permission.permission_name\n" +
//          "from dq_role_permission\n" +
//          "where community.dq_role_permission.role_name = any(select community.dq_user_role.role_name from dq_user_role where user_id=#{dqUserId})")

}