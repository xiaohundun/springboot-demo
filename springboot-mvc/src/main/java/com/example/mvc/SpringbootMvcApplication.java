package com.example.mvc;

import com.example.commons.interfaces.context.InjectApplicationContext;
import com.example.mvc.config.ApplicationContextInit;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

import java.util.Map;

@SpringBootApplication
//  添加子模块的配置文件,但也不能丢了本模块的配置
@PropertySource(value = {"classpath:application.properties", "classpath:redis.properties"})
//  扫描子模块的包,但也不能少了本模块包的扫描
@ComponentScan(basePackages = {"com.example.mvc", "com.example.redis.beans", "com.example.commons"})
public class SpringbootMvcApplication {

    public static void main(String[] args) {
        MySpringApplication application = new MySpringApplication(SpringbootMvcApplication.class);
        application.addInitializers(new ApplicationContextInit());
        ConfigurableApplicationContext ctx = application.run(args);
        Map<String, InjectApplicationContext> injectApplicationContextMap = ctx.getBeansOfType(InjectApplicationContext.class);
        injectApplicationContextMap.forEach((k, v) -> {
            v.setApplicationContext(ctx);
        });
//        ConfigurableEnvironment environment = ctx.getEnvironment();
//        MutablePropertySources mutablePropertySources = environment.getPropertySources();
//        for (org.springframework.core.env.PropertySource<?> mutablePropertySource : mutablePropertySources) {
//            System.out.println(mutablePropertySource.getName() + ":" + mutablePropertySource.getSource());
//        }
//        ConfigurableApplicationContext applicationContext = SpringApplication.run(DemoApplication.class, args);
    }

}
