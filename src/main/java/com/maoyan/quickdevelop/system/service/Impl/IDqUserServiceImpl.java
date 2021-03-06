package com.maoyan.quickdevelop.system.service.Impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.maoyan.quickdevelop.common.core.domain.postprocessor.DqUserPostProcessor;
import com.maoyan.quickdevelop.common.utils.StringUtils;
import com.maoyan.quickdevelop.common.constant.HttpStatus;
import com.maoyan.quickdevelop.common.core.domain.DqRolePermission;
import com.maoyan.quickdevelop.common.core.domain.DqUser;
import com.maoyan.quickdevelop.common.exception.CustomException;
import com.maoyan.quickdevelop.common.utils.DateUtils;
import com.maoyan.quickdevelop.common.utils.MyQueryWrapper;
import com.maoyan.quickdevelop.common.utils.annotation.type.QueryType;
import com.maoyan.quickdevelop.common.utils.ip.IpUtils;
import com.maoyan.quickdevelop.system.mapper.DqUserMapper;
import com.maoyan.quickdevelop.system.mapper.postprocessor.DqUserPostProcessorMapper;
import com.maoyan.quickdevelop.system.service.IDqUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author 猫颜
 * @date 2021/5/27 22:28
 */
@Transactional
@Service
@Slf4j
public class IDqUserServiceImpl implements IDqUserService {

  @Autowired
  private RedisTemplate redisTemplate;
  @Autowired
  private DqUserMapper dqUserMapper;
  @Autowired
  private DqUserPostProcessorMapper dqUserPostProcessorMapper;

  QueryWrapper<DqUser> queryWrapper = new QueryWrapper<>();

  /**
   * 工具型，不抛出异常
   *
   * @param email
   * @param password
   * @return
   */
  @Override
  public DqUser selectDqUserByEmailAndPassword(String email, String password) {
    LambdaQueryWrapper<DqUser> dqUserLambdaQueryWrapper = new LambdaQueryWrapper<>();
    dqUserLambdaQueryWrapper.eq(StringUtils.isNotEmpty(email), DqUser::getEmail, email)
            .eq(StringUtils.isNotEmpty(password), DqUser::getPassword, password);
    DqUser dqUser = dqUserMapper.selectOne(dqUserLambdaQueryWrapper);
    if(StringUtils.isNull(dqUser)){
      throw new CustomException("用户名密码错误",HttpStatus.ERROR);
    }
    return dqUser;
  }

  @Override
  public List<DqUserPostProcessor> commonSelectDqUsers(int pageNum, int pageSize, DqUser dqUser) {
    PageHelper.startPage(pageNum, pageSize);
    List<DqUserPostProcessor> dqUserPostProcessors = dqUserPostProcessorMapper.selectDqUserPostProcesser(dqUser);
    if (StringUtils.isEmpty(dqUserPostProcessors)) {
      throw new CustomException("未查询到用户", HttpStatus.NOT_FOUND);
    }
    return dqUserPostProcessors;
  }

  @Override
  public DqUserPostProcessor getDqUserById_Server(Long dqUserId) {
    // 判断是否为空
    if (StringUtils.isNull(dqUserId)) {
      throw new CustomException("请传入用户ID", HttpStatus.ERROR);
    }
    DqUser dqUser = new DqUser();
    dqUser.setUserId(dqUserId);
    List<DqUserPostProcessor> dqUserPostProcessors = dqUserPostProcessorMapper.selectDqUserPostProcesser(dqUser);
    if (StringUtils.isEmpty(dqUserPostProcessors)){
      throw new CustomException("未查询到用户", HttpStatus.NOT_FOUND);
    }
    return dqUserPostProcessors.get(0);
  }

  /**
   * 工具型
   **/
  @Override
  public DqUser selectDqUserById(Long dqUserId) {
    LambdaQueryWrapper<DqUser> dqUserLambdaQueryWrapper = new LambdaQueryWrapper<>();
    dqUserLambdaQueryWrapper.eq(DqUser::getUserId, dqUserId);
    DqUser dqUser = dqUserMapper.selectOne(dqUserLambdaQueryWrapper);
    return dqUser;
//    MyQueryWrapper<DqUser> myQueryWrapper = new MyQueryWrapper<>();
//    myQueryWrapper.statuseq();
//    myQueryWrapper.eq("user_id", dqUserId);
//    DqUser dqUser = dqUserMapper.selectOne(myQueryWrapper);
//    myQueryWrapper.clear();
//    if (dqUser == null) {
//      throw new CustomException("此用户不存在或被封禁", HttpStatus.NOT_FOUND);
//    }
  }

