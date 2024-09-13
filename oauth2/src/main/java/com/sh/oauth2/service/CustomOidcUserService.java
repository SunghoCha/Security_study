package com.sh.oauth2.service;

import com.sh.oauth2.converters.ProviderUserConverter;
import com.sh.oauth2.converters.ProviderUserRequest;
import com.sh.oauth2.model.ProviderUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional

public class CustomOidcUserService extends AbstractOAuth2UserService implements OAuth2UserService<OidcUserRequest, OidcUser> {

    private final UserService userService;

    public CustomOidcUserService(ProviderUserConverter<ProviderUserRequest, ProviderUser> providerUserConverter, UserService userService) {
        super(providerUserConverter);
        this.userService = userService;
    }

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUserService delegate = new OidcUserService();
        OidcUser oidcUser = delegate.loadUser(userRequest);

        ClientRegistration clientRegistration = userRequest.getClientRegistration();
        ProviderUserRequest providerUserRequest = new ProviderUserRequest(clientRegistration,oidcUser);

        ProviderUser providerUser = createProviderUser(providerUserRequest);
        userService.register(clientRegistration.getRegistrationId(), providerUser);

        return oidcUser;
    }
}
