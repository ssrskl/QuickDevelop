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

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * @author 猫颜
 * @date 2021/5/27 21:28
 * 文章标签对应实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "dq_tag")
public class DqTag implements Serializable{

    private static final long serialVersionUID = 1L;

    /** 标签的ID */
    @TableId(value = "tag_id")
    private Long tagId;

    /** 标签的名称 */
    @TableField(value = "tag_name")
    private String tagName;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

}
