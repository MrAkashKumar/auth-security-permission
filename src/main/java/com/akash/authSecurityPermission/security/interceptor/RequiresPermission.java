package com.akash.authSecurityPermission.security.interceptor;

import com.akash.authSecurityPermission.enums.Feature;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequiresPermission {
    Feature feature();
    String action();
}
