package com.example.accountservice.repository;

import com.example.accountservice.model.MessageDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<MessageDTO, Integer> {
    List<MessageDTO> findByStatus(boolean status);
}
