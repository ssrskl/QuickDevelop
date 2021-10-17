package com.maoyan.quickdevelop.system.domain.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author 猫颜
 * @date 2021/5/28 15:21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "DqArticleVO", description = "增加和修改文章参数")
public class AdminDqArticleVO {

    /**
     * 文章标题
     */
    @ApiModelProperty(value = "文章标题")
    @NotNull
    private String articletitle;

    /**
     * 文章内容
     */
    @ApiModelProperty(value = "文章内容")
    @NotNull
    private String articlecontent;

    /**
     * 文章图片
     */
    @ApiModelProperty(value = "文章图片")
    @NotNull
    private String articleimage;

    /**
     * 类型ID
     */
    @ApiModelProperty(value = "文章类型")
    @NotNull
    private Long typeId;

    /**
     * 文章状态(0正常，1被封禁)
     */
    @ApiModelProperty(value = "文章状态")
    private String status;

    /**
     * 作者ID
     */
    @ApiModelProperty(value = "作者ID")
    @NotNull
    private Long autorId;
    /**
     * 作者昵称
     */
    @ApiModelProperty(value = "作者昵称")
    private String authornickname;

    /**
     * 作者用户名
     */
    @ApiModelProperty(value = "作者用户名")
    private String authorusername;
}
