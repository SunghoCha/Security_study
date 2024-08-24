package com.sh.security.service;

import com.sh.security.domain.Account;
import com.sh.security.dto.request.AccountRequest;
import com.sh.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public void createUser(AccountRequest accountRequest) {
        String encodedPassword = passwordEncoder.encode(accountRequest.getPassword());
        Account account = accountRequest.toEntity(encodedPassword);
        userRepository.save(account);
    }
}
