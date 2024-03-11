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

    @Query(nativeQuery = true, value = "select ct.transaction_id as transactionId, u2.user_id as userId, u2.username as username, u.user_id as consultantId, u.username as consultantName, " +
            "ct.room_id as roomId, ct.status as status, " +
            "ct.created_time as createdTime, ct.last_update_time as lastUpdateTime " +
            "from consult_transaction ct " +
            "join user u on ct.consultant_id = u.user_id " +
            "join user u2 on ct.user_id = u2.user_id " +
            "where ct.user_id = (case when :userId is null then ct.user_id else :userId end) " +
            "and ct.consultant_id = (case when :consultantId is null then ct.consultant_id else :consultantId end)")
    Page<ConsultTransactionModel> getByCustomParam(@Param("userId") Integer userId, @Param("consultantId") Integer consultantId,Pageable pageable);

    @Query(nativeQuery = true, value = "select * from consult_transaction " +
            "where user_id = :userId and consultant_id = :consultantId and status = 'PENDING' ")
    Optional<ConsultTransaction> getPendingTransaction(@Param("userId") Integer userId, @Param("consultantId") Integer consultantId);

}
