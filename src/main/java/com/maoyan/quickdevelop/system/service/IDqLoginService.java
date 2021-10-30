package com.maoyan.quickdevelop.system.service;

import cn.dev33.satoken.stp.SaTokenInfo;
import com.maoyan.quickdevelop.common.core.domain.DqUser;
import com.maoyan.quickdevelop.system.domain.vo.LoginVO;

public interface IDqLoginService {
  public SaTokenInfo dqUserLogin(LoginVO loginVO);
  public SaTokenInfo dqUserThirdLogin();
  public SaTokenInfo dqUserGiteeLogin(String code);
}
