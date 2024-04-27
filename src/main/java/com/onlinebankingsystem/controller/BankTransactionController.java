package com.onlinebankingsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.onlinebankingsystem.dto.BankTransactionRequestDto;
import com.onlinebankingsystem.dto.BankTransactionResponse;
import com.onlinebankingsystem.dto.CommonApiResponse;
import com.onlinebankingsystem.dto.UserStatusUpdateRequestDto;
import com.onlinebankingsystem.resource.BankTransactionResource;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("api/transaction/")
@CrossOrigin(origins = "http://localhost:3000")
public class BankTransactionController {

	@Autowired
	private BankTransactionResource bankTransactionResource;

	@PostMapping("addMoney")
	@Operation(summary = "Api for add money")
	public ResponseEntity<CommonApiResponse> addMoney(@RequestBody BankTransactionRequestDto request) throws Exception {
		return this.bankTransactionResource.addMoney(request);
	}

	@PostMapping("accountTransfer")
	@Operation(summary = "Api for account transfer")
	public ResponseEntity<CommonApiResponse> accountTransfer(@RequestBody BankTransactionRequestDto request)
			throws Exception {
		return this.bankTransactionResource.accountTransfer(request);
	}

	@GetMapping("fetch/transactions/pending")
	@Operation(summary = "Api to get pending transactions")
	public ResponseEntity<BankTransactionResponse> fetchPendingTransaction() throws Exception {
		return this.bankTransactionResource.fetchPendingTransaction();
	}

	@GetMapping("fetch/transactions/success")
	@Operation(summary = "Api to get success transactions")
	public ResponseEntity<BankTransactionResponse> fetchSuccessTransaction() throws Exception {
		return this.bankTransactionResource.fetchSuccessTransaction();
	}

	@GetMapping("fetch/customer/transactions/all")
	@Operation(summary = "Api to get customer transactions")
	public ResponseEntity<BankTransactionResponse> fetchCustomerTransactions(@RequestParam("customerId") int customerId)
			throws Exception {
		return this.bankTransactionResource.fetchCustomerTransactions(customerId);
	}

	@GetMapping("search/customer/transactions/ref-id")
	@Operation(summary = "Api to serach the customer transactions")
	public ResponseEntity<BankTransactionResponse> searchCustomerTransactionsByRefId(
			@RequestParam("transactionRefId") String transactionRefId, @RequestParam("customerId") int customerId)
			throws Exception {
		return this.bankTransactionResource.fetchCustomerTransactionsByTransactionRefId(transactionRefId, customerId);
	}

	@PutMapping("update/status")
	@Operation(summary = "Api to update the transaction status")
	public ResponseEntity<CommonApiResponse> updateTransactionStatus(@RequestBody UserStatusUpdateRequestDto request)
			throws Exception {
		return this.bankTransactionResource.updateTransactionStatus(request);
	}

	@PostMapping("quick/accountTransfer")
	@Operation(summary = "Api for quick account transfer")
	public ResponseEntity<CommonApiResponse> quickAccountTransfer(@RequestBody BankTransactionRequestDto request)
			throws Exception {
		return this.bankTransactionResource.quickAccountTransfer(request);
	}

}
