package com.maoyan.quickdevelop.frameworks.exception;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import com.maoyan.quickdevelop.common.utils.StringUtils;
import com.maoyan.quickdevelop.common.constant.HttpStatus;
import com.maoyan.quickdevelop.common.core.AjaxResult;
import com.maoyan.quickdevelop.common.exception.BaseException;
import com.maoyan.quickdevelop.common.exception.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.security.auth.login.AccountExpiredException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.file.AccessDeniedException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理器
 */

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 重复异常
     * Spring的dao为了统一处理，屏蔽了与特定技术相关的异常,例如SQLException或HibernateException,
     * 抛出的异常是与特定技术无关的org.springframework.dao.DataAccessException类的子类。
     *
     * @param e
     * @return
     */
    @ExceptionHandler(DataAccessException.class)
    public AjaxResult sqlIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e) {
        String errMsg = e.getMessage();
        System.out.println("错误信息：" + errMsg);
        if (StringUtils.isNotBlank(errMsg) && errMsg.contains("user_name")) {
            return AjaxResult.error("用户名重复", e.getErrorCode());
        } else if (StringUtils.isNotBlank(errMsg) && errMsg.contains("email")) {
            return AjaxResult.error("邮箱重复", e.getErrorCode());
        } else if (StringUtils.isNotBlank(errMsg) && errMsg.contains("phone_number")) {
            return AjaxResult.error("手机号重复", e.getErrorCode());
        } else if (StringUtils.isNotBlank(errMsg) && errMsg.contains("IllegalStateException")) {
            return AjaxResult.error("SQL完整性异常", e.getErrorCode());
        }
        return AjaxResult.error("数据重复", e.getErrorCode());

    }

    /**
     * 基础异常
     */
    @ExceptionHandler(BaseException.class)
    public AjaxResult baseException(BaseException e) {
        return AjaxResult.error(e.getMessage());
    }

    /**
     * token异常
     *
     * @param nle
     * @param request
     * @param response
     * @return
     */
    @ExceptionHandler(NotLoginException.class)
    public AjaxResult handlerNotLoginException(NotLoginException nle, HttpServletRequest request, HttpServletResponse response) {

        // 打印堆栈，以供调试
        nle.printStackTrace();
        // 判断场景值，定制化异常信息
        String message = "";
        if (nle.getType().equals(NotLoginException.NOT_TOKEN)) {
            message = "未提供Token";
            log.error(message);
        } else if (nle.getType().equals(NotLoginException.INVALID_TOKEN)) {
            message = "Token无效";
            return AjaxResult.error(NotLoginException.INVALID_TOKEN, message);
        } else if (nle.getType().equals(NotLoginException.TOKEN_TIMEOUT)) {
            message = "Token已过期";
        } else if (nle.getType().equals(NotLoginException.BE_REPLACED)) {
            message = "已被顶下线";
        } else if (nle.getType().equals(NotLoginException.KICK_OUT)) {
            message = "已被踢下线";
        } else {
            message = "当前会话未登录";
        }

        // 返回给前端
        return AjaxResult.error(message);
    }

    /**
     * NotPermissionException 对象可通过 getLoginKey() 方法获取具体是哪个 StpLogic 抛出的异常
     *
     * @param npe
     * @return
     */
    @ExceptionHandler(NotPermissionException.class)
    public AjaxResult handlerNotPermissionException(NotPermissionException npe) {
        return AjaxResult.error("无此权限：" + npe.getCode(), npe.getLoginKey());
    }

//    /**
//     * 没有角色异常（就是权限等级不够）
//     * @param npe
//     * @return
//     */
//    @ExceptionHandler(NotRoleException.class)
//    public AjaxResult handlerNotRoleException(NotRoleException npe){
//        return AjaxResult.error("")
//    }

    /**
     * 业务异常
     */
    @ExceptionHandler(CustomException.class)
    public AjaxResult businessException(CustomException e) {
        if (StringUtils.isNull(e.getCode())) {
            return AjaxResult.error(e.getMessage());
        }
        return AjaxResult.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public AjaxResult handlerNoFoundException(Exception e) {
        log.error(e.getMessage(), e);
        return AjaxResult.error(HttpStatus.NOT_FOUND, "路径不存在，请检查路径是否正确");
    }

    @ExceptionHandler(AccessDeniedException.class)
    public AjaxResult handleAuthorizationException(AccessDeniedException e) {
        log.error(e.getMessage());
        return AjaxResult.error(HttpStatus.FORBIDDEN, "没有权限，请联系管理员授权");
    }

    @ExceptionHandler(AccountExpiredException.class)
    public AjaxResult handleAccountExpiredException(AccountExpiredException e) {
        log.error(e.getMessage(), e);
        return AjaxResult.error(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public AjaxResult handleException(Exception e) {
        log.error(e.getMessage(), e);
        return AjaxResult.error(e.getMessage());
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(BindException.class)
    public AjaxResult validatedBindException(BindException e) {
        log.error(e.getMessage(), e);
        String message = e.getAllErrors().get(0).getDefaultMessage();
        return AjaxResult.error(message);
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object validExceptionHandler(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        return AjaxResult.error(message);
    }

    /***
     * @Author: 猫颜
     * @Description: 请求参数异常
     * @DateTime: 下午3:54 2021/8/4
     * @Params:
     * @param e
     * @Return
     * @return com.maoyan.quickdevelop.common.core.AjaxResult
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public AjaxResult httpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        log.error(e.getMessage(), e);
        return AjaxResult.error("请求参数异常", e.getMessage());
    }

}
