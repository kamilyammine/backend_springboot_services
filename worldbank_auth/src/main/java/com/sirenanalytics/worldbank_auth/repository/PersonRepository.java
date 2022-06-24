package com.sirenanalytics.worldbank_auth.repository;


import com.sirenanalytics.worldbank_auth.model.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long>
{
    Optional<Person> findByEmailAddress(String email);

    @Query(value = "select exists(select 1 from person where email_address = :email)", nativeQuery = true)
    Boolean doesEmailExist(@Param("email") String username);
}
