package com.akash.authSecurityPermission.security;

import com.akash.authSecurityPermission.entity.UserEntity;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Getter
public class UserPrincipal implements UserDetails {

    private final Long id;
    private final String username;
    private final String email;
    private final String password;
    private final boolean enabled;
    private final boolean accountNonLocked;
    private final Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(UserEntity user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.enabled = user.isEnabled();
        this.accountNonLocked = user.isAccountNonLocked();
        this.authorities = buildAuthorities(user);
    }

    private Collection<? extends GrantedAuthority> buildAuthorities(UserEntity user) {
        Set<GrantedAuthority> authorities = new HashSet<>();
        user.getRoleEntities().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
            role.getPermissions().forEach(permission ->
                authorities.add(new SimpleGrantedAuthority(
                    permission.getFeature().name() + "_" + permission.getAction()))
            );
        });
        return authorities;
    }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }

    public boolean hasPermission(String feature, String action) {
        return authorities.stream()
                .anyMatch(a -> a.getAuthority().equals(feature + "_" + action));
    }
}
