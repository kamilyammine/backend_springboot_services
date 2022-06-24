package com.sirenanalytics.worldbank_feedback.repository;

import com.sirenanalytics.worldbank_feedback.model.entity.LookupKadaa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface KadaaRepository extends JpaRepository<LookupKadaa, Long>
{
    /*----------------------------------------------------------+
    |   We only want active Kadaas that have a project on it.   |
    +----------------------------------------------------------*/
    @Query(value = "select distinct k.* from kadaa k inner join project p on k.key = p.kadaa_key where active = true order by k.name_en_us;", nativeQuery = true)
    List<LookupKadaa> findByActiveOrderByNameEnUs(boolean active);
}
