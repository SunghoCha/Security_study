package com.sh.security.controller;

import com.sh.security.domain.Account;
import com.sh.security.dto.request.AccountRequest;
import com.sh.security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public String signup(AccountRequest accountRequest) {
        userService.createUser(accountRequest);

        return "redirect:/";
    }
}
