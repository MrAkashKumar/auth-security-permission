package com.akash.authSecurityPermission.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column
    private String phoneNumber;

    @Column
    private String department;

    @Column
    private String designation;

    @Column
    private String employeeId;

    @Column
    private String profilePicture;

    @Column
    private LocalDate dateOfBirth;

    @Column
    private LocalDate joiningDate;

    @Column
    private String address;

    @Column
    private String emergencyContact;

    @Column(nullable = false)
    @Builder.Default
    private boolean enabled = true;

    @Column(nullable = false)
    @Builder.Default
    private boolean accountNonLocked = true;

    @Column
    private int failedLoginAttempts;

    @Column
    private LocalDateTime lockoutTime;

//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "user_roles",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "role_id"))
//    @Builder.Default
      private Set<RoleEntity> roleEntities = new HashSet<>();
//
//    @ManyToOne(fetch = FetchType.LAZY)
      @JoinColumn(name = "client_id")
      private ClientEntity client;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "manager_id")
//    private User manager;
//
//    public String getFullName() {
//        return firstName + " " + lastName;
//    }

}
