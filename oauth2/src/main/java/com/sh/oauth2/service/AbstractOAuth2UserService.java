package com.sh.oauth2.service;

import com.sh.oauth2.converters.ProviderUserRequest;
import com.sh.oauth2.model.social.GoogleUser;
import com.sh.oauth2.model.social.KeycloakUser;
import com.sh.oauth2.model.social.NaverUser;
import com.sh.oauth2.model.ProviderUser;
import lombok.Getter;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Getter
public abstract class AbstractOAuth2UserService {

    protected ProviderUser createProviderUser(ProviderUserRequest providerUserRequest) {

}
