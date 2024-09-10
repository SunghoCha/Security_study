package com.sh.oauth2.config;

import com.sh.oauth2.filter.CustomOauth2AuthenticationFilter;
import com.sh.oauth2.filter.SecurityContextLoggingFilter;
import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
public class OAuth2ClientConfig {

//    private final DefaultOAuth2AuthorizedClientManager auth2AuthorizedClientManager;
//    private final OAuth2AuthorizedClientRepository auth2AuthorizedClientRepository;

//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web -> web.ignoring().requestMatchers
//                ("/static/js/**", "/static/images/**", "/static/css/**","/static/scss/**"));
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authRequest -> authRequest
                        .requestMatchers("/static/js/**", "/static/images/**", "/static/css/**","/static/scss/**").permitAll()
                        .requestMatchers("/").permitAll()
                        .anyRequest().authenticated())
                .oauth2Login(Customizer.withDefaults())
                .logout(logout -> logout
                        .logoutSuccessUrl("/"));

        return http.build();
    }


//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/", "/oauth2Login", "/client", "/home", "/favicon.ico").permitAll()
//                        .anyRequest().authenticated()
//                )
//                .oauth2Client(Customizer.withDefaults())
//                .addFilterBefore(new SecurityContextLoggingFilter(), UsernamePasswordAuthenticationFilter.class)
//                .addFilterBefore(customOAuth2AuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//    }
//
//    private CustomOauth2AuthenticationFilter customOAuth2AuthenticationFilter() {
//        CustomOauth2AuthenticationFilter authenticationFilter =
//                new CustomOauth2AuthenticationFilter(auth2AuthorizedClientManager, auth2AuthorizedClientRepository);
//
//        authenticationFilter.setAuthenticationSuccessHandler(((request, response, authentication) -> {
//            response.sendRedirect("/home"); // 원래는 client-credential할 때 리다이렉트 사용 안함
//        }));
//        return authenticationFilter;
//    }
}
