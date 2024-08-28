package com.sh.security.dto.response;

import com.sh.security.domain.AccountRole;
import com.sh.security.domain.Role;
import lombok.Builder;

public class RoleResponse {

    private String roleName;
    private String roleDesc;

    @Builder
    public RoleResponse(String roleName, String roleDesc) {
        this.roleName = roleName;
        this.roleDesc = roleDesc;
    }

    public static RoleResponse of(AccountRole accountRole) {
        return RoleResponse.builder()
                .roleName(accountRole.getRole().getRoleName())
                .roleDesc(accountRole.getRole().getRoleDesc())
                .build();
    }
}
