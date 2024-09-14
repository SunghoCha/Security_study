package com.sh.oauth2.config;

import com.sh.oauth2.filter.CustomOauth2AuthenticationFilter;
import com.sh.oauth2.filter.SecurityContextLoggingFilter;
import com.sh.oauth2.service.CustomOAuth2UserService;
import com.sh.oauth2.service.CustomOidcUserService;
import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
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

    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomOidcUserService customOidcUserService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authRequest -> authRequest
                        .requestMatchers("/api/user").hasAnyAuthority("SCOPE_profile", "SCOPE_email")
                        .requestMatchers("/api/oidc").hasAnyAuthority("SCOPE_openid")
                        .requestMatchers("/static/js/**", "/static/images/**", "/static/css/**","/static/scss/**").permitAll()
                        .requestMatchers("/").permitAll()
                        .anyRequest().authenticated())
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint((userInfoEndpointConfig -> userInfoEndpointConfig
                                .userService(customOAuth2UserService)
                                .oidcUserService(customOidcUserService))))
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
