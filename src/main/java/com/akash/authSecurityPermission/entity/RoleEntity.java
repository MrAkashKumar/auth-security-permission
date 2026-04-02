package com.akash.authSecurityPermission.entity;

import com.akash.authSecurityPermission.enums.RoleType;
import jakarta.persistence.*;
import lombok.*;

import java.security.Permission;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleEntity extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column
    private RoleType roleType;

    @Column
    private String description;

    @Column(nullable = false)
    private boolean systemRole = false; // cannot be deleted if true

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "role_permissions",
        joinColumns = @JoinColumn(name = "role_id"),
        inverseJoinColumns = @JoinColumn(name = "permission_id"))
    @Builder.Default
    private Set<PermissionEntity> permissions = new HashSet<>();
}
