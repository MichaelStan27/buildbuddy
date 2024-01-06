package com.buildbuddy.domain.user.repository;

import com.buildbuddy.domain.user.entity.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Propagation;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    Optional<UserEntity> findByUsername(String username);

}
