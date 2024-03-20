package com.buildbuddy.domain.user.repository;

import com.buildbuddy.domain.user.entity.UserEntity;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    @Query(nativeQuery = true, value = "SELECT * FROM user where user_id = :userId " +
            "AND user_id not in ( " +
            "select DISTINCT (user_id) from consultant_detail " +
            ")")
    Optional<UserEntity> findUserById(@Param("userId") Integer userId);

    @Query(nativeQuery = true, value = "SELECT * FROM user where user_id = :consultantId " +
            "AND user_id in ( " +
                "select DISTINCT (user_id) from consultant_detail " +
            ")")
    Optional<UserEntity> findByConsultantId(@Param("consultantId") Integer consultantId);

    Optional<UserEntity> findByUsername(String username);

    // Only for audit purpose, dont user for other purpose
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    @Query(nativeQuery = true, value = "select * from user where username = :username")
    Optional<UserEntity> auditLoadByUsername(@Param("username") String username);

}
