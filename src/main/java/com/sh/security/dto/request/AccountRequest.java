package com.sh.security.dto.request;

import com.sh.security.domain.Account;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class AccountRequest {

    private String username;
    private String password;
    private int age;
    private List<String> roles;

    public AccountRequest(String username, String password, int age, List<String> roles) {
        this.username = username;
        this.password = password;
        this.age = age;
        this.roles = roles;
    }

    public Account toEntity(String encodedPassword) {
        return Account.builder()
                .username(username)
                .password(encodedPassword)
                .age(age)
                //.roles(roles)
                .build();
    }
}
