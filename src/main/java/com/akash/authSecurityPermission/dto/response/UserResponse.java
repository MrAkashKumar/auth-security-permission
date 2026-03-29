package com.akash.authSecurityPermission.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class UserResponse {
    private UUID id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String fullName;
    private String phoneNumber;
    private String department;
    private String designation;
    private String employeeId;
    private String profilePicture;
    private LocalDate joiningDate;
    private boolean enabled;
    private Set<RoleResponse> roles;
    private LocalDateTime createdAt;
}
