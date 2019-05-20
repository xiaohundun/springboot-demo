package com.example.mvc.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.Map;

@Order(Ordered.HIGHEST_PRECEDENCE)
public class ApplicationContextInit implements ApplicationContextInitializer {

    private final Logger logger = LogManager.getLogger(ApplicationContextInit.class);

    /**
     * 不是自动检测并执行的么?
     * spring.factories文件中定义了一系列PropertySource Loaders/Run Listeners/Error Reporters/
     * Application Context Initializers/Application Listeners/Environment Post Processors/
     * Failure Analyzers/FailureAnalysisReporters
     * 1.可以将此类加在spring.factories中的Application Context Initializers后启用(对所有应用生效)
     * 2.calling addInitializers() on SpringApplication before run it(对当前应用生效)
     * 3.通过配置context.initializer.classes(对当前应用生效)
     * 以上配置适用于listeners
     *
     * @param configurableApplicationContext
     */
    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        ConfigurableEnvironment environment = configurableApplicationContext.getEnvironment();
        Map<String, Object> systemEnvironment = environment.getSystemEnvironment();
        for (Map.Entry<String, Object> entry : systemEnvironment.entrySet()) {
            logger.info(entry.getKey() + ":" + entry.getValue());
        }
    }
}
