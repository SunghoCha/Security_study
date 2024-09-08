package com.sh.oauth2.filter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizationSuccessHandler;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;

public class CustomOauth2AuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public static final String DEFAULT_FILTER_PROCESSING_URI = "/oauth2Login/**";
    private DefaultOAuth2AuthorizedClientManager oAuth2AuthorizedClientManager;
    private OAuth2AuthorizedClientRepository oAuth2AuthorizedClientRepository;
    private OAuth2AuthorizationSuccessHandler successHandler;

    public CustomOauth2AuthenticationFilter() {
        super(DEFAULT_FILTER_PROCESSING_URI);
    }

    public CustomOauth2AuthenticationFilter(
            DefaultOAuth2AuthorizedClientManager defaultOAuth2AuthorizedClientManager,
            OAuth2AuthorizedClientRepository oAuth2AuthorizedClientRepository) {

        super(DEFAULT_FILTER_PROCESSING_URI);
        this.defaultOAuth2AuthorizedClientManager = defaultOAuth2AuthorizedClientManager;
        this.oAuth2AuthorizedClientRepository = oAuth2AuthorizedClientRepository;
        this.successHandler = (authorizedClient, principal, attributes) -> {
            oAuth2AuthorizedClientRepository
                    .saveAuthorizedClient(authorizedClient, principal,
                            (HttpServletRequest) attributes.get(HttpServletRequest.class.getName()),
                            (HttpServletResponse) attributes.get(HttpServletResponse.class.getName()));
        };
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        // 아직 인증받지 못했으므로 익명 사용자 토큰
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 권한 부여 요청을 위한 OAuth2AuthorizeRequest
        OAuth2AuthorizeRequest auth2AuthorizeRequest = OAuth2AuthorizeRequest
                .withClientRegistrationId("keycloak")
                .principal(authentication)
                .attribute(HttpServletRequest.class.getName(), request)
                .attribute(HttpServletResponse.class.getName(), response)
                .build();

        OAuth2AuthorizedClient authorizedClient = oAuth2AuthorizedClientManager.authorize(auth2AuthorizeRequest);

        if (authorizedClient != null) {
            
        }

        return null;
    }
}
