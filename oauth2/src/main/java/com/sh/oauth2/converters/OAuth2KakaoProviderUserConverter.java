package com.sh.oauth2.converters;

import com.sh.oauth2.enums.OAuth2Config;
import com.sh.oauth2.model.ProviderUser;
import com.sh.oauth2.model.social.GoogleUser;
import com.sh.oauth2.model.social.KakaoUser;

public class OAuth2KakaoProviderUserConverter implements ProviderUserConverter<ProviderUserRequest, ProviderUser> {

    @Override
    public ProviderUser convert(ProviderUserRequest providerUserRequest) {

        if (!providerUserRequest.clientRegistration().getRegistrationId().equals(OAuth2Config.SocialType.KAKAO.getSocialName())) {
            return null;
        }

        return new KakaoUser(providerUserRequest.oAuth2User(), providerUserRequest.clientRegistration());
    }


}
