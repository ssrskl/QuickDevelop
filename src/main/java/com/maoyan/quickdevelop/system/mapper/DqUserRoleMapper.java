package com.maoyan.quickdevelop.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maoyan.quickdevelop.common.core.domain.DqUserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DqUserRoleMapper extends BaseMapper<DqUserRole> {
  @Select("select community.dq_user_role.role_name from dq_user_role where user_id=#{dqUserId}")
  List<String> selectRoleNameById(@Param(value = "dqUserId") Long dqUserId);
}
