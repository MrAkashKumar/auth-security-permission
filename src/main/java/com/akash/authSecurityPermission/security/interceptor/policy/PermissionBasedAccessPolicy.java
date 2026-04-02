package com.akash.authSecurityPermission.security.interceptor.policy;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class PermissionBasedAccessPolicy implements AccessPolicy{

    //TODO Need to add Feature service
    public PermissionBasedAccessPolicy(){

    }

    @Override
    public boolean canAccess(Authentication authentication, String featureCode) {

        if(authentication == null || !authentication.isAuthenticated()){
            return false;
        }
        boolean isSuperAdmin = authentication.getAuthorities().stream().anyMatch(grantedAuthority -> "ROLES_ADMIN".equals(grantedAuthority.getAuthority()));
        if(isSuperAdmin){
            return true;
        }

        Set<String> required = new HashSet<>(); //get required permission
        if(required.isEmpty()){
            return true;
        }

        Set<String> userAuths = new HashSet<>();
        for(GrantedAuthority grantedAuthority : authentication.getAuthorities()){
            userAuths.add(grantedAuthority.getAuthority());
        }


        return false;
    }
}
