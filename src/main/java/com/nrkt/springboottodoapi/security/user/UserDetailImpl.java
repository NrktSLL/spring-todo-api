package com.nrkt.springboottodoapi.security.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nrkt.springboottodoapi.domain.User;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class UserDetailImpl implements UserDetails {

    @EqualsAndHashCode.Include
    private String id;
    private String username;
    @JsonIgnore
    private String password;
    private String email;
    private Boolean active;

    public final UserDetailImpl build(User user) {
        this.setId(user.getId());
        this.setEmail(user.getEmail());
        this.setUsername(user.getUsername());
        this.setPassword(user.getPassword());
        this.active = true;

        return this;
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
        return this.active;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }
}

