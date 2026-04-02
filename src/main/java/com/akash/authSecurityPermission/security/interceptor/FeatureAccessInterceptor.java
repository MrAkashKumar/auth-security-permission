package com.akash.authSecurityPermission.security.interceptor;

import com.akash.authSecurityPermission.security.interceptor.policy.AccessPolicy;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class FeatureAccessInterceptor implements HandlerInterceptor {

    private final AccessPolicy accessPolicy;


    public FeatureAccessInterceptor(AccessPolicy accessPolicy) {
        this.accessPolicy = accessPolicy;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(!(handler instanceof HandlerMethod handlerMethod)) return true;

        FeatureAccess featureAccess = handlerMethod.getMethodAnnotation(FeatureAccess.class);
        if (featureAccess == null){
            /* try at class level */
            featureAccess = handlerMethod.getBeanType().getAnnotation(FeatureAccess.class);
        }
        if(featureAccess==null){
            /* no feature constraint */
            return true;
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean granted = accessPolicy.canAccess(authentication, featureAccess.value());
        if(!granted){
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden: missing privileges for feature "+featureAccess.value());
            return false;
        }

        return true;
    }
}
