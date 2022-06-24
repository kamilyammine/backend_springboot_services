package com.sirenanalytics.worldbank_common.repository;

import com.sirenanalytics.worldbank_common.model.entity.LogResetPassword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommonLogResetPasswordRepository extends JpaRepository<LogResetPassword, Long>
{
    Optional<LogResetPassword> findByKey(String key);
}
