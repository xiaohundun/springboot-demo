package com.example.mvc.config;

import org.apache.catalina.core.ApplicationServletRegistration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.WebApplicationInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

public class WebApplicationInit implements WebApplicationInitializer {

    private final Logger logger = LogManager.getLogger(WebApplicationInit.class);

    /**
     * 为什么没有加载?
     * issue:https://github.com/spring-projects/spring-boot/issues/522
     * 内嵌Tomcat服务器并不会检测并执行WebApplicationInitializer
     * 说是有意的设计
     * 考虑使用ServletContextInitializer或者ApplicationContextInitializer?
     *
     * @param servletContext
     * @throws ServletException
     */
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        ApplicationServletRegistration registration = (ApplicationServletRegistration) servletContext.getServletRegistration("dispatcherServlet");
        logger.debug("[dispatcherServletRequestMappingHandlerMapping's registration:" + registration.toString() + "]");
        logger.debug("[dispatcherServlet's registration's mappings:" + registration.getMappings() + "]");
    }
}
