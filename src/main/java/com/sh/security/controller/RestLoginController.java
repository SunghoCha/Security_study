package com.sh.security.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestLoginController {

    @PostMapping("/api/login")
    public String restLogin() {
        return "restLogin";
    }
}
