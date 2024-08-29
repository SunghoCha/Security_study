package com.sh.security.admin.service.impl;

import com.sh.security.admin.repository.ResourcesRepository;
import com.sh.security.admin.service.ResourcesService;
import com.sh.security.domain.Resources;
import com.sh.security.security.manager.CustomDynamicAuthorizationManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ResourcesServiceImpl implements ResourcesService {

    private final ResourcesRepository resourcesRepository;
    private final CustomDynamicAuthorizationManager authorizationManager;

    @Override
    public Resources getResource(Long id) {
        return resourcesRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public List<Resources> getResources() {
        return resourcesRepository.findAll(Sort.by(Sort.Order.asc("orderNum")));
    }

    @Override
    public void createResources(Resources resources) {
        resourcesRepository.save(resources);
        authorizationManager.reload();
    }

    @Override
    public void deleteResources(Long id) {
        resourcesRepository.deleteById(id);
        authorizationManager.reload();
    }
}
