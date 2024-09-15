package com.sh.oauth2.service;

import com.sh.oauth2.converters.ProviderUserConverter;
import com.sh.oauth2.converters.ProviderUserRequest;
import com.sh.oauth2.model.ProviderUser;
import com.sh.oauth2.model.social.GoogleUser;
import com.sh.oauth2.model.social.KeycloakUser;
import com.sh.oauth2.model.social.NaverUser;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public abstract class AbstractOAuth2UserService {

    private final ProviderUserConverter<ProviderUserRequest, ProviderUser> providerUserConverter;

    public AbstractOAuth2UserService(ProviderUserConverter<ProviderUserRequest, ProviderUser> providerUserConverter) {
        this.providerUserConverter = providerUserConverter;
    }

    protected ProviderUser createProviderUser(ProviderUserRequest providerUserRequest) {
        return providerUserConverter.convert(providerUserRequest);
    }
}
