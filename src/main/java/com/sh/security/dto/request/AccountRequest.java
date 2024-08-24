package com.sh.security.dto.request;

import com.sh.security.domain.Account;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class AccountRequest {

    private String username;
    private String password;
    private int age;
    private String roles;

    @Builder
    public AccountRequest( String username, String password, int age, String roles) {
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
                .roles(roles)
                .build();
    }
}
