package com.maoyan.quickdevelop.system.service.admin;

import com.maoyan.quickdevelop.common.core.domain.DqArticle;
import com.maoyan.quickdevelop.common.core.domain.DqUser;
import org.apache.ibatis.annotations.Param;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface IAdminDqUserService {

    /**
     * 
     * @author 猫颜
     * @date  上午11:37
     * @param pageNum
     * @param pageSize
     * @param dqUser
     * @return java.util.List<com.maoyan.quickdevelop.common.core.domain.DqArticle>
     */
    public List<DqUser> selectDqUserList(int pageNum, int pageSize, DqUser dqUser) throws NoSuchFieldException, InvocationTargetException, NoSuchMethodException, IllegalAccessException;

    /**
     * 根据Id删除用户
     * @param dqUserId
     * @return
     */
    public int deleteDqUserById(Long dqUserId);

    /**
     * 通过id封号
     * @param dqUserId
     * @return
     */
    public int banUserById(Long dqUserId);

    /**
     * 通过id解封
     * @param dqUserId
     * @return
     */
    public int unsealUserById(Long dqUserId);


    /**
     * 根据ID更改用户
     * @param dqUser
     * @return
     */
    public int updateDqUser(DqUser dqUser);

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
     * 添加一个用户
     * @param dqUser
     * @return
     */
    public int insertDqUser(DqUser dqUser);

    /**
     * 注销自己用户
     * @param
     * @return
     */
    public int deleteDqUser();



    /**
     * 获取当前用户的权限
     * @return
     */
    public String selectNowDqUserPermission();

}
