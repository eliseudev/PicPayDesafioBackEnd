package com.picpaytestetecnico.repositories;

import com.picpaytestetecnico.domain.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
