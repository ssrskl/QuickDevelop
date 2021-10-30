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

