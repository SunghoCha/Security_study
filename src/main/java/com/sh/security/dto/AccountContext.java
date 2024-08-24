package com.sh.security.dto;

import com.sh.security.dto.request.AccountRequest;
import com.sh.security.dto.response.AccountResponse;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class AccountContext implements UserDetails {

    private final AccountResponse accountResponse;
    private final List<GrantedAuthority> authorities;

    @Builder
    public AccountContext(AccountResponse accountResponse, List<GrantedAuthority> authorities) {
        this.accountResponse = accountResponse;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return accountResponse.getPassword();
    }

    @Override
    public String getUsername() {
        return accountResponse.getUsername();
    }
}
