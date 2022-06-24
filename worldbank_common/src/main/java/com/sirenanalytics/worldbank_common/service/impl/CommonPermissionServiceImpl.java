package com.sirenanalytics.worldbank_common.service.impl;


import com.sirenanalytics.worldbank_common.model.entity.Permission;
import com.sirenanalytics.worldbank_common.repository.CommonPermissionRepository;
import com.sirenanalytics.worldbank_common.service.CommonPermissionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

@Service
public class CommonPermissionServiceImpl implements CommonPermissionService
{
    @Resource
    CommonPermissionRepository commonPermissionRepository;

    @Override
    public Page<Permission> find(Pageable pageable)
    {
        return commonPermissionRepository.findAll(pageable);
    }

    @Override
    public Optional<Permission> findById(Long id)
    {
        return commonPermissionRepository.findById(id);
    }
}
