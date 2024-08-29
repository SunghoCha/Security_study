package com.sh.security.admin.service.impl;

import com.sh.security.admin.repository.RoleHierarchyRepository;
import com.sh.security.admin.service.RoleHierarchyService;
import com.sh.security.domain.RoleHierarchy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RoleHierarchyServiceImpl implements RoleHierarchyService {

    private final RoleHierarchyRepository roleHierarchyRepository;

    @Override
    public String findAllHierarchy() {
        List<RoleHierarchy> roleHierarchies = roleHierarchyRepository.findAll();
        Iterator<RoleHierarchy> iterator = roleHierarchies.iterator();
        StringBuilder hierarchyRoleBuilder = new StringBuilder();

        while (iterator.hasNext()) {
            RoleHierarchy roleHierarchy = iterator.next();
            if (roleHierarchy.getParent() != null) {
                hierarchyRoleBuilder.append(roleHierarchy.getParent().getRoleName());
                hierarchyRoleBuilder.append(" > ");
                hierarchyRoleBuilder.append(roleHierarchy.getRoleName());
                hierarchyRoleBuilder.append("\n");
            }
        }
        return hierarchyRoleBuilder.toString();
    }
}
