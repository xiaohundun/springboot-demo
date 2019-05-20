package com.example.mvc.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

@Component
public class ServletContextInit implements ServletContextInitializer {

    private final Logger logger = LogManager.getLogger(ServletContextInit.class);

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        logger.info("[servlet info:" + servletContext.getServerInfo() + "]");
    }

}
