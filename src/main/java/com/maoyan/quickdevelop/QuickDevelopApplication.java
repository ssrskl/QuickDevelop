package com.maoyan.quickdevelop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author: 猫颜
 */
@SpringBootApplication
@EnableTransactionManagement
@ServletComponentScan
public class QuickDevelopApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuickDevelopApplication.class, args);
        System.out.println(
                "\033[1;91m"+"       \\`*-.\n" +
                "\033[1;91m"+"        )  _`-.\n" +
                "\033[1;92m"+"       .  : `. .\n" +
                "\033[1;93m"+"       : _   '  \\\n" +
                "\033[1;94m"+"       ; *` _.   `*-._\n" +
                "\033[1;95m"+"       `-.-'          `-.\n" +
                "\033[1;96m"+"         ;       `       `.\n" +
                "\033[1;91m"+"         :.       .        \\\n" +
                "\033[1;92m"+"         . \\  .   :   .-'   .\n" +
                "\033[1;93m"+"         '  `+.;  ;  '      :\n" +
                "\033[1;94m"+"         :  '  |    ;       ;-.\n" +
                "\033[1;95m"+"         ; '   : :`-:     _.`* ;\n" +
                "\033[1;96m"+"     .*' /  .*' ; .*`- +'  `*'\n" +
                "\033[1;91m"+"      `*-*   `*-*  `*-*'\n" +
                "\033[1;92m"+"      启动成功!!! \033[0m");
    }
}
