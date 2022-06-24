package com.sirenanalytics.worldbank_common.repository;

import com.sirenanalytics.worldbank_common.model.entity.XAccessKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommonAccessKeyRepository extends JpaRepository<XAccessKey, Long>
{
    @Query(value = "select * from x_access_key order by create_datetime desc", nativeQuery = true)
    List<XAccessKey> findAllOrderByCreateDateTimeDesc();
}
