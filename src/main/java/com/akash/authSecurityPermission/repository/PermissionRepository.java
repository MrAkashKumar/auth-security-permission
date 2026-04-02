package com.akash.authSecurityPermission.repository;

import com.akash.authSecurityPermission.entity.PermissionEntity;
import com.akash.authSecurityPermission.enums.Feature;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PermissionRepository extends JpaRepository<PermissionEntity, Long> {

    Optional<PermissionEntity> findByFeatureAndAction(Feature feature, String action);
    List<PermissionEntity> findByFeature(Feature feature);
    boolean existsByFeatureAndAction(Feature feature, String action);
}
