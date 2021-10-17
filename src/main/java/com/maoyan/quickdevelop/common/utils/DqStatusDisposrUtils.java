package com.maoyan.quickdevelop.common.utils;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.maoyan.quickdevelop.common.core.domain.dqabstract.DqStatusDispose;

/**
 * @author 猫颜
 * @date 2021/5/30 17:14
 * Status状态处理工具类
 */
public class DqStatusDisposrUtils {

    QueryWrapper<DqStatusDispose> queryWrapper1 = new QueryWrapper<>();

    //校验DqArticle状态
    public static DqStatusDispose disposeDqArticle(DqStatusDispose dqClass) {
        //接收参数为null,则扔出null到controller层，由controller处理。
        if (dqClass == null){
            return null;
        }
        //校验登陆与权限
        if (StpUtil.isLogin() && StpUtil.hasRole("管理员")) {
            return dqClass;
        }
        if (StringUtils.equals("1",dqClass.getStatus()) ){
            return null;
        }
        return dqClass;
    }

    //校验DqArticle状态
    public QueryWrapper<? extends DqStatusDispose> disposeDqArticles(QueryWrapper<? extends DqStatusDispose> queryWrapper){

            //校验登陆与权限
            if (StpUtil.isLogin() && StpUtil.hasRole("管理员")) {
                return queryWrapper;
            }else {

                queryWrapper1.eq("status","0");
                return queryWrapper1;
            }


    }
}
