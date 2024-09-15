package com.sh.oauth2.service;

import com.sh.oauth2.converters.ProviderUserConverter;
import com.sh.oauth2.converters.ProviderUserRequest;
import com.sh.oauth2.model.ProviderUser;
import com.sh.oauth2.model.users.User;
import com.sh.oauth2.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomUserDetailsService extends AbstractOAuth2UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(ProviderUserConverter<ProviderUserRequest,
                                    ProviderUser> providerUserConverter, UserRepository userRepository) {

        super(providerUserConverter);
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("No user found with username" + username);
        }
        ProviderUserRequest providerUserRequest = new ProviderUserRequest(userOptional.get());
        ProviderUser providerUser = createProviderUser(providerUserRequest);

        return new PrincipalUser(providerUser);
    }
}
