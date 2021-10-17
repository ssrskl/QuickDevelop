package com.maoyan.quickdevelop.common.core.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.maoyan.quickdevelop.common.core.domain.dqabstract.DqStatusDispose;
import com.maoyan.quickdevelop.common.utils.annotation.MapperQuery;
import com.maoyan.quickdevelop.common.utils.annotation.type.QueryType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
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
@TableName(value = "dq_user")
public class DqUser extends DqStatusDispose implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 用户ID */
    /**
     * 主键使用TableId注解，否则MybatisPlus默认使用id来查询
     */
    @TableId(value = "user_id")
    private Long userId;

    /** 用户账号 */
    @TableField(value = "user_name")
    @MapperQuery(queryType = QueryType.EQ)
    private String userName;

    /** 用户昵称 */
    @TableField(value = "nick_name")
    @MapperQuery(queryType = QueryType.NE)
    private String nickName;

    /** 用户邮箱 */
    @Email(message = "邮箱格式不正确")
    @TableField(value = "email")
    private String email;

    /** 手机号码 */
    @Size(min = 0, max = 11, message = "手机号码长度不能超过11个字符")
    @TableField(value = "phone_number")
    private String phoneNumber;

    /** 用户性别 0=男,1=女,2=未知*/
    @TableField(value = "sex")
    private String sex;

    /** 用户头像 */
    @TableField(value = "avatar")
    private String avatar;

    /** 密码 */
    @TableField(value = "password")
    private String passWord;

    /** 帐号状态（0正常 1停用） */
    @TableField(value = "status")
    private String status;

    /** 删除标志（0代表存在 1代表删除,暂时没打算用） */
    @TableField(value = "delFlag")
    private String delFlag;

    /** 最后登录IP */
    @TableField(value = "loginIp")
    private String loginIp;

    /** 最后登录时间 */
    @TableField(value = "loginDate")
    private Date loginDate;

    /** 用户角色ID */
    @TableField(value = "role")
    private String role;

    /** 个性签名 **/
    @TableField(value = "signature")
    private String signature;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

}
