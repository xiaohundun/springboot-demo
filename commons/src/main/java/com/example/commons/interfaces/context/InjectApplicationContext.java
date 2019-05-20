package com.example.commons.interfaces.context;

import org.springframework.context.ApplicationContext;

/*
 * chou created at 2019-03-27
 * @Description:
 * */
@FunctionalInterface
public interface InjectApplicationContext {
    void setApplicationContext(ApplicationContext applicationContext);
}
