package com.maoyan.quickdevelop.system.service;

import com.maoyan.quickdevelop.common.core.domain.DqUser;
import com.maoyan.quickdevelop.common.core.domain.domainvo.DqUserRegisterVO;
import com.maoyan.quickdevelop.system.domain.vo.RegisterVO;

public interface IDqRegisterService {
  public int dqUserRegister(DqUserRegisterVO dqUserRegisterVO);
}
