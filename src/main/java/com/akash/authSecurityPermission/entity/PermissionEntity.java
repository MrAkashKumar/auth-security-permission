package com.akash.authSecurityPermission.entity;

import com.akash.authSecurityPermission.enums.Feature;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "permissions", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"feature", "action"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PermissionEntity extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Feature feature;

    @Column(nullable = false)
    private String action; // READ, WRITE, DELETE, APPROVE etc

    @Column
    private String description;
}
