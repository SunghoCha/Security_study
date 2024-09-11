package com.sh.oauth2.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;
import java.util.Map;

@Slf4j
@Controller
public class IndexController {

    @GetMapping("/")
    public String index(Model model, Authentication authentication, @AuthenticationPrincipal OAuth2User oAuth2User){
        OAuth2AuthenticationToken authenticationToken = (OAuth2AuthenticationToken) authentication;
        if (authenticationToken != null) {
            Map<String, Object> attributes = oAuth2User.getAttributes();
            Collection<? extends GrantedAuthority> authorities = authenticationToken.getPrincipal().getAuthorities();
            for (GrantedAuthority authority : authorities) {
                log.info("authority : {}", authority);
            }
            String username;
            if (authenticationToken.getAuthorizedClientRegistrationId().equals("naver")){
                Map<String, Object> response = (Map) attributes.get("response");
                username = (String) response.get("name");
            } else { // google, keycloak...
                username = (String) attributes.get("name");
            }
            model.addAttribute("user", username);
        }
        return "index";
    }
}
