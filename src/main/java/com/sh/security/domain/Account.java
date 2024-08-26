package com.sh.security.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.LinkedHashSet;
import java.util.Set;

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

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    Set<Role> roles = new LinkedHashSet<>();

    @Builder
    public Account(String username, String password, int age, Set<Role> roles) {
        this.username = username;
        this.password = password;
        this.age = age;
        this.roles = roles;
    }
}
