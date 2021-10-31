package com.maoyan.quickdevelop.system.service.Impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.hutool.core.util.RandomUtil;
import com.maoyan.quickdevelop.common.constant.HttpStatus;
import com.maoyan.quickdevelop.common.core.domain.DqUser;
import com.maoyan.quickdevelop.common.exception.CustomException;
import com.maoyan.quickdevelop.common.utils.DateUtils;
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
  public int dqUserRegister(DqUser dqUser) {
    dqUser.setUserName(dqUser.getUserName());
    //将密码md5加密
    dqUser.setPassword(SaSecureUtil.md5(SaSecureUtil.sha1(dqUser.getPassword())));
    dqUser.setEmail(dqUser.getEmail());
    dqUser.setPhoneNumber(dqUser.getPhoneNumber());
    dqUser.setSex("2");
    dqUser.setAvatar("avatar");
    dqUser.setStatus("1");
    dqUser.setSignature("无");
    dqUser.setGrade(1L);
    dqUser.setExperience(0L);
    dqUser.setCreateTime(DateUtils.getNowDate());
    dqUser.setUpdateTime(DateUtils.getNowDate());
    dqUser.setLoginDate(DateUtils.getNowDate());
    // 生成邮箱校验码
    dqUser.setCheckParam(RandomUtil.randomString(20));
    dqUser.setCheckStatus("0");
    // 发送到邮箱认证邮箱
    //注册
    int i = iUserService.insertDqUser(dqUser);
    //注册成功返回值为1，失败为0
    if (i <= 0) {
      throw new CustomException("注册失败", HttpStatus.ERROR);
    }
    return i;
  }
}
