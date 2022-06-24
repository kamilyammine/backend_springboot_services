package com.sirenanalytics.worldbank_common.repository;

import com.sirenanalytics.worldbank_common.model.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommonPermissionRepository extends JpaRepository<Permission, Long>
{
    @Query(value = "select p.* from permission p inner join role_permission rp on p.id = " +
            "rp.permission_id where rp.role_id = :roleId", nativeQuery = true)
    List<Permission> findByRoleId(@Param("roleId") Long roleId);
}
