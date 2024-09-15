package com.sh.security.security.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sh.security.dto.request.AccountRequest;
import com.sh.security.security.token.RestAuthenticationToken;
import com.sh.security.util.WebUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StringUtils;

import java.io.IOException;

public class RestAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public RestAuthenticationFilter() {
        super(new AntPathRequestMatcher("/api/login", "POST"));
        //setSecurityContextRepository(getSecurityContextRepository(http));
    }

//    private SecurityContextRepository getSecurityContextRepository(HttpSecurity http) {
//        SecurityContextRepository securityContextRepository = http.getSharedObject(SecurityContextRepository.class);
//        if (securityContextRepository == null) {
//            securityContextRepository = new DelegatingSecurityContextRepository(
//                    new RequestAttributeSecurityContextRepository(), new HttpSessionSecurityContextRepository()
//            );
//        }
//        return securityContextRepository;
//    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if (!HttpMethod.POST.name().equals(request.getMethod()) || !WebUtil.isAjax(request)) {
            throw new IllegalArgumentException("Authentication method is not supported");
        }
        AccountRequest accountRequest = objectMapper.readValue(request.getReader(), AccountRequest.class);
        if (!StringUtils.hasText(accountRequest.getUsername()) || !StringUtils.hasText(accountRequest.getPassword())) {
            throw new AuthenticationServiceException("Username or Password is not provided");
        }
        RestAuthenticationToken authenticationToken = new RestAuthenticationToken(accountRequest.getUsername(), accountRequest.getPassword());

        return getAuthenticationManager().authenticate(authenticationToken);
    }
}
