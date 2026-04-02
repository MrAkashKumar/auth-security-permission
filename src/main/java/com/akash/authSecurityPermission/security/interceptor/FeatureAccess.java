package com.akash.authSecurityPermission.security.interceptor;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FeatureAccess {

    String value(); //Feature-Code
}
