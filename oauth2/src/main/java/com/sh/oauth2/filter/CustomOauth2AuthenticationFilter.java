package com.sh.oauth2.filter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizationSuccessHandler;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
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
        this.oAuth2AuthorizedClientManager = defaultOAuth2AuthorizedClientManager;
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
        log.debug("인증 전 authentication : {}", authentication);
        if (authentication == null) {
            authentication = new AnonymousAuthenticationToken("anonymous", "anonymousUser1",
                            AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS"));
            log.debug("null일 경우 익명 객체 생성 : {}", authentication);
        }

        // 권한 부여 요청을 위한 OAuth2AuthorizeRequest
        OAuth2AuthorizeRequest auth2AuthorizeRequest = OAuth2AuthorizeRequest
                .withClientRegistrationId("keycloak")
                .principal(authentication)
                .attribute(HttpServletRequest.class.getName(), request)
                .attribute(HttpServletResponse.class.getName(), response)
                .build();

        OAuth2AuthorizedClient authorizedClient = oAuth2AuthorizedClientManager.authorize(auth2AuthorizeRequest);

        if (authorizedClient != null) {
            OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService = new DefaultOAuth2UserService();
            ClientRegistration clientRegistration = authorizedClient.getClientRegistration();
            OAuth2AccessToken accessToken = authorizedClient.getAccessToken();
            OAuth2UserRequest oAuth2UserRequest = new OAuth2UserRequest(clientRegistration, accessToken);
            OAuth2User oAuth2User = oAuth2UserService.loadUser(oAuth2UserRequest);

//            // 권한 맵핑 이름 참고용
//            SimpleAuthorityMapper authorityMapper = new SimpleAuthorityMapper();
//            authorityMapper.setPrefix("SCOPE");
//            Set<GrantedAuthority> grantedAuthorities = authorityMapper.mapAuthorities(oAuth2User.getAuthorities());

            OAuth2AuthenticationToken oAuth2AuthenticationToken =
                    new OAuth2AuthenticationToken(oAuth2User, oAuth2User.getAuthorities(), clientRegistration.getRegistrationId());
            log.debug("인증 후 authentication : {}", oAuth2AuthenticationToken);
            SecurityContextHolder.getContext().setAuthentication(oAuth2AuthenticationToken);
            Authentication authentication4Check = SecurityContextHolder.getContext().getAuthentication();
            log.debug("저장 후 authentication : {}", authentication4Check);
            this.successHandler.onAuthorizationSuccess(authorizedClient, oAuth2AuthenticationToken, createAttributes(request, response));

            return oAuth2AuthenticationToken;
        }
        return authentication;
    }

    private Map<String, Object> createAttributes(HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> attributes = new HashMap<>();
        attributes.put(HttpServletRequest.class.getName(), request);
        attributes.put(HttpServletResponse.class.getName(), response);

        return attributes;
    }
}
