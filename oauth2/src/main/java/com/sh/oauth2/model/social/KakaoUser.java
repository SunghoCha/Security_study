package com.sh.oauth2.model.social;

import com.sh.oauth2.model.OAuth2ProviderUser;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

public class KakaoUser extends OAuth2ProviderUser {

    private final Map<String, Object> profile;

    public KakaoUser(OAuth2User oAuth2User, ClientRegistration clientRegistration) {
        super(oAuth2User, clientRegistration, oAuth2User.getAttributes());
        Map<String, Object> kakaoAccount = (Map<String, Object>) getAttributes().get("kakao_account");
        profile = (Map<String, Object>) kakaoAccount.get("profile");
    }

    @Override
    public String getId() {
        return (String)getAttributes().get("id");
    }

    @Override
    public String getUsername() {
        return (String) profile.get("nickname");
    }

    @Override
    public String getPicture() {
        return (String) profile.get("profile_image_url");
    }
}
