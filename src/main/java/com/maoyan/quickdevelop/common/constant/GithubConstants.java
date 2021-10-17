package com.maoyan.quickdevelop.common.constant;

/**
 * github常量化字段
 */
public class GithubConstants {
  //github获取的clientid
  public static final String CLIENT_ID = "15d067f1d8a119a23d64";
  //这里填写在GitHub上注册应用时候获得 CLIENT_SECRET
  public static final String CLIENT_SECRET = "9105b92dc20901fff3267112d88e634e2b6c744e";
  // 回调路径
  public static final String CALLBACK = "http://localhost/callback";
  //获取code的url
  public static final String CODE_URL = "https://github.com/login/oauth/authorize?client_id=" + CLIENT_ID + "&state=STATE&redirect_uri=" + CALLBACK + "";
  //获取token的url
  public static final String TOKEN_URL = "https://github.com/login/oauth/access_token?client_id=" + CLIENT_ID + "&client_secret=" + CLIENT_SECRET + "&code=CODE&redirect_uri=" + CALLBACK + "";
  //获取用户信息的url
  public static final String USER_INFO_URL = "https://api.github.com/user?access_token=TOKEN";
}
