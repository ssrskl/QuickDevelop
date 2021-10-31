package com.maoyan.quickdevelop.system.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maoyan.quickdevelop.common.core.domain.DqUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author 猫颜
 * @date 2021/5/27 22:25
 */
@Mapper
public interface DqUserMapper extends BaseMapper<DqUser> {


//    DqUser selectDquserById(@Param("dqUserId")Long dqUserId);

    /**
     * 查询用户列表
     * @param dqUser 用户
     * @return 用户列表
     */
//    public List<DqUser> selectDqUserList(DqUser dqUser);
}
