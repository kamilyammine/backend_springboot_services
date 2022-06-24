package com.sirenanalytics.worldbank_common.repository;

import com.sirenanalytics.worldbank_common.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CommonRoleRepository extends JpaRepository<Role, Long>
{
    @Query(value = "select r.* from role r inner join user_role ur on r.id = ur.role_id where ur.user_id = :userId", nativeQuery = true)
    List<Role> findByUserId(@Param("userId") Long userId);

    @Modifying
    @Query(value = "delete from role_permission where role_id = :roleId", nativeQuery = true)
    void deleteRolePermissionsByRoleId(@Param("roleId") Long roleId);

    @Modifying
    @Query(value = "insert into role_permission values(:roleId, :permissionId, :createUserId, :createTimestamp)", nativeQuery = true)
    void insertRolePermissionRecord(@Param("roleId") Long roleId, @Param("permissionId") Long permissionId,
                                    @Param("createUserId") Long createUserId, @Param("createTimestamp") LocalDateTime createTimestamp);
}
