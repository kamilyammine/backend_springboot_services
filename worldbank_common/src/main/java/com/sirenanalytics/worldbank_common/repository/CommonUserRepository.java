package com.sirenanalytics.worldbank_common.repository;

import com.sirenanalytics.worldbank_common.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface CommonUserRepository extends JpaRepository<User, Long>
{
    String usersWithRoleQuery = "select * from moj_user u inner join user_role ur on u.id = ur.user_id " +
            "inner join role r on ur.role_id = r.id where lower(r.name) = lower(:roleName)";

    Optional<User> findByUsername(String username);

    @Query(value = "select exists(select 1 from moj_user where username = :username)", nativeQuery = true)
    Boolean doesUsernameExist(@Param("username") String username);

    @Query(value = usersWithRoleQuery, nativeQuery = true)
    Page<User> getUsersWithRole(Pageable pageable, @Param("roleName") String roleName);

    @Modifying
    @Query(value = "delete from user_role where user_id = :userId", nativeQuery = true)
    void deleteUserRolesByUserId(@Param("userId") Long userId);

    @Modifying
    @Query(value = "insert into user_role values(:userId, :roleId, :createUserId, :createTimestamp)", nativeQuery = true)
    void insertUserRoleRecord(@Param("userId") Long userId, @Param("roleId") Long roleId,
                              @Param("createUserId") Long createUserId, @Param("createTimestamp") LocalDateTime createTimestamp);
}
