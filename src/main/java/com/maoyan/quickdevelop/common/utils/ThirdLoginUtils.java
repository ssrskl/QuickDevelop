package com.maoyan.quickdevelop.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.maoyan.quickdevelop.common.constant.GiteeConstans;
import com.maoyan.quickdevelop.common.core.AjaxResult;
import com.maoyan.quickdevelop.common.utils.http.HttpUtils;

public class ThirdLoginUtils {

  public static JSONObject getUserInfo(String code){
    JSONObject accessToken = getAccessToken(code);
    //从json对象中获取access——token
    String access_token = accessToken.getString("access_token");
    //使用access_token获取用户信息
    String userinfostr = HttpUtils.sendGet(GiteeConstans.getUserInfo(access_token), null);
    //序列化用户信息
    JSONObject userInfo = JSON.parseObject(userinfostr);
    return userInfo;
  }

  protected static JSONObject getAccessToken(String code){
    //获取accessInfo字符串
    String accessInfo = HttpUtils.sendPost(GiteeConstans.getTokenUrl(code), null);
    //将返回的字符串序列化为json对象
    JSONObject jsonObject = JSON.parseObject(accessInfo);
    return jsonObject;
  }
}
