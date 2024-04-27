package com.onlinebankingsystem.service;

import java.util.List;

import com.onlinebankingsystem.entity.BankTransaction;
import com.onlinebankingsystem.entity.User;

public interface BankTransactionService {

	BankTransaction addTransaction(BankTransaction bankTransaction);

	BankTransaction updateTransaction(BankTransaction bankTransaction);

	BankTransaction getTransactionId(int transactionId);

	List<BankTransaction> getTransactionByStatusIn(List<String> status);

	List<BankTransaction> getTransactionByStatusInAndUser(List<String> status, User user);
	
	List<BankTransaction> getTransactionByTransactionRedIdInAndUser(String transactionRefId, User user);

}
