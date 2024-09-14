package com.sh.oauth2.certification;

import com.sh.oauth2.model.ProviderUser;
import com.sh.oauth2.model.users.User;
import com.sh.oauth2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SelfCertification {

    private final UserRepository userRepository;

    public void checkCertification(ProviderUser providerUser) {
        User user = userRepository.findByUsername(providerUser.getId()).orElseThrow(IllegalArgumentException::new);
        boolean bool = providerUser.getProvider().equals("none") || providerUser.getProvider().equals("naver");
        providerUser.isCertificated(bool);

    }

    public void certificate(ProviderUser providerUser) {

    }
}
