package com.maoyan.quickdevelop.system.service.Impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.maoyan.quickdevelop.common.utils.StringUtils;
import com.maoyan.quickdevelop.common.constant.HttpStatus;
import com.maoyan.quickdevelop.common.core.domain.DqUser;
import com.maoyan.quickdevelop.common.exception.CustomException;
import com.maoyan.quickdevelop.common.utils.DateUtils;
import com.maoyan.quickdevelop.common.utils.ThirdLoginUtils;
import com.maoyan.quickdevelop.system.domain.vo.LoginVO;
import com.maoyan.quickdevelop.system.mapper.DqUserMapper;
import com.maoyan.quickdevelop.system.service.IDqLoginService;
import com.maoyan.quickdevelop.system.service.IDqUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@Slf4j
public class IDqLoginServiceImpl implements IDqLoginService {

  @Autowired
  private IDqUserService iUserService;
  @Autowired
  private DqUserMapper dqUserMapper;

  @Override
  public SaTokenInfo dqUserLogin(DqUser dqUser) {

    //登陆方法
    DqUser dqUserFromDatabase = iUserService.selectDqUserByEmailAndPassword(dqUser.getEmail(), SaSecureUtil.md5(SaSecureUtil.sha1(dqUser.getPassword())));
    //去掉用户的password
    dqUser.setPassword("000000");
    // 校验用户邮箱验证
    if (!StringUtils.equals("1", dqUserFromDatabase.getCheckStatus())) {
      throw new CustomException("邮箱验证未通过", HttpStatus.ERROR);
    }
    /** 断言适合做测试开发，不适合实际生产 **/
    //Assert.notNull(dqUser,"用户名密码错误");
    //Sa-Token登陆
    //TODO 登陆并使用数据库用户ID来作为Satoken的用户ID
    StpUtil.setLoginId(dqUserFromDatabase.getUserId(), true);
//        StpUtil.setLoginId(dqUser.getUserName(),true);
    SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
    System.out.println(tokenInfo.tokenName);
    System.out.println(tokenInfo.tokenValue);
    //传入tokeninfo,不再使用传统的cookie
//        		"tokenName": "MaoyanToken",
//		"tokenValue": "e4fd232e-c5cf-4f00-afa7-f3d4875ede6b
    return tokenInfo;
  }

  /**
   * 第三方登陆
   *
   * @return
   */
  @Override
  public SaTokenInfo dqUserThirdLogin() {

    return null;
  }

  @Override
  public SaTokenInfo dqUserGiteeLogin(String code) {
    LambdaQueryWrapper<DqUser> dqUserLambdaQueryWrapper = new LambdaQueryWrapper<>();
    JSONObject userInfo = ThirdLoginUtils.getUserInfo(code);
    //格式化userInfo
    DqUser dqUser = new DqUser();
    //1. 获取用户名
    String dqUsername = userInfo.getString("login");
    //2. 获取昵称
    String dqUserNickName = userInfo.getString("name");
    //3. 获取头像
    String dqAvatar = userInfo.getString("avatar_url");
    //4. 获取个性签名
    String dqSignature = userInfo.getString("bio");
    //5. 获取邮箱
    String dqEmail = userInfo.getString("email");
    dqUser.setUserName(dqUserNickName);
    dqUser.setAvatar(dqAvatar);
    dqUser.setSignature(dqSignature);
    dqUser.setEmail(dqEmail);
    dqUser.setPassword("系统设定的密码");
    dqUser.setCreateTime(DateUtils.getNowDate());
    dqUser.setUpdateTime(DateUtils.getNowDate());
    dqUser.setLoginDate(DateUtils.getNowDate());
    //从数据库查询是否有这个用户
    dqUserLambdaQueryWrapper.eq(DqUser::getUserName, dqUsername);
    DqUser dqUserByUserName = dqUserMapper.selectOne(dqUserLambdaQueryWrapper);
    if (StringUtils.isNull(dqUserByUserName)) {
      //为空则注册一个
      int i = iUserService.insertDqUser(dqUser);
      if (i <= 0) {
        //注册失败
        throw new CustomException("注册失败", HttpStatus.ERROR);
      }
    }
    //已存在用户，则登陆
    StpUtil.setLoginId(iUserService.selectDqUserByUserName(dqUsername).getUserId(), true);
    SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
    System.out.println(tokenInfo.tokenName);
    System.out.println(tokenInfo.tokenValue);
    return tokenInfo;
  }

}
