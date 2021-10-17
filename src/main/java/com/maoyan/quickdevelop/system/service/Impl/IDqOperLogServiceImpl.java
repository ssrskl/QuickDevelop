package com.maoyan.quickdevelop.system.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.maoyan.quickdevelop.common.utils.StringUtils;
import com.maoyan.quickdevelop.common.constant.HttpStatus;
import com.maoyan.quickdevelop.common.core.domain.DqOperLog;
import com.maoyan.quickdevelop.common.exception.CustomException;
import com.maoyan.quickdevelop.system.mapper.DqOperLogMapper;
import com.maoyan.quickdevelop.system.service.IDqOperLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 猫颜
 * @date 2021年07月27日 下午5:27
 * 类的作用：TODO
 */
@Transactional
@Service
@Slf4j
public class IDqOperLogServiceImpl implements IDqOperLogService {
    @Autowired
    private DqOperLogMapper dqOperLogMapper;
    QueryWrapper<DqOperLog> queryWrapper = new QueryWrapper<>();

    @Override
    public List<DqOperLog> selectDqOperLogs(int pageNum, int pageSize, DqOperLog dqOperLog) {
        PageHelper.startPage(pageNum, pageSize);
        queryWrapper.lambda().eq(StringUtils.isNotNull(dqOperLog.getOperId()), DqOperLog::getOperId, dqOperLog.getOperId())
                .eq(StringUtils.isNotNull(dqOperLog.getTitle()), DqOperLog::getTitle, dqOperLog.getTitle())
                .eq(StringUtils.isNotNull(dqOperLog.getBusinessType()), DqOperLog::getBusinessType, dqOperLog.getBusinessType())
                .eq(StringUtils.isNotNull(dqOperLog.getMethod()), DqOperLog::getMethod, dqOperLog.getMethod())
                .eq(StringUtils.isNotNull(dqOperLog.getRequestMethod()), DqOperLog::getRequestMethod, dqOperLog.getRequestMethod())
                .eq(StringUtils.isNotNull(dqOperLog.getOperUserName()), DqOperLog::getOperUserName, dqOperLog.getOperUserName())
                .eq(StringUtils.isNotNull(dqOperLog.getOperUrl()), DqOperLog::getOperUrl, dqOperLog.getOperUrl())
                .eq(StringUtils.isNotNull(dqOperLog.getOperIp()), DqOperLog::getOperIp, dqOperLog.getOperIp())
                .eq(StringUtils.isNotNull(dqOperLog.getStatus()), DqOperLog::getStatus, dqOperLog.getStatus())
                .like(StringUtils.isNotNull(dqOperLog.getErrorMsg()), DqOperLog::getErrorMsg, dqOperLog.getErrorMsg());
        List<DqOperLog> dqOperLogs = dqOperLogMapper.selectList(queryWrapper);
        queryWrapper.clear();
        if (StringUtils.isEmpty(dqOperLogs)){
            throw new CustomException("未查询到操作日志", HttpStatus.NOT_FOUND);
        }
        return dqOperLogs;
    }

    @Override
    public int insertDqOperLog(DqOperLog dqOperLog) {
        int insert = dqOperLogMapper.insert(dqOperLog);
        if (insert <= 0) {
            log.info("日志操作添加失败");
        }
        log.info("日志操作添加成功");
        return insert;
    }
}