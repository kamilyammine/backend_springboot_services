package com.sirenanalytics.worldbank_common.repository;


import com.sirenanalytics.worldbank_common.model.entity.XSharedSecret;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommonSharedSecretRepository extends JpaRepository<XSharedSecret, Long>
{
    @Query(value = "select * from x_shared_secret order by create_datetime desc", nativeQuery = true)
    List<XSharedSecret> findAllOrderByCreateDateTimeDesc();
}
