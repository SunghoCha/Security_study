package com.sh.oauth2.enums;

import lombok.Getter;

public class OAuth2Config {

    @Getter
    public enum SocialType {
        GOOGLE("google"),
        NAVER("naver"),
        KAKAO("kakao");

        private final String socialName;

        SocialType(String socialName) {
            this.socialName = socialName;
        }
    }
}
