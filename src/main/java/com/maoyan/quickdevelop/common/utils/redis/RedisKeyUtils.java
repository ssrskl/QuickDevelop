package com.maoyan.quickdevelop.common.utils.redis;

/**
 * @author 猫颜
 * @date 2021年07月24日 下午3:33
 * 类的作用：TODO redis点赞工具类
 */
public class RedisKeyUtils {
    //保存用户点赞数据的key
    public static final String MAP_KEY_USER_LIKED = "MAP_USER_LIKED";
    //保存文章被点赞数量的key
    public static final String MAP_KEY_ARTICLE_LIKED_COUNT = "MAP_ARTICLE_LIKED_COUNT";

    /**
     * 拼接被点赞的用户id和点赞的人的id作为key。格式 222222::333333
     * @param dqArticleId 被点赞的文章id
     * @param dqUserId 点赞的人的id
     * @return
     */
    public static String getLikedKey(Long dqArticleId, Long dqUserId){
        StringBuilder builder = new StringBuilder();
        builder.append(dqArticleId);
        builder.append("::");
        builder.append(dqUserId);
        return builder.toString();
    }
}