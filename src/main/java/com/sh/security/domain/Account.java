package com.sh.security.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Controller;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_Id")
    private Long id;
    private String username;
    private String password;
    private int age;
    private String roles;

    @Builder
    public Account(String username, String password, int age, String roles) {
        this.username = username;
        this.password = password;
        this.age = age;
        this.roles = roles;
    }
}
