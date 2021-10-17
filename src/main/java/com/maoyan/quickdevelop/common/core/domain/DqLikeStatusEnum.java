package com.maoyan.quickdevelop.common.core.domain;

import lombok.Getter;

@Getter
public enum DqLikeStatusEnum {
    LIKE(1,"点赞"),
    UNLIKE(0,"取消点赞");

    private Integer code;

    private String msg;

    DqLikeStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
