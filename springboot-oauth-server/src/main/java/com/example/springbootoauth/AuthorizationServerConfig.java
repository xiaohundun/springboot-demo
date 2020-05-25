package com.example.springbootoauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    //使用密码模式才需要AuthenticationManager
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserApprovalHandler userApprovalHandler;

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Override
    public void configure (AuthorizationServerSecurityConfigurer security) throws Exception {
        super.configure(security);
    }

    @Override
    public void configure (ClientDetailsServiceConfigurer clients) throws Exception {
        super.configure(clients);
        //随便先加一个
        clients.inMemory().withClient("demo").secret(passwordEncoder.encode("abc")).authorizedGrantTypes("authorization_code", "password").resourceIds("rg_demo").scopes("read").redirectUris("http://localhost:8081/login/oauth2/code/moxianbao");
    }

    @Bean
    TokenStore tokenStore () {
        return new InMemoryTokenStore();
    }

    @Override
    public void configure (AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        super.configure(endpoints);
        //password grants are switched on by injecting an AuthenticationManager
        endpoints.authenticationManager(authenticationManager).tokenStore(tokenStore()).userApprovalHandler(userApprovalHandler);
        //refresh_token
        //                 .userDetailsService(userDetailsService);
    }

    @Bean
    public ApprovalStore approvalStore () {
        TokenApprovalStore store = new TokenApprovalStore();
        store.setTokenStore(tokenStore);
        return store;
    }

    @Bean
    @Lazy
    @Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
    public UserApprovalHandler userApprovalHandler () throws Exception {
        UserApprovalHandler handler = new UserApprovalHandler();
        handler.setApprovalStore(approvalStore());
        handler.setRequestFactory(new DefaultOAuth2RequestFactory(clientDetailsService));
        handler.setClientDetailsService(clientDetailsService);
        handler.setUseApprovalStore(true);
        return handler;
    }
}

