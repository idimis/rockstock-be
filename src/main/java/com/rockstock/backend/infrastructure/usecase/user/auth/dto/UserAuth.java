package com.rockstock.backend.infrastructure.usecase.user.auth.dto;

import com.rockstock.backend.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAuth implements UserDetails {
    private User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {

        return true;
    }

    @Override
    public boolean isAccountNonLocked() {

        return !this.user.getIsLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {

        return true;
    }

    @Override
    public boolean isEnabled() {

        return this.user.getIsEnabled();
    }

    /**
     * Menambahkan metode untuk memeriksa apakah user telah terverifikasi.
     *
     * @return true jika user terverifikasi, false jika tidak.
     */
    public boolean isVerified() {
        return this.user.getIsVerified();
    }

    /**
     * Mendapatkan ID user.
     *
     * @return ID user.
     */
    public Long getUserId() {
        return this.user.getId();
    }
}
