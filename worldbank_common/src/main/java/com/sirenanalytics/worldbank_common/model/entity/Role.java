package com.sirenanalytics.worldbank_common.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "role")
public class Role
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = null;
    private String name;
    private String description;

    @Column(name = "system_role", nullable = false)
    private Boolean systemRole;

    @Transient
    private List<Permission> permissions = new ArrayList<>();

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Boolean isSystemRole()
    {
        return systemRole;
    }

    public void setSystemRole(Boolean systemRole)
    {
        this.systemRole = systemRole;
    }

    public List<Permission> getPermissions()
    {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions)
    {
        this.permissions = permissions;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Role)) return false;
        Role role1 = (Role) o;
        return Objects.equals(name, role1.name) &&
                Objects.equals(permissions, role1.permissions);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(name, permissions);
    }
}
