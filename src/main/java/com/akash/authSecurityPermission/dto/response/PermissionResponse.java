package com.akash.authSecurityPermission.dto.response;

import com.akash.authSecurityPermission.enums.Feature;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PermissionResponse {
    private Long id;
    private Feature feature;
    private String action;
    private String description;
}
