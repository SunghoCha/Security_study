package com.sh.security.admin.service;

import com.sh.security.domain.Resources;

import java.util.List;

public interface ResourcesService {

    Resources getResource(Long id);

    List<Resources> getResources();

    void createResources(Resources resources);

    void deleteResources(Long id);
}
