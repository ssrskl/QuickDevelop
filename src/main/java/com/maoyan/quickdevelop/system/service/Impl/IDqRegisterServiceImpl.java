package com.maoyan.quickdevelop.system.service.Impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.maoyan.quickdevelop.common.constant.HttpStatus;
import com.maoyan.quickdevelop.common.core.domain.DqUser;
import com.maoyan.quickdevelop.common.exception.CustomException;
import com.maoyan.quickdevelop.common.rabbitmq.ProucerUtil;
import com.maoyan.quickdevelop.common.utils.DateUtils;
import com.maoyan.quickdevelop.common.utils.StringUtils;
import com.maoyan.quickdevelop.system.domain.vo.RegisterVO;
import com.maoyan.quickdevelop.system.mapper.DqUserMapper;
import com.maoyan.quickdevelop.system.service.IDqRegisterService;
import com.maoyan.quickdevelop.system.service.IDqUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

@Transactional
@Service
@Slf4j
public class IDqRegisterServiceImpl implements IDqRegisterService {
  @Autowired
  private IDqUserService iUserService;
  @Autowired
  private DqUserMapper dqUserMapper;
  @Autowired
  private ProucerUtil proucerUtil;

  @Override
  public int dqUserRegister(RegisterVO registerVO) {
    String random = RandomUtil.randomString(20);
    DqUser newDqUser = new DqUser();
    newDqUser.setUserName(registerVO.getUserName());
    newDqUser.setEmail(registerVO.getEmail());
    newDqUser.setPhoneNumber(registerVO.getPhoneNumber());
    newDqUser.setSex(StringUtils.isNotEmpty(registerVO.getSex()) ? registerVO.getSex() : "2");
    newDqUser.setAvatar(StringUtils.isNotEmpty(registerVO.getAvatar()) ? registerVO.getAvatar() : "morende");
    newDqUser.setPassword(SaSecureUtil.md5(SaSecureUtil.sha1(registerVO.getPassword())));
    newDqUser.setStatus("1");
    newDqUser.setLoginIp("127.0.0.1");
    newDqUser.setLoginDate(DateUtils.getNowDate());
    newDqUser.setSignature(StringUtils.isNotEmpty(registerVO.getSignature()) ? registerVO.getSignature() : "个性签名");
    newDqUser.setGrade(1L);
    newDqUser.setExperience(0L);
    newDqUser.setCheckParam(random);
    newDqUser.setCheckStatus("0");
    newDqUser.setSchoolId(0L);
    newDqUser.setCreateTime(DateUtils.getNowDate());
    newDqUser.setUpdateTime(DateUtils.getNowDate());
    // 判断用户是否在未激活状态，且有重复信息，是则删除用户
    LambdaQueryWrapper<DqUser> dqUserLambdaQueryWrapper = new LambdaQueryWrapper<>();
    dqUserLambdaQueryWrapper.eq(DqUser::getUserName, newDqUser.getUserName())
            .or()
            .eq(DqUser::getEmail, newDqUser.getEmail())
            .or()
            .eq(DqUser::getPhoneNumber, newDqUser.getPhoneNumber());
    DqUser dqUser = dqUserMapper.selectOne(dqUserLambdaQueryWrapper);
    if (StringUtils.isNotNull((dqUser))) {
      int i1 = dqUserMapper.deleteById(dqUser);
      if (i1 <= 0) {
        throw new CustomException("注册失败", HttpStatus.ERROR);
      }
    }
    //注册
    int i = iUserService.insertDqUser(newDqUser);
    //注册成功返回值为1，失败为0
    if (i <= 0) {
      throw new CustomException("注册失败", HttpStatus.ERROR);
    }
    // 发送到邮箱认证邮箱
    // 发送消息
    HashMap<String, String> emailMessage = new HashMap<>();
    emailMessage.put("DqUserUsername", newDqUser.getUserName());
    emailMessage.put("DqUserEmail", newDqUser.getEmail());
    emailMessage.put("EmailVerificationCode", random);
    proucerUtil.send(JSONUtil.toJsonStr(emailMessage));
    return i;
  }
}
