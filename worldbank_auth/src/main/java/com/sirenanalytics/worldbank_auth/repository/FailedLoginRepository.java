package com.sirenanalytics.worldbank_auth.repository;

import com.sirenanalytics.worldbank_auth.model.entity.FailedLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FailedLoginRepository extends JpaRepository<FailedLogin, Long>
{
    Optional<FailedLogin> findByUserId(Long userId);

    @Modifying
    @Query(value = "delete from failed_login where user_id = :userId", nativeQuery = true)
    void deleteExistingByUserId(@Param("userId") Long userId);
}
