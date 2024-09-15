package com.sh.oauth2.converters;

import com.sh.oauth2.enums.OAuth2Config;
import com.sh.oauth2.model.ProviderUser;
import com.sh.oauth2.model.social.GoogleUser;

import java.util.List;

public class OAuth2GoogleProviderUserConverter implements ProviderUserConverter<ProviderUserRequest, ProviderUser> {

    @Override
    public ProviderUser convert(ProviderUserRequest providerUserRequest) {
        if (!OAuth2Config.SocialType.GOOGLE.getSocialName().equals(providerUserRequest.clientRegistration().getRegistrationId())) {
            return null;
        }
        return new GoogleUser(providerUserRequest.oAuth2User(), providerUserRequest.clientRegistration());
    }
}
