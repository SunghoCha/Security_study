package com.sh.security.security.service;

import com.sh.security.domain.Account;
import com.sh.security.dto.AccountContext;
import com.sh.security.dto.request.AccountRequest;
import com.sh.security.dto.response.AccountResponse;
import com.sh.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service(value = "userDetailsService")
@RequiredArgsConstructor
public class FormUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Account account = userRepository.findByUsername(username);
        if (account == null) {
            throw new UsernameNotFoundException("No user found with username" + username);
        }
        AccountResponse response = AccountResponse.of(account);
        List<GrantedAuthority> authorities = account.getAccountRoles().stream()
                .map(accountRole -> new SimpleGrantedAuthority(accountRole.getRole().getRoleName()))
                .collect(Collectors.toList());

        return AccountContext.builder()
                .accountResponse(response)
                .authorities(authorities)
                .build();
    }
}
