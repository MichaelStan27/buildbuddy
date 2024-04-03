package com.buildbuddy.domain.consult.repository;

import com.buildbuddy.domain.consult.entity.ConsultantRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ConsultantRequestRepository extends JpaRepository<ConsultantRequest, Integer> {

    @Query(nativeQuery = true, value = "SELECT username " +
            "FROM ( " +
            "    SELECT username FROM consultant_request " +
            "    UNION " +
            "    SELECT username FROM user " +
            ") AS combined_tables " +
            "WHERE username = :username")
    Optional<String> findByUsername(@Param("username") String username);

    @Query(nativeQuery = true, value = "SELECT email " +
            "FROM ( " +
            "    SELECT email FROM consultant_request " +
            "    UNION " +
            "    SELECT email FROM user " +
            ") AS combined_tables " +
            "WHERE email = :email")
    Optional<String> findByEmail(@Param("email") String email);
}
