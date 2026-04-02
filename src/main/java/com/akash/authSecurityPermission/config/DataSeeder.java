package com.akash.authSecurityPermission.config;

import com.akash.authSecurityPermission.entity.PermissionEntity;
import com.akash.authSecurityPermission.entity.RoleEntity;
import com.akash.authSecurityPermission.entity.UserEntity;
import com.akash.authSecurityPermission.enums.Feature;
import com.akash.authSecurityPermission.enums.RoleType;
import com.akash.authSecurityPermission.repository.PermissionRepository;
import com.akash.authSecurityPermission.repository.RoleRepository;
import com.akash.authSecurityPermission.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author Akash Kumar
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) {
        if (permissionRepository.count() == 0) {
            seedPermissionsAndRoles();
            seedSuperAdmin();
        }
    }

    private void seedPermissionsAndRoles() {
        log.info("Seeding permissions and roles...");

        // Create all permissions
        Map<String, PermissionEntity> perms = new HashMap<>();
        for (Feature feature : Feature.values()) {
            for (String action : List.of("READ", "WRITE", "DELETE", "APPROVE")) {
                // Only create relevant action combos
                if (isValidCombination(feature, action)) {
                    String key = feature + "_" + action;
                    if (!permissionRepository.existsByFeatureAndAction(feature, action)) {
                        PermissionEntity p = permissionRepository.save(PermissionEntity.builder()
                                .feature(feature).action(action)
                                .description(feature.name() + " " + action)
                                .build());
                        perms.put(key, p);
                    } else {
                        permissionRepository.findByFeatureAndAction(feature, action)
                                .ifPresent(p -> perms.put(key, p));
                    }
                }
            }
        }

        // SUPER_ADMIN role - all permissions
        createSystemRole("SUPER_ADMIN", RoleType.SUPER_ADMIN,
            "Full system access", new HashSet<>(perms.values()));

        // ADMIN role - manage users/roles/clients but not super admin features
        createSystemRole("ADMIN", RoleType.ADMIN, "Administrative access",
            filterPerms(perms, Feature.USER_CREATE, Feature.USER_READ, Feature.USER_UPDATE,
                Feature.ROLE_MANAGE, Feature.PERMISSION_MANAGE, Feature.CLIENT_CREATE,
                Feature.CLIENT_READ, Feature.CLIENT_UPDATE, Feature.CLIENT_DELETE,
                Feature.CLAIM_READ, Feature.TIMESHEET_READ, Feature.LEAVE_READ,
                Feature.REPORTS_VIEW, Feature.DASHBOARD_VIEW));

        // MANAGER role
        createSystemRole("MANAGER", RoleType.MANAGER, "Manager access",
            filterPerms(perms, Feature.USER_READ, Feature.CLAIM_READ, Feature.CLAIM_APPROVE,
                Feature.CLAIM_REJECT, Feature.TIMESHEET_READ, Feature.TIMESHEET_APPROVE,
                Feature.LEAVE_READ, Feature.LEAVE_APPROVE, Feature.LEAVE_REJECT,
                Feature.DASHBOARD_VIEW));

        // HR role
        createSystemRole("HR", RoleType.ADMIN, "Human Resources access",
            filterPerms(perms, Feature.USER_CREATE, Feature.USER_READ, Feature.USER_UPDATE,
                Feature.CLAIM_READ, Feature.CLAIM_APPROVE, Feature.CLAIM_REJECT,
                Feature.TIMESHEET_READ, Feature.TIMESHEET_APPROVE,
                Feature.LEAVE_READ, Feature.LEAVE_APPROVE, Feature.LEAVE_REJECT,
                Feature.REPORTS_VIEW, Feature.DASHBOARD_VIEW));

        // EMPLOYEE role
        createSystemRole("EMPLOYEE", RoleType.EMPLOYEE, "Employee access",
            filterPerms(perms, Feature.CLAIM_CREATE, Feature.CLAIM_READ,
                Feature.TIMESHEET_CREATE, Feature.TIMESHEET_READ,
                Feature.LEAVE_APPLY, Feature.LEAVE_READ, Feature.DASHBOARD_VIEW));

        // CLIENT role
        createSystemRole("CLIENT", RoleType.CLIENT, "Client portal access",
            filterPerms(perms, Feature.CLIENT_READ, Feature.DASHBOARD_VIEW));

        log.info("Roles and permissions seeded successfully");
    }

    private void createSystemRole(String name, RoleType type, String desc, Set<PermissionEntity> perms) {
        if (!roleRepository.existsByName(name)) {
            roleRepository.save(RoleEntity.builder()
                    .name(name).roleType(type).description(desc)
                    .permissions(perms).systemRole(true).build());
        }
    }

    private Set<PermissionEntity> filterPerms(Map<String, PermissionEntity> perms, Feature... features) {
        Set<PermissionEntity> result = new HashSet<>();
        for (Feature feature : features) {
            for (String action : List.of("READ", "WRITE", "DELETE", "APPROVE")) {
                String key = feature + "_" + action;
                if (perms.containsKey(key)) result.add(perms.get(key));
            }
        }
        return result;
    }

    private boolean isValidCombination(Feature feature, String action) {
        return switch (action) {
            case "READ" -> true;
            case "WRITE" -> !feature.name().endsWith("_READ");
            case "DELETE" -> feature.name().contains("DELETE") || feature == Feature.USER_DELETE
                    || feature == Feature.CLIENT_DELETE;
            case "APPROVE" -> feature.name().contains("APPROVE") || feature == Feature.CLAIM_APPROVE
                    || feature == Feature.TIMESHEET_APPROVE || feature == Feature.LEAVE_APPROVE;
            default -> false;
        };
    }

    private void seedSuperAdmin() {
        if (!userRepository.existsByUsername("superadmin")) {
            RoleEntity  superAdminRole = roleRepository.findByName("SUPER_ADMIN").orElseThrow();
            UserEntity superAdmin = UserEntity.builder()
                    .username("superadmin")
                    .email("superadmin@enterprise.com")
                    .password(passwordEncoder.encode("SuperAdmin@123"))
                    .firstName("Super")
                    .lastName("Admin")
                    .employeeId("EMP00001")
                    .department("IT")
                    .designation("System Administrator")
                    .roleEntities(Set.of(superAdminRole))
                    .build();
            userRepository.save(superAdmin);
            log.info("Super admin user created: superadmin / SuperAdmin@123");
        }
    }
}
