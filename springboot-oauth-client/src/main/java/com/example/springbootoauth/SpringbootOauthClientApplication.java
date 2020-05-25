package com.example.springbootoauth;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@SpringBootApplication
public class SpringbootOauthClientApplication extends WebSecurityConfigurerAdapter {

    @GetMapping("/user")
    public Map<String, Object> user (@AuthenticationPrincipal OAuth2User principal) {
        return Collections.singletonMap("name", principal.getAttribute("name"));
    }

    @GetMapping("/error")
    public String error (HttpServletRequest request) {
        String message = (String) request.getSession().getAttribute("error.message");
        request.getSession().removeAttribute("error.message");
        return message;
    }

    @Override
    protected void configure (HttpSecurity http) throws Exception {
        // @formatter:off
        http.authorizeRequests(a -> a.antMatchers("/", "/favicon.ico", "/error", "/webjars/**").permitAll().anyRequest().authenticated()).exceptionHandling(e -> e.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))).oauth2Login();
        http.logout(l -> l.logoutSuccessUrl("/").permitAll()).csrf(c -> c.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()));
        // @formatter:on
    }

    public static void main (String[] args) {
        SpringApplication.run(SpringbootOauthClientApplication.class, args);
    }

    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2UserService () {
        DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
        return request -> {
            OAuth2User user = delegate.loadUser(request);
            if (!"github".equals(request.getClientRegistration().getRegistrationId())) {
                return user;
            }

            OAuth2AuthorizedClient client = new OAuth2AuthorizedClient(request.getClientRegistration(), user.getName(), request.getAccessToken());
            String url = user.getAttribute("organizations_url");
            if (url != null) {
                try {
                    URL urlurl = new URL(url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) urlurl.openConnection();
                    httpURLConnection.setRequestMethod(HttpMethod.GET.name());
                    httpURLConnection.connect();
                    if (httpURLConnection.getResponseCode() == 200) {
                        try (InputStream inputStream = httpURLConnection.getInputStream(); BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
                            String result;
                            StringBuilder sb = new StringBuilder();
                            while ((result = bufferedReader.readLine()) != null) {
                                sb.append(result);
                            }
                            ObjectMapper objectMapper = new ObjectMapper();
                            List r = objectMapper.readValue(sb.toString(), List.class);
                            if (r.size() != 0) {
                                for (Object o : r) {
                                    if (o.equals("my_team")) {
                                        return user;
                                    }
                                }
                            }
                        }
                    }
                } catch (IOException e) {
                    return user;
                }

            }
            throw new OAuth2AuthenticationException(new OAuth2Error("invalid_token", "Not in Team", ""));
        };
    }

}
