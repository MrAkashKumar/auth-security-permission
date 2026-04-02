package com.akash.authSecurityPermission.security.interceptor;

import com.akash.authSecurityPermission.exception.AccessDeniedException;
import com.akash.authSecurityPermission.security.UserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author Akash Kumar
 */
@Slf4j
@Aspect
@Component
public class PermissionInterceptor {

    @Around("@annotation(requiresPermission) || @within(requiresPermission)")
    public Object checkPermission(ProceedingJoinPoint joinPoint, RequiresPermission requiresPermission) throws Throwable {
        // If annotation is on class, check method-level annotation first
        if (requiresPermission == null) {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            requiresPermission = method.getAnnotation(RequiresPermission.class);
        }

        if (requiresPermission == null) {
            return joinPoint.proceed();
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDeniedException("Authentication required");
        }

        if (authentication.getPrincipal() instanceof UserPrincipal principal) {
            String feature = requiresPermission.feature().name();
            String action = requiresPermission.action();

            if (!principal.hasPermission(feature, action)) {
                log.warn("Access denied for user '{}' to feature '{}' action '{}'",
                        principal.getUsername(), feature, action);
                throw new AccessDeniedException(
                    String.format("Access denied: insufficient permissions for %s_%s", feature, action));
            }
        }

        return joinPoint.proceed();
    }
}
