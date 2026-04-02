package com.akash.authSecurityPermission.dto.response;

import com.akash.authSecurityPermission.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

/**
 * @author Akash Kumar
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleResponse {
    private UUID id;
    private String name;
    private RoleType roleType;
    private String description;
    private boolean systemRole;
    private Set<PermissionResponse> permissions;
}
