package com.sh.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String dashboard() {
        return "/dashboard";
    }

    @GetMapping("/user")
    public String user() {
        return "/user";
    }

    @GetMapping("/manager")
    public String loginPage() {
        return "/manager";
    }

    @GetMapping("/admin")
    public String admin() {
        return "/admin";
    }
}
