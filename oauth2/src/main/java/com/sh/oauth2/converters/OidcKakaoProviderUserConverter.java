package com.sh.oauth2.converters;

import com.sh.oauth2.enums.OAuth2Config;
import com.sh.oauth2.model.ProviderUser;
import com.sh.oauth2.model.social.KakaoUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

public class OidcKakaoProviderUserConverter implements ProviderUserConverter<ProviderUserRequest, ProviderUser> {

    @Override
    public ProviderUser convert(ProviderUserRequest providerUserRequest) {

        if (!providerUserRequest.clientRegistration().getRegistrationId().equals(OAuth2Config.SocialType.KAKAO.getSocialName())) {
            return null;
        }

        if (!(providerUserRequest.oAuth2User() instanceof OidcUser)) {
            return null;
        }

        return new KakaoUser(providerUserRequest.oAuth2User(), providerUserRequest.clientRegistration());
    }


}
