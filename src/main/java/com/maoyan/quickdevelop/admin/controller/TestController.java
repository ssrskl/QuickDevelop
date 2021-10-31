package com.maoyan.quickdevelop.admin.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.maoyan.quickdevelop.common.core.AjaxResult;
import com.maoyan.quickdevelop.common.core.domain.DqMessage;
import com.maoyan.quickdevelop.common.core.domain.DqUser;
import com.maoyan.quickdevelop.common.core.domain.postprocessor.DqArticlePostProcesser;
import com.maoyan.quickdevelop.system.mapper.DqMessageMapper;
import com.maoyan.quickdevelop.common.utils.mail.DqMailUtil;
import com.maoyan.quickdevelop.system.mapper.postprocessor.DqArticlePostProcessorMapper;
import com.maoyan.quickdevelop.system.service.IDqUserService;
import com.maoyan.quickdevelop.system.service.sseemitter.SseEmitterServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.Map;

/**
 * @author 猫颜
 * @date 2021/5/27 15:56
 */
@RestController
@RequestMapping("/cao")
public class TestController {

    private static final Logger log = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private IDqUserService iDqUserService;
    @Autowired
    private DqArticlePostProcessorMapper dqArticlePostProcessorMapper;
    @Autowired
    private DqMessageMapper dqMessageMapper;
    @Autowired
    private DqMailUtil dqMailUtil;
    @GetMapping("/hello")
    @ResponseBody
    public String test() {
        log.info("Hello");
        return "Hello World!";
    }

    @GetMapping("/login")
    public String login(String username, String password) {
        // 此处仅作模拟示例，真实项目需要从数据库中查询数据进行比对
        if ("zhang".equals(username) && "123456".equals(password)) {
            StpUtil.setLoginId(10001, true);
            return "登录成功";
        }
        return "登录失败";
    }


    @GetMapping("/getToken")
    public String getToken() {
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        return tokenInfo.toString();
    }

    @GetMapping("/check")
    public String checkPermission() {
        boolean b = StpUtil.hasPermission("user-delete");
        System.out.println("结果为：" + b);
        return "hello";
    }

    @GetMapping("/getsession")
    public String getSession() {

        SaSession session = StpUtil.getSession();
        session.setAttribute("name", "maoyan");
        Map<String, Object> dataMap = session.getDataMap();
        for (String map :
                dataMap.keySet()) {
            System.out.println(map);
        }
        return "getSession";
    }

    @GetMapping("/getuserbyid/{dqUserId}")
    public String getUser(@PathVariable Long dqUserId) {
        DqUser dqUser = iDqUserService.selectDqUserById(dqUserId);
        if (dqUser == null) {
            System.out.println("空");
            return "无";
        } else {
            return dqUser.toString();
        }
    }


    @GetMapping("/md5")
    public String testMD5() {
        String md5 = SaSecureUtil.md5(SaSecureUtil.sha1("123456"));
        //String hash = Md5Utils.hash("123456");
        return md5;
    }


    @GetMapping("/per")
    public String checkPermissions(){
        if (StpUtil.hasPermission("*"))
        {
            return "你有超管权限";
        }else{
            return "你没有超管权限";
        }
    }

    @GetMapping("/dqmessage")
    public AjaxResult testDqMessageMapper(){
        DqMessage dqMessage = dqMessageMapper.selectById(1L);
        System.out.println(dqMessage);
        return AjaxResult.success("查询成功",dqMessage);
    }

    @GetMapping("/connect/{userId}")
    public SseEmitter connect(@PathVariable String userId){
        SseEmitter connect = SseEmitterServer.connect(userId);
        return connect;
    }
    @GetMapping("/send/{userId}")
    public AjaxResult send(@PathVariable String userId){
        SseEmitterServer.sendMessage(userId);
        return AjaxResult.success("success");
    }

    /**
     * 测试邮件发送功能
     */
    @GetMapping("/sendmail")
    public AjaxResult sendMail(){
        dqMailUtil.sendMail("标题","1071352028@qq.com","hello");
        return AjaxResult.success("adas");
    }
}