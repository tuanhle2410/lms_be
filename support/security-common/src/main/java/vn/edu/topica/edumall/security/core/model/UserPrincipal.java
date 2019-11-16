package vn.edu.topica.edumall.security.core.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;

/**
 * This class extends UserDetails which use to hold user info and authority
 *
 * @author trungnt9
 */
@Getter
public class UserPrincipal implements UserDetails {

    private static final long serialVersionUID = -949955411012753606L;

    private String userId;

    private String username;

    @JsonIgnore
    private String email;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    private String avatar;

    public UserPrincipal(String userId, String avatar, String username, String email, String password,
                         Collection<? extends GrantedAuthority> authorities) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserPrincipal create(SSOUserInfo ssoUserInfo) {
        return new UserPrincipal(ssoUserInfo.getUserId(), "", "", ssoUserInfo.getEmail(), "", null);
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
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
