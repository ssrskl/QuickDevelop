package com.maoyan.quickdevelop.system.mapper.postprocessor;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maoyan.quickdevelop.common.core.domain.DqFollowUser;
import com.maoyan.quickdevelop.common.core.domain.DqUser;
import com.maoyan.quickdevelop.common.core.domain.postprocessor.DqUserPostProcessor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DqFollowUserPostProcessorMapper extends BaseMapper<DqFollowUser>{
  /**
   * 查询指定用户关注的人
   *
   * @param dqUserId
   * @return
   */
//  @Select("select dq_user.user_id,\n" +
//          "       user_name,\n" +
//          "       email,\n" +
//          "       phone_number,\n" +
//          "       sex,\n" +
//          "       avatar,\n" +
//          "       status,\n" +
//          "       loginIp,\n" +
//          "       loginDate,\n" +
//          "       signature,\n" +
//          "       grade,\n" +
//          "       experience,\n" +
//          "       check_param,\n" +
//          "       check_status,\n" +
//          "       dq_user.school_id,\n" +
//          "       dq_user.create_time,\n" +
//          "       dq_user.update_time,\n" +
//          "       user_role_id,\n" +
//          "       role_name,\n" +
//          "       dur.user_id,\n" +
//          "       role_status,\n" +
//          "       dur.create_time,\n" +
//          "       dur.update_time\n" +
//          "from dq_user\n" +
//          "         left join dq_user_role dur on dq_user.user_id = dur.user_id\n" +
//          "where dq_user.user_id = any(select community.dq_follow_interdquser.followed_dquser_id\n" +
//          "                         from dq_follow_interdquser\n" +
//          "                         where give_follow_dquser_id = #{dqUserId})")
  List<DqUserPostProcessor> selectFollowedDqUserByUserId(@Param(value = "dqUserId") Long dqUserId);

  /**
   * 查询指定用户的粉丝
   * @param dqUserId
   * @return
   */
  List<DqUserPostProcessor> selectGiveFollowDqUserByUserId(@Param(value = "dqUserId") Long dqUserId);
}
