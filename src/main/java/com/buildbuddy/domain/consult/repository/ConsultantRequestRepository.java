package com.buildbuddy.domain.consult.repository;

import com.buildbuddy.domain.consult.entity.ConsultantRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ConsultantRequestRepository extends JpaRepository<ConsultantRequest, Integer> {

    @Query(nativeQuery = true, value = "SELECT username " +
            "FROM ( " +
            "    SELECT username FROM consultant_request WHERE status = 'PENDING' or status = 'APPROVED' " +
            "    UNION " +
            "    SELECT username FROM user " +
            ") AS combined_tables " +
            "WHERE username = :username")
    Optional<String> getByUsername(@Param("username") String username);

    @Query(nativeQuery = true, value = "SELECT email " +
            "FROM ( " +
            "    SELECT email FROM consultant_request WHERE status = 'PENDING' or status = 'APPROVED' " +
            "    UNION " +
            "    SELECT email FROM user " +
            ") AS combined_tables " +
            "WHERE email = :email")
    Optional<String> getByEmail(@Param("email") String email);

    Optional<ConsultantRequest> findByUsername(String username);

    Optional<ConsultantRequest> findByEmail(String email);

    @Query(nativeQuery = true, value = "select * from consultant_request " +
            "where reviewed_by like (case when :search is null then reviewed_by else :search end) " +
            "or username like (case when :search is null then username else :search end) " +
            "or status like (case when :search is null then status else :search end) " +
            "or email like (case when :search is null then email else :search end) " +
            "or gender like (case when :search is null then gender else :search end) " +
            "or age like (case when :search is null then age else :search end)")
    Page<ConsultantRequest> getByCustomParam(@Param("search") String search, Pageable pageable);
}
