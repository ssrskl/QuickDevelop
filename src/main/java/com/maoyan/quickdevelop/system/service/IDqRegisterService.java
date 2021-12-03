package com.maoyan.quickdevelop.system.service;

import com.maoyan.quickdevelop.system.domain.DqUserVO;
import com.maoyan.quickdevelop.system.domain.vo.RegisterVO;

public interface IDqRegisterService {
  public int dqUserRegister(RegisterVO registerVO);
}
