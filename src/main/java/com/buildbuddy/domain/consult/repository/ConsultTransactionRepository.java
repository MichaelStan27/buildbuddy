package com.buildbuddy.domain.consult.repository;

import com.buildbuddy.domain.consult.entity.ConsultTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConsultTransactionRepository extends JpaRepository<ConsultTransaction, Integer> {

    Optional<ConsultTransaction> findByTransactionIdAndUserIdAndConsultantId(Integer transactionId, Integer userId, Integer consultantId);

}
