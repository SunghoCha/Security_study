package com.sh.security.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoleHierarchy {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_hierarchy_id")
    private Long id;

    private String roleName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", referencedColumnName = "role_hierarchy_id", insertable = false, updatable = false)
    private RoleHierarchy parent;

    @OneToMany(mappedBy = "parent")
    private Set<RoleHierarchy> children = new HashSet<>();
}
