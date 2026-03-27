package com.akash.authSecurityPermission.dto.request;

import com.akash.authSecurityPermission.enums.Feature;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PermissionRequest {
    @NotNull(message = "Feature is required")
    private Feature feature;

    @NotBlank(message = "Action is required")
    private String action;

    private String description;
}
