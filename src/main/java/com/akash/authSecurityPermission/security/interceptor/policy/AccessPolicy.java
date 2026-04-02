package com.akash.authSecurityPermission.security.interceptor.policy;

import org.springframework.security.core.Authentication;

public interface AccessPolicy {

    boolean canAccess(Authentication authentication, String featureCode);
}
