package com.maoyan.quickdevelop.system.service.Impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.maoyan.quickdevelop.common.constant.HttpStatus;
import com.maoyan.quickdevelop.common.core.domain.DqUser;
import com.maoyan.quickdevelop.common.core.domain.DqUserRole;
import com.maoyan.quickdevelop.common.exception.CustomException;
import com.maoyan.quickdevelop.common.rabbitmq.ProucerUtil;
import com.maoyan.quickdevelop.common.utils.DateUtils;
import com.maoyan.quickdevelop.common.utils.StringUtils;
import com.maoyan.quickdevelop.system.domain.vo.RegisterVO;
import com.maoyan.quickdevelop.system.mapper.DqUserMapper;
import com.maoyan.quickdevelop.system.mapper.DqUserRoleMapper;
import com.maoyan.quickdevelop.system.service.IDqRegisterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author 猫颜
 * @date 2021/1/2
 */
@Transactional
@Service
@Slf4j
public class IDqRegisterServiceImpl implements IDqRegisterService {
  @Autowired
  private RedisTemplate redisTemplate;
  @Autowired
  private DqUserMapper dqUserMapper;
  @Autowired
  private ProucerUtil proucerUtil;
  @Autowired
  private DqUserRoleMapper dqUserRoleMapper;

  @Override
  public int dqUserRegister(RegisterVO registerVO) {
    // 校验验证码
    // 从Redis获取验证码,就是获取value，然后通过value获取key，key是用户的邮箱，再来激活邮箱
    // 验证码空判断
    String o = String.valueOf(redisTemplate.opsForValue().get(registerVO.getEmail()));
    if (StringUtils.isNull(o)) {
      throw new CustomException("验证码不存在，或已过时", HttpStatus.ERROR);
    }
    String emailVerificationCodeFromRedis = o;
    if (!StringUtils.equals(registerVO.getVerificationCode(), emailVerificationCodeFromRedis)) {
      // 邮箱验证失败
      throw new CustomException("验证码错误", HttpStatus.ERROR);
    }
    // 创建新的用户实例
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
    newDqUser.setCheckStatus("0");
    newDqUser.setSchoolId(0L);
    newDqUser.setCreateTime(DateUtils.getNowDate());
    newDqUser.setUpdateTime(DateUtils.getNowDate());
    // 判断用户是否已经验证过，在发送验证码的时候其实已经验证过了
    //注册
    int i = dqUserMapper.insert(newDqUser);
    if (i <= 0) {
      throw new CustomException("注册失败", HttpStatus.ERROR);
    }
    // 给用户分配角色
    // 获取用户
    LambdaQueryWrapper<DqUser> dqUserLambdaQueryWrapper = new LambdaQueryWrapper<>();
    dqUserLambdaQueryWrapper.eq(DqUser::getUserName,newDqUser.getUserName());
    DqUser dqUser = dqUserMapper.selectOne(dqUserLambdaQueryWrapper);
    DqUserRole dqUserRole = new DqUserRole();
    dqUserRole.setRoleName("普通用户");
    dqUserRole.setUserId(dqUser.getUserId());
    dqUserRole.setRoleStatus("1");
    dqUserRole.setCreateTime(DateUtils.getNowDate());
    dqUserRole.setUpdateTime(DateUtils.getNowDate());
    int insert = dqUserRoleMapper.insert(dqUserRole);
    if (insert <= 0) {
      throw new CustomException("注册失败", HttpStatus.ERROR);
    }
    return i;
  }

  @Override
  public int getEmailVerificationCode(String dqUserMail) {
    // 生成验证码
    String random = RandomUtil.randomString(6);
    // 判定此用户是否已经注册
    LambdaQueryWrapper<DqUser> dqUserLambdaQueryWrapper = new LambdaQueryWrapper<>();
    dqUserLambdaQueryWrapper.eq(DqUser::getEmail, dqUserMail);
    DqUser dqUser = dqUserMapper.selectOne(dqUserLambdaQueryWrapper);
    if (StringUtils.isNotNull(dqUser)) {
      throw new CustomException("此用户已存在", HttpStatus.ERROR);
    }
    // 向Redis中存入验证码
// 判断用户2min之内是否已经发送了验证码
    /**
     * key：用户邮箱
     * value：验证码
     */
    if (redisTemplate.hasKey(dqUserMail)) {
      // 已经发送过邮箱验证码了
      throw new CustomException("操作频繁", HttpStatus.ERROR);
    } else {
      // 向Redis发送验证码，设置2min过期
      redisTemplate.opsForValue().append(dqUserMail, random);
      redisTemplate.expire(dqUserMail, 2, TimeUnit.MINUTES);
      // 将邮箱通知放入消息队列
      // 发送到邮箱认证邮箱
      // 发送消息
      HashMap<String, String> emailMessage = new HashMap<>();
      // 用户还没注册成功，先无需获取用户名
      // emailMessage.put("DqUserUsername", dqUser.getUserName());
      emailMessage.put("DqUserEmail", dqUserMail);
      emailMessage.put("EmailVerificationCode", random);
      // proucerUtil.send(JSONUtil.toJsonStr(emailMessage));
      proucerUtil.send(emailMessage);
    }
    return HttpStatus.SUCCESS;
  }
}
