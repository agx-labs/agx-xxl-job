package com.xxl.job.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(XxlJobAdminApplication.class);

    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext context = SpringApplication.run(XxlJobAdminApplication.class, args);
        Environment env = context.getEnvironment();
        String applicationName = env.getProperty("spring.application.name", "xxl-job-admin");
        String profile = String.join(",", env.getActiveProfiles());
        String host = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port", "8080");
        String contextPath = env.getProperty("server.servlet.context-path", "");
        logger.info("""
                        \n----------------------------------------------------------
                        \tApplication  : {}
                        \tProfile(s)   : {}
                        \tLocal        : http://localhost:{}{}
                        \tExternal     : http://{}:{}{}
                        ----------------------------------------------------------""",
                applicationName,
                profile,
                port, contextPath,
                host, port, contextPath);
    }

}

