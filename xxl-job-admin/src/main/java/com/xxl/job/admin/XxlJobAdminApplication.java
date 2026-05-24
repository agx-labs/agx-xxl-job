package com.xxl.job.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;

/**
 * @author xuxueli 2018-10-28 00:38:13
 */
@SpringBootApplication
public class XxlJobAdminApplication {

    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext context = SpringApplication.run(XxlJobAdminApplication.class, args);
        Environment env = context.getEnvironment();

        String port        = env.getProperty("server.port", "8080");
        String contextPath = env.getProperty("server.servlet.context-path", "");
        String appName     = env.getProperty("spring.application.name", "xxl-job-admin");
        String profile     = String.join(",", env.getActiveProfiles());
        String host        = InetAddress.getLocalHost().getHostAddress();

        String localUrl    = "http://localhost:" + port + contextPath;
        String externalUrl = "http://" + host + ":" + port + contextPath;

        // 使用 System.out 输出，避免 logback ANSI 颜色码干扰 IDE 超链接识别
        System.out.printf("""
                %n----------------------------------------------------------
                \tApplication  : %s
                \tProfile(s)   : %s
                \tLocal        : %s
                \tExternal     : %s
                ----------------------------------------------------------
                %n""",
                appName, profile, localUrl, externalUrl);
    }

}

