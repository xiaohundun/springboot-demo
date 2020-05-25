package com.example.springbootoauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    TokenStore tokenStore;

    @Override
    public void configure (ResourceServerSecurityConfigurer resources) throws Exception {
        super.configure(resources);
        resources.resourceId("rg_demo").stateless(false).tokenStore(tokenStore);
        //        .authenticationEntryPoint();
    }

    @Override
    public void configure (HttpSecurity http) throws Exception {
        // security DSL
        http.requestMatchers().antMatchers("/user/**").and().authorizeRequests().antMatchers("/user").access("#oauth2.hasScope('read')");
    }
}
