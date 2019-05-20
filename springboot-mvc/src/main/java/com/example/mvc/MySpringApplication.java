package com.example.mvc;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

public class MySpringApplication extends SpringApplication {

    private final Log logger = LogFactory.getLog(MySpringApplication.class);

    @Override
    protected void afterRefresh(ConfigurableApplicationContext context, ApplicationArguments args) {
        super.afterRefresh(context, args);
        logger.info("[Spring application refreshed]");
    }

    public MySpringApplication(Class<?>... primarySources) {
        super(primarySources);
    }

}
