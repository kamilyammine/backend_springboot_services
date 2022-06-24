package com.sirenanalytics.worldbank_common.service;

import com.sirenanalytics.worldbank_common.model.entity.Permission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CommonPermissionService
{
    Page<Permission> find(Pageable pageable);

    Optional<Permission> findById(Long id);
}
