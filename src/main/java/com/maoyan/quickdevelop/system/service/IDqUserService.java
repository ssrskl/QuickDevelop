package com.maoyan.quickdevelop.system.service;

import cn.dev33.satoken.stp.SaTokenInfo;
import com.maoyan.quickdevelop.common.core.domain.DqUser;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @author 猫颜
 * @date 2021/5/27 22:26
 */

public interface IDqUserService {

    /**
     * 通过邮箱和密码查询用户
     * @param email
     * @param password
     * @return
     */
    public DqUser selectDqUserByEmailAndPassword(String email,String password);
    /**
     * new 查询所有的用户
     * @author 猫颜
     * @date  下午9:01
     * @param dqUser 用户类
     * @return java.util.List<com.maoyan.quickdevelop.common.core.domain.DqUser>
     */
    public List<DqUser> selectAllDqUsers(int pageNum,int pageSize,DqUser dqUser);

    /**
     * 实用型
     * @param dqUserId
     * @return
     */
    public DqUser getDqUserById_Server(Long dqUserId);

    /**
     * 根据ID查询用户
     * @param dqUserId
     * @return
     */
    public DqUser selectDqUserById(Long dqUserId);

    /**
     * 根据用户名查询用户
     * @param userName
     * @return
     */
    public DqUser selectDqUserByUserName(String userName);


    /**
     * 根据账号密码查询用户（登陆功能）
     * @param userName
     * @param password
     * @return
     */
    public DqUser selectDqUserByUserNameAndPassword(String userName,String password);

    /**
     * 添加一个用户（注册方法）
     * @param dqUser
     * @return
     */
    public int insertDqUser(DqUser dqUser);

//    /**
//     * 获取全部的用户
//     * @param pageNum 查询的页数
//     * @param pageSize 每页的大小
//     * @return
//     */
//    public List<DqUser> selectAllDqUsers(int pageNum, int pageSize);

    /**
     * 注销用户
     * @param
     * @return
     */
    public int deleteDqUserMySelf();

    /**
     * 根据ID更改用户
     * @return
     */
    public int updateDqUserSelf(DqUser dqUser);



    /**
     * 获取当前用户的权限
     * @return
     */
    public String selectNowDqUserPermission();


}
