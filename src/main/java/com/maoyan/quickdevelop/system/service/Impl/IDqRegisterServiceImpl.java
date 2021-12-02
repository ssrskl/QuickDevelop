package com.maoyan.quickdevelop.system.service.Impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.hutool.core.util.RandomUtil;
import com.maoyan.quickdevelop.common.constant.HttpStatus;
import com.maoyan.quickdevelop.common.core.domain.DqUser;
import com.maoyan.quickdevelop.common.core.domain.domainvo.DqUserRegisterVO;
import com.maoyan.quickdevelop.common.exception.CustomException;
import com.maoyan.quickdevelop.common.utils.DateUtils;
import com.maoyan.quickdevelop.common.utils.StringUtils;
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
  public int dqUserRegister(DqUserRegisterVO dqUserRegisterVO) {
    DqUser newDqUser = new DqUser();
    newDqUser.setUserName(dqUserRegisterVO.getUserName());
    newDqUser.setEmail(dqUserRegisterVO.getEmail());
    newDqUser.setPhoneNumber(dqUserRegisterVO.getPhoneNumber());
    newDqUser.setSex(StringUtils.isNotEmpty(dqUserRegisterVO.getSex()) ? dqUserRegisterVO.getSex() : "2");
    newDqUser.setAvatar(StringUtils.isNotEmpty(dqUserRegisterVO.getAvatar()) ? dqUserRegisterVO.getAvatar() : "morende");
    newDqUser.setPassword(SaSecureUtil.md5(SaSecureUtil.sha1(dqUserRegisterVO.getPassword())));
    newDqUser.setStatus("1");
    newDqUser.setLoginIp("127.0.0.1");
    newDqUser.setLoginDate(DateUtils.getNowDate());
    newDqUser.setSignature(StringUtils.isNotEmpty(dqUserRegisterVO.getSignature()) ? dqUserRegisterVO.getSignature() : "个性签名");
    newDqUser.setGrade(1L);
    newDqUser.setExperience(0L);
    newDqUser.setCheckParam(RandomUtil.randomString(20));
    newDqUser.setCheckStatus("0");
    newDqUser.setSchoolId(0L);
    newDqUser.setCreateTime(DateUtils.getNowDate());
    newDqUser.setUpdateTime(DateUtils.getNowDate());
    // 发送到邮箱认证邮箱
    //注册
    int i = iUserService.insertDqUser(newDqUser);
    //注册成功返回值为1，失败为0
    if (i <= 0) {
      throw new CustomException("注册失败", HttpStatus.ERROR);
    }
    return i;
  }
}
