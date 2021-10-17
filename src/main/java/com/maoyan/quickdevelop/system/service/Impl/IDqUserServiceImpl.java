package com.maoyan.quickdevelop.system.service.Impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
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
import com.maoyan.quickdevelop.system.service.admin.IDqRolePermissionService;
import com.maoyan.quickdevelop.system.service.IDqUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    private DqUserMapper dqUserMapper;

    @Autowired
    private IDqRolePermissionService idqRolePermissionService;

    QueryWrapper<DqUser> queryWrapper = new QueryWrapper<>();

    @Override
    public List<DqUser> selectAllDqUsers(int pageNum, int pageSize, DqUser dqUser) throws NoSuchFieldException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        MyQueryWrapper<DqUser> myQueryWrapper = new MyQueryWrapper<>();
        PageHelper.startPage(pageNum, pageSize);
        HashMap<String, Object> queryRules = new LinkedHashMap<>();
        queryRules.put("userId", QueryType.EQ);
        queryRules.put("userName", QueryType.LIKE);
        queryRules.put("nickName", QueryType.LIKE);
        queryRules.put("email", QueryType.EQ);
        queryRules.put("phonenumber", QueryType.EQ);
        queryRules.put("role", QueryType.EQ);
        queryRules.put("status", QueryType.EQ);
        dqUser.setStatus("0");
        myQueryWrapper.queryAll(dqUser, queryRules);
        List<DqUser> dqUsers = dqUserMapper.selectList(myQueryWrapper);
        myQueryWrapper.clear();
        return dqUsers;
    }

    @Override
    public DqUser selectDqUserById(Long dqUserId) {
        MyQueryWrapper<DqUser> myQueryWrapper = new MyQueryWrapper<>();
        myQueryWrapper.statuseq();
        myQueryWrapper.eq("user_id", dqUserId);
        DqUser dqUser = dqUserMapper.selectOne(myQueryWrapper);
        myQueryWrapper.clear();
        if (dqUser == null) {
            throw new CustomException("此用户不存在或被封禁", HttpStatus.NOT_FOUND);
        }
        return dqUser;
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
    public DqUser selectDqUserByUserNameAndPassword(String userName, String password) {
        QueryWrapper<DqUser> queryWrapper = new QueryWrapper<>();
        //判断是否是第三方注册
        queryWrapper.eq("user_name",userName);
        if (StringUtils.equals("系统设定的密码",dqUserMapper.selectOne(queryWrapper).getPassWord())){
            throw new CustomException("此用户只能使用第三方登陆",HttpStatus.ERROR);
        }
        queryWrapper.clear();
        queryWrapper.eq("user_name", userName).eq("password", password);
        DqUser dqUser = dqUserMapper.selectOne(queryWrapper);
        queryWrapper.clear();
        if (StringUtils.isNull(dqUser)) {
            throw new CustomException("密码错误", HttpStatus.FORBIDDEN);
        }
        if (StringUtils.equals(dqUser.getStatus(), "1")) {
            log.error("登陆失败");
            throw new CustomException("你的帐号被封禁，请联系管理员", HttpStatus.FORBIDDEN);
        }
        log.info("登陆成功");
        return dqUser;
    }

    @Override
    public int insertDqUser(DqUser dqUser) {
        int insert = dqUserMapper.insert(dqUser);
        return insert;
    }


    @Override
    public int deleteDqUser() {
        Long dqUserId = StpUtil.getLoginIdAsLong();
        int i = dqUserMapper.deleteById(dqUserId);
        return i;
    }

    /**
     * 常规根据ID更改用户
     *
     * @param dqUser
     * @return
     */
    @Override
    public int updateDqUserById(DqUser dqUser) {
        //获取原有的ID来更改
        if (StpUtil.getLoginIdAsLong() != dqUser.getUserId()) {
            throw new CustomException("不能更新其他用户信息", HttpStatus.FORBIDDEN);
        }
        //DqUser dqUser1 = selectDqUserById(dqUserId);
        DqUser oldDqUser = dqUserMapper.selectById(dqUser.getUserId());
        if (StringUtils.isNull(oldDqUser)) {
            throw new CustomException("此用户不存在", HttpStatus.FORBIDDEN);
        }
        oldDqUser.setUserName(dqUser.getUserName());
        oldDqUser.setNickName(dqUser.getNickName());
        oldDqUser.setEmail(dqUser.getEmail());
        oldDqUser.setPhoneNumber(dqUser.getPhoneNumber());
        oldDqUser.setSex(dqUser.getSex());
        oldDqUser.setAvatar(dqUser.getAvatar());
        if (StringUtils.isNotEmpty(dqUser.getPassWord())) {
            oldDqUser.setPassWord(SaSecureUtil.md5(SaSecureUtil.sha1(dqUser.getPassWord())));
        }
        oldDqUser.setSignature(dqUser.getSignature());

        oldDqUser.setLoginDate(DateUtils.getNowDate());
        oldDqUser.setLoginIp(IpUtils.getHostIp());
        oldDqUser.setUpdateTime(DateUtils.getNowDate());
        int i = dqUserMapper.updateById(dqUser);
        if (i <= 0) {
            throw new CustomException("更新失败", HttpStatus.ERROR);
        }
        return i;
    }


    @Override
    public String selectNowDqUserPermission() {
//        StpUtil.isLogin();
//        if (StpUtil.isLogin()){
        long loginIdAsLong = StpUtil.getLoginIdAsLong();
        //获取当前用户的所有信息
        DqUser dqUser = selectDqUserById(loginIdAsLong);
        String roleName = dqUser.getRole();
        DqRolePermission dqRolePermission = idqRolePermissionService.selectDqRolePermissionByName(roleName);
        String permissionName = dqRolePermission.getPermissionName();
        return permissionName;
//        }else {
//            return "guest-permission";
//        }

    }


}
