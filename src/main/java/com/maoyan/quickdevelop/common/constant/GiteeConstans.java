package com.maoyan.quickdevelop.common.constant;

import lombok.Data;

/**
 * Gitee常量
 */
@Data
public class GiteeConstans {
  //github获取的clientid
  public static final String CLIENT_ID = "e21ada1875792c7df538e290dc403e377289ccfa94343a4aed715cd7f9297ee2";
  //这里填写在GitHub上注册应用时候获得 CLIENT_SECRET
  public static final String CLIENT_SECRET = "2454d65b0313082f4eb351e829003a478c5df20831cc5206982393adc746b69e";
  // 回调路径
  public static final String REDIRECT_URI = "http://www.codeman.ink:8848/login";
  //获取code的url
  public String CODE_URL = "https://gitee.com/oauth/authorize?client_id="+CLIENT_ID+"&redirect_uri="+REDIRECT_URI+"&response_type=code";
  //获取用户信息的url
  public static final String USER_INFO_URL = "https://gitee.com/api/v5/user?access_token=";

  private String clientId;
  private String clientSecret;
  private String redirectUri;
  private String codeUrl;
  private String userInfoUrl;

  public GiteeConstans() {
    this.clientId = CLIENT_ID;
    this.clientSecret = CLIENT_SECRET;
    this.redirectUri = REDIRECT_URI;
    this.codeUrl = CODE_URL;
    this.userInfoUrl = USER_INFO_URL;
  }

  public static String getTokenUrl(String code){
    return "https://gitee.com/oauth/token?grant_type=authorization_code&code="+code+"&client_id="+CLIENT_ID+"&redirect_uri="+ REDIRECT_URI +"&client_secret="+CLIENT_SECRET;
  }
  public static String getUserInfo(String accessToken){
    return "https://gitee.com/api/v5/user?access_token="+accessToken;
  }

}
