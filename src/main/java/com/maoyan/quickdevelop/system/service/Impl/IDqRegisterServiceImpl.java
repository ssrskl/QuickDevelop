package com.maoyan.quickdevelop.system.service.Impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import com.maoyan.quickdevelop.common.constant.HttpStatus;
import com.maoyan.quickdevelop.common.core.domain.DqUser;
import com.maoyan.quickdevelop.common.exception.CustomException;
import com.maoyan.quickdevelop.common.utils.DateUtils;
import com.maoyan.quickdevelop.system.domain.vo.RegisterVO;
import com.maoyan.quickdevelop.system.service.IDqRegisterService;
import com.maoyan.quickdevelop.system.service.IDqUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@Slf4j
public class IDqRegisterServiceImpl implements IDqRegisterService {
  @Autowired
  private IDqUserService iUserService;

  @Override
  public int dqUserRegister(RegisterVO registerVO) {
    DqUser dqUser = new DqUser();
    dqUser.setUserName(registerVO.getUserName());
    //将密码md5加密
    dqUser.setPassWord(SaSecureUtil.md5(SaSecureUtil.sha1(registerVO.getPassWord())));
    dqUser.setEmail(registerVO.getEmail());
    dqUser.setAvatar(registerVO.getAvatar());
    dqUser.setPhoneNumber(registerVO.getPhoneNumber());
    dqUser.setCreateTime(DateUtils.getNowDate());
    dqUser.setUpdateTime(DateUtils.getNowDate());
    dqUser.setLoginDate(DateUtils.getNowDate());
    //注册
    int i = iUserService.insertDqUser(dqUser);
    //注册成功返回值为1，失败为0
    if (i <= 0) {
      throw new CustomException("注册失败", HttpStatus.ERROR);
    }
    return i;
  }
}
