package com.rockstock.backend.infrastructure.system.security;

import com.rockstock.backend.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@AllArgsConstructor
public class UserAuth implements UserDetails {
    private final User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
    }

    @Override
    public String getPassword() {
        // Jika user login via Google OAuth, password bisa null, jadi kita kembalikan null
        return user.getPassword();  // null jika user login via OAuth
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !user.getIsLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.getIsEnabled();
    }

    public boolean isVerified() {
        return user.getIsVerified();
    }

    public Long getUserId() {
        return user.getId();
    }

    // Menambahkan logika untuk menentukan apakah user baru berdasarkan apakah password null
    public boolean isOAuthUser() {
        return user.getPassword() == null;  // Menandakan bahwa user login via OAuth
    }
}
