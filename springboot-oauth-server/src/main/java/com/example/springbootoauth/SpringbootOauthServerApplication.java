package com.example.springbootoauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Collections;
import java.util.Map;

@RestController
@SpringBootApplication
@EnableWebSecurity(debug = true)
//@EnableWebMvc
public class SpringbootOauthServerApplication extends WebSecurityConfigurerAdapter {

    @GetMapping("/user")
    public HttpEntity<Map<String, String>> user (@AuthenticationPrincipal Principal principal) {
        return new ResponseEntity<>(Collections.singletonMap("id", principal.getName()), HttpStatus.OK);
    }

    @Override
    protected void configure (HttpSecurity http) throws Exception {
        // @formatter:off
        http.authorizeRequests().antMatchers("/login.jsp").permitAll().anyRequest().hasRole("USER").and().exceptionHandling().accessDeniedPage("/login.jsp?authorization_error=true").and().csrf().requireCsrfProtectionMatcher(new AntPathRequestMatcher("/oauth/authorize")).disable().logout().logoutUrl("/logout").logoutSuccessUrl("/login.jsp").and().formLogin().loginProcessingUrl("/login")
            //                .successForwardUrl("/index.jsp")
            .failureUrl("/login.jsp?authentication_error=true").loginPage("/login.jsp").and()
            // 关闭nosniff
            .headers().contentTypeOptions().disable();

        // @formatter:on
    }

    @Override
    public void configure (WebSecurity web) {
        web.ignoring().antMatchers("/webjars/**", "/favicon.ico", "/images/**");
    }

    @Override
    protected void configure (AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("balala").password("$2a$10$QOhLotan6kumoHRp8Z2ajOZfBhKgaMvgqeaxrSXxrKN2FgR3hlJ9.").roles("USER").and().withUser("moxianbao").password("$2a$10$RpHbnvFdFoUgpK89iVnSzugfqZAfDeKvVKp3LoVBxCj6fuwqtdBlO").roles("USER");
    }

    public static void main (String[] args) {
        SpringApplication.run(SpringbootOauthServerApplication.class, args);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean () throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder () {
        return new BCryptPasswordEncoder();
    }


}
