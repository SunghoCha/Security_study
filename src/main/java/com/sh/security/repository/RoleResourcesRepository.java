package com.sh.security.repository;

import com.sh.security.domain.RoleResources;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleResourcesRepository extends JpaRepository<RoleResources, Long> {

}
