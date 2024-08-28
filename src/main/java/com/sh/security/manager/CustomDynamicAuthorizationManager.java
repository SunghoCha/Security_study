package com.sh.security.manager;

import com.sh.security.repository.RoleResourcesRepository;
import com.sh.security.security.mapper.MapBaseUrlRoleMapper;
import com.sh.security.security.mapper.PersistentUrlRoleMapper;
import com.sh.security.security.service.DynamicAuthorizationService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authorization.AuthorityAuthorizationManager;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcherEntry;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomDynamicAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    private static final AuthorizationDecision ACCESS = new AuthorizationDecision(true);
    private final HandlerMappingIntrospector handlerMappingIntrospector;
    private final RoleResourcesRepository roleResourcesRepository;
    private List<RequestMatcherEntry<AuthorizationManager<RequestAuthorizationContext>>> mappings;

    @PostConstruct
    public void mapping() {
        DynamicAuthorizationService authorizationService = new DynamicAuthorizationService(new PersistentUrlRoleMapper(roleResourcesRepository));
        mappings = authorizationService.getUrlRoleMappings()
                .entrySet()
                .stream()
                .map(entry -> new RequestMatcherEntry<>(
                        new MvcRequestMatcher(handlerMappingIntrospector, entry.getKey()),
                        customAuthorizationManger(entry.getValue())
                ))
                .collect(Collectors.toList());
    }

    private AuthorizationManager<RequestAuthorizationContext> customAuthorizationManger(String role) {
        if (role.startsWith("ROLE")) {
            return AuthorityAuthorizationManager.hasAuthority(role);
        } else {
            return new WebExpressionAuthorizationManager(role);
        }
    }

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext context) {

        for (RequestMatcherEntry<AuthorizationManager<RequestAuthorizationContext>> mapping : mappings) {

            RequestMatcher requestMatcher = mapping.getRequestMatcher();
            RequestMatcher.MatchResult matchResult = requestMatcher.matcher(context.getRequest());
            if (matchResult.isMatch()) {
                AuthorizationManager<RequestAuthorizationContext> manager = mapping.getEntry();
                RequestAuthorizationContext requestAuthorizationContext =
                        new RequestAuthorizationContext(context.getRequest(), matchResult.getVariables());

                log.info("========== context 비교 테스트용 로그 ==========");

                log.info("mapping context : {}", context);
                log.info("mapping context : {}", context.getVariables());

                log.info("created context : {}", requestAuthorizationContext);
                log.info("created context : {}", requestAuthorizationContext.getVariables());

                // 동일하다면 굳이 새로 context 만들지 않고 인자로 전달받은 context 전달
                return manager.check(authentication, requestAuthorizationContext);
            }
        }
        return ACCESS;
    }

    @Override
    public void verify(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
        AuthorizationManager.super.verify(authentication, object);
    }
}
