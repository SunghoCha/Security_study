package com.sh.security.controller;

import com.sh.security.dto.response.AccountResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RestApiController {

    @GetMapping("/user")
    public AccountResponse restUser(@AuthenticationPrincipal AccountResponse accountResponse) {
        return accountResponse;
    }

    @GetMapping("/manager")
    public AccountResponse restManager(@AuthenticationPrincipal AccountResponse accountResponse) {
        return accountResponse;
    }

    @GetMapping("/admin")
    public AccountResponse restAdmin(@AuthenticationPrincipal AccountResponse accountResponse) {
        return accountResponse;
    }

    @GetMapping("/logout") // POST로 구현할 것
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContextHolderStrategy().getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "logout";
    }
}
