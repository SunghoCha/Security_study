package com.sh.security.security.mapper;

import com.sh.security.domain.RoleResources;
import com.sh.security.repository.RoleResourcesRepository;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class PersistentUrlRoleMapper implements UrlRoleMapper{

    private final LinkedHashMap<String, String> urlRoleMappings;

    public PersistentUrlRoleMapper(RoleResourcesRepository roleResourcesRepository) {
        this.urlRoleMappings = new LinkedHashMap<>();
        roleResourcesRepository.findAll() // N + 1 문제 발생하는데 여기선 성능최적화 안하고 시큐리티 기능 확인용...
                .forEach(src -> urlRoleMappings.put(
                        src.getResources().getResourceName(),
                        src.getRole().getRoleName()
                ));
    }

    @Override
    public Map<String, String> getUrlRoleMappings() {
        return new HashMap<>(urlRoleMappings);
    }
}
