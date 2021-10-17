package com.maoyan.quickdevelop.system.service;

import com.maoyan.quickdevelop.common.core.domain.DqUser;

import java.util.List;

/**
 * @author 猫颜
 * @date 2021年07月24日 下午3:14
 * 类的作用：TODO
 */
public interface IDqGivelikeService {

    /***
     * @Author: 猫颜
     * @Description: 给文章点赞
     * @DateTime: 下午3:17 2021/7/24
     * @Params:
     * @param dqArticleId
     * @Return
     * @return int
     */
    public int giveALike(Long dqArticleId);

    /***
     * @Author: 猫颜
     * @Description: 给文章取消点赞
     * @DateTime: 下午3:18 2021/7/24
     * @Params:
     * @param dqArticleId
     * @Return
     * @return int
     */
    public int unlike(Long dqArticleId);

    /***
     * @Author: 猫颜
     * @Description: 查看当前用户对指定文章的点赞状态
     * @DateTime: 下午5:44 2021/7/24
     * @Params:
     * @Return
     * @return int
     */
    public int checkStatusForDqArticle(Long dqArticleId);

    /***
     * @Author: 猫颜
     * @Description: 获取指定文章的点赞数量
     * @DateTime: 下午8:24 2021/7/24
     * @Params:
     * @param dqArticleId
     * @Return
     * @return int
     */
    public int getLikeNumberInDqArticle(Long dqArticleId);

    /***
     * @Author: 猫颜
     * @Description: 给文章点赞的所有用户
     * @DateTime: 上午7:07 2021/7/25
     * @Params:
     * @Return
     * @return java.util.List<com.maoyan.quickdevelop.common.core.domain.DqUser>
     */
    public List<DqUser> likedDqUserInfoInDqArticle(int pageNum, int pageSize, Long dqArticleId);
}
