package com.sh.security.dto.response;

import com.sh.security.domain.Account;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class AccountResponse {

    private Long id;
    private String username;
    private String password;
    private int age;
    private String roles;

    @Builder
    public AccountResponse(Long id, String username, String password, int age, String roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.age = age;
        this.roles = roles;
    }

    public static AccountResponse of(Account account) {
        return AccountResponse.builder()
                .id(account.getId())
                .username(account.getUsername())
                .password(account.getPassword())
                .age(account.getAge())
                .roles("ADMIN") // 임시
                .build();
    }
}
