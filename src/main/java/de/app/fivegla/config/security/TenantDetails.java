package de.app.fivegla.config.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * Represents a tenant in the system.
 */
@Getter
@Setter
public class TenantDetails implements UserDetails {

    /**
     * The UUID of the tenant.
     */
    private String tenantId;

    /**
     * The access token of the tenant.
     */
    private String accessToken;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return accessToken;
    }

    @Override
    public String getUsername() {
        return getTenantId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