  @Override
  public DqUser selectDqUserByUserName(String userName) {
    MyQueryWrapper<DqUser> myQueryWrapper = new MyQueryWrapper<>();
    myQueryWrapper.statuseq();
    myQueryWrapper.eq("user_name", userName);
    DqUser dqUser = dqUserMapper.selectOne(myQueryWrapper);
    myQueryWrapper.clear();
    if (dqUser == null) {
      throw new CustomException("此用户不存在或被封禁", HttpStatus.NOT_FOUND);
    }
    return dqUser;
  }


  @Override
  public int insertDqUser(DqUser dqUser) {
    int insert = dqUserMapper.insert(dqUser);
    return insert;
  }


  @Override
  public int deleteDqUserMySelf() {
    Long dqUserId = StpUtil.getLoginIdAsLong();
    int i = dqUserMapper.deleteById(dqUserId);
    if (i <= 0) {
      throw new CustomException("删除失败", HttpStatus.ERROR);
    }
    return i;
  }

  /**
   * 常规根据ID更改用户
   *
   * @param dqUser
   * @return
   */
  @Override
  public int updateDqUserSelf(DqUser dqUser) {
    // 获取用户原有的信息
    DqUser oldDqUser = dqUserMapper.selectById(StpUtil.getLoginIdAsLong());
    // 为空则默认不更改。
    oldDqUser.setUserName(dqUser.getUserName());
    oldDqUser.setEmail(dqUser.getEmail());
    oldDqUser.setPhoneNumber(dqUser.getPhoneNumber());
    oldDqUser.setSex(dqUser.getSex());
    oldDqUser.setAvatar(dqUser.getAvatar());
    if (StringUtils.isNotEmpty(dqUser.getPassword())) {
      oldDqUser.setPassword(SaSecureUtil.md5(SaSecureUtil.sha1(dqUser.getPassword())));
    }
    oldDqUser.setSchoolId(dqUser.getSchoolId());
    oldDqUser.setSignature(dqUser.getSignature());
    oldDqUser.setLoginDate(DateUtils.getNowDate());
    oldDqUser.setLoginIp(IpUtils.getHostIp());
    oldDqUser.setUpdateTime(DateUtils.getNowDate());
    int i = dqUserMapper.updateById(oldDqUser);
    if (i <= 0) {
      throw new CustomException("更新失败", HttpStatus.ERROR);
    }
    return i;
  }

  /**
   * 邮箱校验
   * @param emailVerificationCode
   * @return
   */
  @Override
  public int emailVerification(String dqUserMail,String emailVerificationCode) {
    // 用户是否存在判定
    LambdaQueryWrapper<DqUser> dqUserLambdaQueryWrapper = new LambdaQueryWrapper<>();
    dqUserLambdaQueryWrapper.eq(DqUser::getEmail,dqUserMail);
    DqUser dqUser = dqUserMapper.selectOne(dqUserLambdaQueryWrapper);
    if (StringUtils.isNull(dqUser)) {
      throw new CustomException("不存在此用户");
    }
    // 从Redis获取验证码,就是获取value，然后通过value获取key，key是用户的邮箱，再来激活邮箱
    // 验证码空判断
    Object o = redisTemplate.opsForValue().get(dqUserMail);
    if (StringUtils.isNull(o)){
      throw new CustomException("验证码不存在",HttpStatus.ERROR);
    }
    String  emailVerificationCodeFromRedis= o.toString();
    if (!StringUtils.equals(emailVerificationCode,emailVerificationCodeFromRedis)){
      // 邮箱验证失败
      throw new CustomException("验证码错误",HttpStatus.ERROR);
    }
    // 邮箱验证成功
    return HttpStatus.SUCCESS;
  }
}
