package com.sh.oauth2.service;

import com.sh.oauth2.model.ProviderUser;
import com.sh.oauth2.model.users.User;
import com.sh.oauth2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void register(String registrationId, ProviderUser providerUser) {
        Optional<User> userOptional = userRepository.findByUsername(providerUser.getUsername());
        if (userOptional.isEmpty()) {
            User user = User.builder()
                    .registrationId(registrationId)
                    .socialId(providerUser.getId())
                    .username(providerUser.getUsername())
                    .provider(providerUser.getProvider())
                    .email(providerUser.getEmail())
                    .authorities(providerUser.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                    .build();
            userRepository.save(user);
        } else {
            System.out.println("기존에 존재하는 회원일 경우의 처리 미정.. 정보 체크 후 업데이트? 통합 정책 등..");
        }
    }
}
