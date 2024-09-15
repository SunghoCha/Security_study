package com.sh.oauth2.converters;

import com.sh.oauth2.enums.OAuth2Config;
import com.sh.oauth2.model.ProviderUser;
import com.sh.oauth2.model.social.NaverUser;

public class OAuth2NaverProviderUserConverter implements ProviderUserConverter<ProviderUserRequest, ProviderUser> {

    @Override
    public ProviderUser convert(ProviderUserRequest providerUserRequest) {

        if (OAuth2Config.SocialType.NAVER.getSocialName().equals(providerUserRequest.clientRegistration().getRegistrationId())) {
            return null;
        }

        return new NaverUser(providerUserRequest.oAuth2User(), providerUserRequest.clientRegistration());
    }
}
