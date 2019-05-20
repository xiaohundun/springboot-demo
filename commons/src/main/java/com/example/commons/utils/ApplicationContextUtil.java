package com.example.commons.utils;
/*
 * chou created at 2019-03-27
 * @Description:
 * */

import com.example.commons.interfaces.context.InjectApplicationContext;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component()
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ApplicationContextUtil implements InjectApplicationContext {

    private static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static Object getBean(String s) {
        return applicationContext.getBean(s);
    }

    public static <T> T getBean(Class t) {
        return (T) applicationContext.getBean(t);
    }

    public static String[] getBeanDefinitions() {
        return applicationContext.getBeanDefinitionNames();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        ApplicationContextUtil.applicationContext = applicationContext;
    }
}
