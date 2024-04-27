package com.onlinebankingsystem.dto;

import java.util.ArrayList;
import java.util.List;

import com.onlinebankingsystem.entity.BankTransaction;

public class BankTransactionResponse extends CommonApiResponse {

	List<BankTransaction> transactions = new ArrayList<>();

	public List<BankTransaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<BankTransaction> transactions) {
		this.transactions = transactions;
	}

}
