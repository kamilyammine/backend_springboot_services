package com.sirenanalytics.worldbank_common.service;


import com.sirenanalytics.worldbank_common.model.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CommonRoleService
{
    Page<Role> find(Pageable pageable);

    Optional<Role> findById(Long id);

    List<Role> findByUserId(Long userId);

    Role save(Role role);

    void setRolePermissions(Long roleIdId, List<Long> permissionIdList);

    void deleteById(Long id);
}
