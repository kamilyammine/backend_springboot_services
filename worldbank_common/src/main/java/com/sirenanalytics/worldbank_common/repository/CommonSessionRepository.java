package com.sirenanalytics.worldbank_common.repository;

import com.sirenanalytics.worldbank_common.model.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CommonSessionRepository extends JpaRepository<Session, Long>
{
    Optional<Session> findByAccessToken(String accessToken);

    Optional<Session> findByRefreshToken(String refreshToken);

    @Modifying
    void deleteByAccessToken(@Param("accessToken") String accessToken);

    @Modifying
    void deleteByRefreshToken(@Param("refreshToken") String refreshToken);

    @Modifying
    @Query(value = "delete from session where username = :username", nativeQuery = true)
    void deleteExistingSessionsForUser(@Param("username") String username);

    /*------------------------------------------------------+
    |   Delete all sessions created more than 2 hours ago   |
    +------------------------------------------------------*/
    @Modifying
    @Query(value = "delete from session where create_datetime > now() - interval '2 hours'", nativeQuery = true)
    void deleteStaleSessions();
}
