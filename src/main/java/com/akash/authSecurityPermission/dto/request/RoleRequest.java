package com.akash.authSecurityPermission.dto.request;

import com.akash.authSecurityPermission.enums.RoleType;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class RoleRequest {
    @NotBlank(message = "Role name is required")
    private String name;
    private RoleType roleType;
    private String description;
    private Set<UUID> permissionIds;
}
