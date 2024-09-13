package com.sh.oauth2.service;

import com.sh.oauth2.converters.ProviderUserConverter;
import com.sh.oauth2.converters.ProviderUserRequest;
import com.sh.oauth2.model.ProviderUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CustomOAuth2UserService extends AbstractOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserService userService;

    public CustomOAuth2UserService(ProviderUserConverter<ProviderUserRequest, ProviderUser> providerUserConverter, UserService userService) {
        super(providerUserConverter);
        this.userService = userService;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        ClientRegistration clientRegistration = userRequest.getClientRegistration();
        ProviderUserRequest providerUserRequest = new ProviderUserRequest(clientRegistration, oAuth2User);
        ProviderUser providerUser = createProviderUser(providerUserRequest);
        userService.register(clientRegistration.getRegistrationId(), providerUser);

        return oAuth2User;
    }
}
