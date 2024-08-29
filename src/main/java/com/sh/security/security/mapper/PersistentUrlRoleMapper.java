package com.sh.security.security.mapper;

import com.sh.security.domain.RoleResources;
import com.sh.security.repository.RoleResourcesRepository;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class PersistentUrlRoleMapper implements UrlRoleMapper{

    private final RoleResourcesRepository roleResourcesRepository;

    @Override
    public Map<String, String> getUrlRoleMappings() {
        return roleResourcesRepository.findAll().stream()
                .collect(Collectors.toMap(
                        roleResources -> roleResources.getResources().getResourceName(),
                        roleResources -> roleResources.getRole().getRoleName()
                ));
    }
}
