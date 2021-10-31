----查询指定ID用户关注的用户
select dq_user.user_id,
       user_name,
       email,
       phone_number,
       sex,
       avatar,
       status,
       loginIp,
       loginDate,
       signature,
       grade,
       experience,
       check_param,
       check_status,
       dq_user.create_time,
       dq_user.update_time,
       user_role_id,
       role_name,
       dur.user_id,
       role_status,
       dur.create_time,
       dur.update_time
from dq_user
         left join dq_user_role dur on dq_user.user_id = dur.user_id
where dq_user.user_id = any(
select community.dq_follow_interdquser.followed_dquser_id
from dq_follow_interdquser
where give_follow_dquser_id = 1)

          # 通用查询版块信息
select dq_section.section_id,
       dq_section.section_name,
       dq_section.section_introduce,
       dq_section.section_logo,
       dq_section.section_background,
       dq_section.section_admin_user_id,
       dq_section.section_weight,
       dq_section.create_time,
       dq_section.update_time,
       dq_user.user_id,
       dq_user.user_name,
       dq_user.email,
       dq_user.phone_number,
       dq_user.sex,
       dq_user.avatar,
       dq_user.status,
       dq_user.loginIp,
       dq_user.loginDate,
       dq_user.signature,
       dq_user.grade,
       dq_user.experience,
       dq_user.check_param,
       dq_user.check_status,
       dq_user.create_time,
       dq_user.update_time,
       dur.user_role_id,
       dur.role_name,
       dur.user_id,
       dur.role_status,
       dur.create_time,
       dur.update_time,
       (select count(*) from dq_article where dq_article.section_id = dq_section.section_id) as article_num,
                                            (select count(*)
from dq_follow_interdqsection
where dq_follow_interdqsection.followed_dqsection_id = dq_section.section_id)        as follow_num
from dq_section
    left join dq_user on dq_section.section_admin_user_id = dq_user.user_id
    left join dq_user_role dur on dq_section.section_admin_user_id = dur.user_id
order by dq_section.section_id ASC , follow_num DESC


    # 查询指定用户关注的版块
select dq_section.section_id,
       dq_section.section_name,
       dq_section.section_introduce,
       dq_section.section_logo,
       dq_section.section_background,
       dq_section.section_admin_user_id,
       dq_section.section_weight,
       dq_section.create_time,
       dq_section.update_time,
       dur.user_role_id,
       dur.role_name,
       dur.user_id,
       dur.role_status,
       dur.create_time,
       dur.update_time
from dq_section
         left join dq_user_role dur on dq_section.section_admin_user_id = dur.user_id
where section_id = any (select community.dq_follow_interdqsection.followed_dqsection_id
                        from dq_follow_interdqsection
                        where give_follow_dquser_id = 1)

          # 根据版块Id查询关注这个版块的用户
select dq_user.user_id,
       dq_user.user_name,
       dq_user.email,
       dq_user.phone_number,
       dq_user.sex,
       dq_user.avatar,
       dq_user.password,
       dq_user.status,
       dq_user.loginIp,
       dq_user.loginDate,
       dq_user.signature,
       dq_user.grade,
       dq_user.experience,
       dq_user.check_param,
       dq_user.check_status,
       dq_user.create_time,
       dq_user.update_time,
       dur.user_role_id,
       dur.role_name,
       dur.user_id,
       dur.role_status,
       dur.create_time,
       dur.update_time
from dq_user
         left join dq_user_role dur on dq_user.user_id = dur.user_id
where dq_user.user_id = any (select community.dq_follow_interdqsection.give_follow_dquser_id
                             from dq_follow_interdqsection
                             where followed_dqsection_id = 1)