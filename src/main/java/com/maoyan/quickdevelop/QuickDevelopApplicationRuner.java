package com.maoyan.quickdevelop;

import cn.dev33.satoken.SaManager;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author 猫颜
 * @date 2021/5/27 16:23
 */
@Component
@Order(1)
public class QuickDevelopApplicationRuner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("Sa-token的配置为:" + SaManager.getConfig());
    }
}