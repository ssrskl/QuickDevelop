package com.maoyan.quickdevelop.common.core.domain.postprocessor;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 猫颜
 * @date 2021年08月02日 上午7:44
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DqTypePostProcesser implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 类型ID **/
    @TableId(value = "type_id")
    private Long typeId;

    /** 类型名称 **/
    @TableField(value = "type_name")
    private String typeName;

    /** 类型图片 **/
    @TableField(value = "type_image")
    private String typeImage;

    /** 类型状态（0可用，1不可用） **/
    @TableField(value = "status")
    private String status;

    /** 创建人ID **/
    @TableField(value = "create_manid")
    private Long createManId;

    /** 修改人ID **/
    @TableField(value = "update_manid")
    private Long updateManId;

    /** 类型介绍 **/
    @TableField(value = "introduce")
    private String introduce;

    /** 所属文章的数量 **/
    private int articleNum;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}