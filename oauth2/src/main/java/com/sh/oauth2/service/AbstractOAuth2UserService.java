package com.sh.oauth2.service;

import com.sh.oauth2.model.GoogleUser;
import com.sh.oauth2.model.KeycloakUser;
import com.sh.oauth2.model.NaverUser;
import com.sh.oauth2.model.ProviderUser;
import lombok.Getter;
import org.springframework.security.core.parameters.P;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Getter
public abstract class AbstractOAuth2UserService {

    protected ProviderUser createProviderUser(OAuth2User oAuth2User,ClientRegistration clientRegistration) {
        String registrationId = clientRegistration.getRegistrationId();
        if (registrationId.equals("keycloak")) {
            return new KeycloakUser(oAuth2User, clientRegistration);

        } else if (registrationId.equals("google")) {
            return new GoogleUser(oAuth2User, clientRegistration);

        } else if (registrationId.equals("naver")) {
            return new NaverUser(oAuth2User, clientRegistration);

        } else {
            throw new IllegalArgumentException("유효하지 않은 registrationId: " + registrationId);
        }
    }
}
