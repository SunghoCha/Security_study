package com.sh.security.controller;

import com.sh.security.dto.response.AccountResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
}
