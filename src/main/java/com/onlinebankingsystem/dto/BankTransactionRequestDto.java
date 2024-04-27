package com.onlinebankingsystem.dto;

import java.math.BigDecimal;

public class BankTransactionRequestDto {

	private int userId;

	private int bankId;

//	private BigDecimal amount;

	private int sourceBankAccountId;

	private String transactionType;

	private String toBankAccount; // for account transfer

	private String toBankIfsc; // for account transfer

	private String accountTransferPurpose;

	private BigDecimal amount; // add money, Transfer Money

	private String senderName; // add money

	private String senderAddress; // add money

	private String description; // add money

	private String beneficiaryName; // Transfer Money

	private String accountNumber; // Transfer Money

	private String swiftCode; // Transfer Money

	private String bankName; // Transfer Money

	private String bankAddress; // Transfer Money

	private String purpose; // Transfer Money

	private Integer beneficiaryId; // for quick account transfer

	private String currency; // for quick account transfer

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getBankId() {
		return bankId;
	}

	public void setBankId(int bankId) {
		this.bankId = bankId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public int getSourceBankAccountId() {
		return sourceBankAccountId;
	}

	public void setSourceBankAccountId(int sourceBankAccountId) {
		this.sourceBankAccountId = sourceBankAccountId;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getToBankAccount() {
		return toBankAccount;
	}

	public void setToBankAccount(String toBankAccount) {
		this.toBankAccount = toBankAccount;
	}

	public String getToBankIfsc() {
		return toBankIfsc;
	}

	public void setToBankIfsc(String toBankIfsc) {
		this.toBankIfsc = toBankIfsc;
	}

	public String getAccountTransferPurpose() {
		return accountTransferPurpose;
	}

	public void setAccountTransferPurpose(String accountTransferPurpose) {
		this.accountTransferPurpose = accountTransferPurpose;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getSenderAddress() {
		return senderAddress;
	}

	public void setSenderAddress(String senderAddress) {
		this.senderAddress = senderAddress;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBeneficiaryName() {
		return beneficiaryName;
	}

	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getSwiftCode() {
		return swiftCode;
	}

	public void setSwiftCode(String swiftCode) {
		this.swiftCode = swiftCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankAddress() {
		return bankAddress;
	}

	public void setBankAddress(String bankAddress) {
		this.bankAddress = bankAddress;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public Integer getBeneficiaryId() {
		return beneficiaryId;
	}

	public void setBeneficiaryId(Integer beneficiaryId) {
		this.beneficiaryId = beneficiaryId;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

}
