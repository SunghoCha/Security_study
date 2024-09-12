package com.sh.oauth2.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CustomAuthorityMapper implements GrantedAuthoritiesMapper {

    private String prefix = "ROLE_";

    @Override
    public Collection<? extends GrantedAuthority> mapAuthorities(Collection<? extends GrantedAuthority> authorities) {

        Set<GrantedAuthority> mapped = new HashSet<>(authorities.size());
        for (GrantedAuthority authority : authorities) {
            mapped.add(mapAuthority(authority.getAuthority()));
        }

        return mapped;
    }

    private GrantedAuthority mapAuthority(String name) {

        if (name.lastIndexOf(".") > 0) {
            int index = name.lastIndexOf(".");
            name = "SCOPE_" + name.substring(index + 1);
        } else if (!name.startsWith(prefix) && !name.startsWith("SCOPE")) {
            name = prefix + name;
        }
        return new SimpleGrantedAuthority(name);
    }
}
