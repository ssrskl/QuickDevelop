package com.maoyan.quickdevelop.common.core.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author 猫颜
 * @date 2021年07月27日 下午4:48
 * 类的作用：TODO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "dq_operlog")
public class DqOperLog {
    /** 日志主键 */
    @TableId(value = "oper_id")
    private Long operId;

    /** 操作模块 */
    @TableField(value = "title")
    private String title;

    /** 业务类型（0=其它,1=新增,2=修改,3=删除,4=授权,5=导出,6=导入,7=强退,8=生成代码,9=清空数据） */
    @TableField(value = "business_type")
    private Integer businessType;

    /** 请求方法 */
    @TableField(value = "method")
    private String method;

    /** 请求方式 */
    @TableField(value = "request_method")
    private String requestMethod;

    /** 操作人员 */
    @TableField(value = "oper_username")
    private String operUserName;

    /** 请求url */
    @TableField(value = "oper_url")
    private String operUrl;

    /** 操作的ip */
    @TableField(value = "oper_ip")
    private String operIp;

    /** 操作地点 */
    @TableField(value = "oper_location")
    private String operLocation;

    /** 请求参数 */
    @TableField(value = "oper_param")
    private String operParam;

    /** 返回参数 */
    @TableField(value = "json_result")
    private String jsonResult;

    /** 操作状态（0正常 1异常） */
    @TableField(value = "status")
    private String status;

    /** 错误消息 */
    @TableField(value = "error_msg")
    private String errorMsg;

    /** 操作时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date operTime;
}