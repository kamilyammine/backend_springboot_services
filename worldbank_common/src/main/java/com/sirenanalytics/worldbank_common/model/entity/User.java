package com.sirenanalytics.worldbank_common.model.entity;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@DynamicUpdate
@Table(name = "cf_user")
public class User implements UserDetails
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = null;

    @Column(name = "username", length = 200)
    private String username;

    private String password;

    @Column(name = "change_pw_required")
    private Boolean changePasswordRequired;

    private Boolean enabled;

    @Transient
    private List<Role> roles = new ArrayList<>();

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    @Override
    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public Boolean getChangePasswordRequired()
    {
        return changePasswordRequired;
    }

    public void setChangePasswordRequired(Boolean changePasswordRequired)
    {
        this.changePasswordRequired = changePasswordRequired;
    }

    public Boolean getEnabled()
    {
        return enabled;
    }

    public List<Role> getRoles()
    {
        return roles;
    }

    public void setRoles(List<Role> roles)
    {
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        Set<GrantedAuthority> gaSet = new HashSet<>();
        roles.stream().map(Role::getPermissions).forEach(gaSet::addAll);

        return gaSet;
    }

    public boolean hasPermission(@NonNull String permissionName)
    {
        return roles.stream().flatMap(role -> role.getPermissions().stream())
                .anyMatch(permission -> permissionName.equalsIgnoreCase(permission.getName()));
    }

    @Override
    public boolean isEnabled()
    {
        return enabled;
    }

    public void setEnabled(Boolean enabled)
    {
        this.enabled = enabled;
    }

    @Override
    public boolean isAccountNonExpired()
    {
        return !enabled;
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return !enabled;
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return !enabled;
    }
}
