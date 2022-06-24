package com.sirenanalytics.worldbank_common.service.impl;


import com.sirenanalytics.worldbank_common.model.entity.Role;
import com.sirenanalytics.worldbank_common.repository.CommonRoleRepository;
import com.sirenanalytics.worldbank_common.service.CommonRoleService;
import com.sirenanalytics.worldbank_common.util.ContextUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommonRoleServiceImpl implements CommonRoleService
{
    @Resource
    CommonRoleRepository commonRoleRepository;

    @Override
    public Page<Role> find(Pageable pageable)
    {
        return commonRoleRepository.findAll(pageable);
    }

    @Override
    public Optional<Role> findById(Long id)
    {
        return commonRoleRepository.findById(id);
    }

    @Override
    public List<Role> findByUserId(Long userId)
    {
        return commonRoleRepository.findByUserId(userId);
    }

    @Override
    public Role save(Role role)
    {
        if (role.isSystemRole() == null)
            role.setSystemRole(Boolean.FALSE);

        return commonRoleRepository.save(role);
    }

    @Override
    @Transactional
    public void setRolePermissions(Long roleId, List<Long> permissionIdList)
    {
        Long modifyingUserId = ContextUtil.getCurrentUserId();
        LocalDateTime now = LocalDateTime.now();

        commonRoleRepository.deleteRolePermissionsByRoleId(roleId);
        for (Long permissionId : permissionIdList)
            commonRoleRepository.insertRolePermissionRecord(roleId, permissionId, modifyingUserId, now);
    }

    @Override
    public void deleteById(Long id)
    {
        commonRoleRepository.deleteById(id);
    }
}
