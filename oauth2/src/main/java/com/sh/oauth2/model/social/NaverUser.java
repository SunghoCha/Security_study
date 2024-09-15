package com.sh.oauth2.model.social;

import com.sh.oauth2.model.OAuth2ProviderUser;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

public class NaverUser extends OAuth2ProviderUser {

    private final Map<String, Object> response;

    public NaverUser(OAuth2User oAuth2User, ClientRegistration clientRegistration) {
        super(oAuth2User, clientRegistration, oAuth2User.getAttributes());
        response = (Map<String, Object>) getAttributes().get("response");
    }

    @Override
    public String getId() {
        return (String) getAttributes().get("id");
    }

    @Override
    public String getUsername() {
        return (String) response.get("email");
    }

    @Override
    public String getPicture() {
        return (String) response.get("profile_image");
    }
}
