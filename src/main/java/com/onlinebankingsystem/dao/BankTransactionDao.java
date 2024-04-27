package com.onlinebankingsystem.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.onlinebankingsystem.entity.BankTransaction;
import com.onlinebankingsystem.entity.User;

@Repository
public interface BankTransactionDao extends JpaRepository<BankTransaction, Integer> {

	List<BankTransaction> findByStatusIn(List<String> status);

	List<BankTransaction> findByStatusInAndUser(List<String> status, User user);

	List<BankTransaction> findByTransactionRefIdContainingIgnoreCaseAndUser(String transactionRefId, User user);

}
