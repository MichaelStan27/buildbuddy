package com.buildbuddy.domain.consult.repository;

import com.buildbuddy.domain.consult.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Integer> {
}
