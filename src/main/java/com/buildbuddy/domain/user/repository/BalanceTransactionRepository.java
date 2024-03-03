package com.buildbuddy.domain.user.repository;

import com.buildbuddy.domain.user.entity.BalanceTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BalanceTransactionRepository extends JpaRepository<BalanceTransaction, Integer> {

    @Query(nativeQuery = true, value = "select * from balance_transaction " +
            "where transaction_id = :transactionId and user_id = :userId and transaction_type = :transactionType")
    Optional<BalanceTransaction> findByTransactionIdAndUserId(@Param("transactionId") Integer transactionId,
                                                              @Param("userId") Integer userId,
                                                              @Param("transactionType") String transactionType);

}
