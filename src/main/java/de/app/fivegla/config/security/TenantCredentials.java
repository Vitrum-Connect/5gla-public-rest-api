package de.app.fivegla.config.security;

import de.app.fivegla.persistence.entity.Tenant;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class TenantCredentials implements UserDetails {

    private final Tenant tenant;

    public TenantCredentials(Tenant tenant) {
        this.tenant = tenant;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return tenant.getAccessToken();
    }

    @Override
    public String getUsername() {
        return tenant.getTenantId();
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
