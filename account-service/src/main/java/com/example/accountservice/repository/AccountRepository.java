package com.example.accountservice.repository;

import com.example.accountservice.model.AccountDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<AccountDTO, Integer> {
}
