package com.buildbuddy.domain.user.repository;

import com.buildbuddy.domain.user.entity.BalanceTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BalanceTransactionRepository extends JpaRepository<BalanceTransaction, Integer> {
}
