package com.sh.oauth2.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.List;
import java.util.Map;

public abstract class OAuth2ProviderUser implements ProviderUser {

    private final OAuth2User oAuth2User;
    private final ClientRegistration clientRegistration;
    private final Map<String, Object> attributes;

    public OAuth2ProviderUser(OAuth2User oAuth2User, ClientRegistration clientRegistration) {
        this.oAuth2User = oAuth2User;
        this.clientRegistration = clientRegistration;
        this.attributes = oAuth2User.getAttributes();
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getEmail() {
        return "";
    }

    @Override
    public String getProvider() {
        return "";
    }

    @Override
    public List<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return Map.of();
    }
}
