package com.maoyan.quickdevelop.system.service;

import com.maoyan.quickdevelop.common.core.domain.DqOperLog;

import java.util.List;

/**
 * @author 猫颜
 * @date 2021年07月27日 下午5:26
 * 类的作用：TODO
 */
public interface IDqOperLogService {


    /***
     * @Author: 猫颜
     * @Description: 通用查询日志操作
     * @DateTime: 上午8:31 2021/7/28
     * @Params:
     * @param dqOperLog
     * @Return
     * @return java.util.List<com.maoyan.quickdevelop.common.core.domain.DqOperLog>
     */
    public List<DqOperLog> selectDqOperLogs(int pageNum, int pageSize, DqOperLog dqOperLog);

    /***
     * @Author: 猫颜
     * @Description: 添加操作日志
     * @DateTime: 下午5:26 2021/7/27
     * @Params:
     * @Return
     * @return int
     */
    public int insertDqOperLog(DqOperLog dqOperLog);

}
