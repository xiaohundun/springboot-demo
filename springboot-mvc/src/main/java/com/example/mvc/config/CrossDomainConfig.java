package com.example.mvc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 设置允许跨域
 */
@Configuration
public class CrossDomainConfig {
    /**
     * 允许任何域名使用
     * 允许任何头
     * 任何方法
     */
    private CorsConfiguration buildConfig() {
        CorsConfiguration c = new CorsConfiguration();
        c.addAllowedHeader("*");
        c.addAllowedMethod("*");
        c.addAllowedOrigin("*");
        return c;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig());
        return new CorsFilter(source);
    }
}
