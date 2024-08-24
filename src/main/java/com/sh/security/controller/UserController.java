package com.sh.security.controller;

import com.sh.security.domain.Account;
import com.sh.security.dto.request.AccountRequest;
import com.sh.security.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public String signup(AccountRequest accountRequest) {
        log.info("Received signup request with username: {}", accountRequest.getUsername());

        userService.createUser(accountRequest);

        log.info("User {} has been created successfully, redirecting to home.", accountRequest.getUsername());
        return "redirect:/";
    }
}
