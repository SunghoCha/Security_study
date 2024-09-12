package com.sh.oauth2.converters;

import com.sh.oauth2.model.ProviderUser;
import org.springframework.stereotype.Component;

@Component
public class DelegatingProviderUserConverter implements ProviderUserConverter<ProviderUserRequest, ProviderUser> {

    @Override
    public ProviderUser converter(ProviderUserRequest providerUserRequest) {
        return null;
    }
}
