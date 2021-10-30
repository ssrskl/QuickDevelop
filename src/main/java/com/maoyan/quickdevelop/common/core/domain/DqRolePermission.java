package com.maoyan.quickdevelop.common.core.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 猫颜
 * @date 2021/5/27 21:28
 * 用户实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "dq_role_permission")
public class DqRolePermission implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 角色ID */
    /**
     * 主键使用TableId注解，否则MybatisPlus默认使用id来查询
     */
    @TableId(value = "role_permission_id")
    private Long rolePermissionId;

    /** 角色名称 */
    @TableField(value = "role_name")
    private String roleName;

    /** 权限名称 */
    @TableField(value = "permission_name")
    private String permissionName;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

}
