package com.buildbuddy.domain.consult.repository;

import com.buildbuddy.domain.consult.entity.ConsultTransaction;
import com.buildbuddy.domain.consult.entity.ConsultTransactionModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ConsultTransactionRepository extends JpaRepository<ConsultTransaction, Integer> {

    Optional<ConsultTransaction> findByTransactionIdAndUserIdAndConsultantId(Integer transactionId, Integer userId, Integer consultantId);

    @Query(nativeQuery = true, value = "select ct.transaction_id as transactionId, u.username as consultantName, ct.room_id as roomId, ct.status as status, " +
            "ct.created_time as createdTime, ct.last_update_time as lastUpdateTime " +
            "from consult_transaction ct " +
            "join user u on ct.consultant_id = u.user_id " +
            "where ct.user_id = :userId")
    Page<ConsultTransactionModel> getByCustomParam(@Param("userId") Integer userId, Pageable pageable);

}
