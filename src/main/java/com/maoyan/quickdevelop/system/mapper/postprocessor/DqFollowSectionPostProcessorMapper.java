package com.maoyan.quickdevelop.system.mapper.postprocessor;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maoyan.quickdevelop.common.core.domain.postprocessor.DqSectionPostProcessor;
import com.maoyan.quickdevelop.common.core.domain.postprocessor.DqUserPostProcessor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DqFollowSectionPostProcessorMapper {
  /**
   * 查询指定用户关注的版块
   *
   * @return
   */
//  @Select("select dq_section.section_id,\n" +
//          "       dq_section.section_name,\n" +
//          "       dq_section.section_introduce,\n" +
//          "       dq_section.section_logo,\n" +
//          "       dq_section.section_background,\n" +
//          "       dq_section.section_admin_user_id,\n" +
//          "       dq_section.section_weight,\n" +
//          "       dq_section.create_time,\n" +
//          "       dq_section.update_time,\n" +
//          "       dq_user.user_id,\n" +
//          "       dq_user.user_name,\n" +
//          "       dq_user.email,\n" +
//          "       dq_user.phone_number,\n" +
//          "       dq_user.sex,\n" +
//          "       dq_user.avatar,\n" +
//          "       dq_user.password,\n" +
//          "       dq_user.status,\n" +
//          "       dq_user.loginIp,\n" +
//          "       dq_user.loginDate,\n" +
//          "       dq_user.signature,\n" +
//          "       dq_user.grade,\n" +
//          "       dq_user.experience,\n" +
//          "       dq_user.check_param,\n" +
//          "       dq_user.check_status,\n" +
//          "       dq_user.school_id,\n" +
//          "       dq_user.create_time,\n" +
//          "       dq_user.update_time,\n" +
//          "       dur.user_role_id,\n" +
//          "       dur.role_name,\n" +
//          "       dur.user_id,\n" +
//          "       dur.role_status,\n" +
//          "       dur.create_time,\n" +
//          "       dur.update_time,\n" +
//          "       (select count(*) from dq_article where dq_article.section_id = dq_section.section_id) as article_num,\n" +
//          "       (select count(*)\n" +
//          "        from dq_follow_interdqsection)                                                       as follow_num\n" +
//          "from dq_section\n" +
//          "         left join dq_user on dq_section.section_admin_user_id = dq_user.user_id\n" +
//          "         left join dq_user_role dur on dq_section.section_admin_user_id = dur.user_id\n" +
//          "where section_id = any (select community.dq_follow_interdqsection.followed_dqsection_id\n" +
//          "                        from dq_follow_interdqsection\n" +
//          "                        where give_follow_dquser_id = #{dqUserId})")
  List<DqSectionPostProcessor> selectFollowedSectionByUserId(@Param(value = "dqUserId") Long dqUserId);

  /**
   * 根据版块ID查询关注这个版块的用户
   *
   * @param dqSectionId
   * @return
   */
//  @Select(value = "select dq_user.user_id,\n" +
//          "       dq_user.user_name,\n" +
//          "       dq_user.email,\n" +
//          "       dq_user.phone_number,\n" +
//          "       dq_user.sex,\n" +
//          "       dq_user.avatar,\n" +
//          "       dq_user.password,\n" +
//          "       dq_user.status,\n" +
//          "       dq_user.loginIp,\n" +
//          "       dq_user.loginDate,\n" +
//          "       dq_user.signature,\n" +
//          "       dq_user.grade,\n" +
//          "       dq_user.experience,\n" +
//          "       dq_user.check_param,\n" +
//          "       dq_user.check_status,\n" +
//          "       dq_user.school_id,\n" +
//          "       dq_user.create_time,\n" +
//          "       dq_user.update_time,\n" +
//          "       dur.user_role_id,\n" +
//          "       dur.role_name,\n" +
//          "       dur.user_id,\n" +
//          "       dur.role_status,\n" +
//          "       dur.create_time,\n" +
//          "       dur.update_time\n" +
//          "from dq_user\n" +
//          "         left join dq_user_role dur on dq_user.user_id = dur.user_id\n" +
//          "where dq_user.user_id = any (select community.dq_follow_interdqsection.give_follow_dquser_id\n" +
//          "                             from dq_follow_interdqsection\n" +
//          "                             where followed_dqsection_id = #{dqSectionId})")
  List<DqUserPostProcessor> selectGiveFollowDqUserBySectionId(@Param(value = "dqSectionId") Long dqSectionId);

}
