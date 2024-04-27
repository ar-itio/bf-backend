package com.onlinebankingsystem.utility;

public class Constants {

	public enum UserRole {
		ROLE_CUSTOMER("CUSTOMER"), ROLE_ADMIN("ADMIN"), ROLE_BANK("BANK");

		private String role;

		private UserRole(String role) {
			this.role = role;
		}

		public String value() {
			return this.role;
		}
	}

	public enum UserStatus {
		ACTIVE("Active"), PENDING("Pending"), DEACTIVATED("Deactivated"), REJECT("Reject");

		private String status;

		private UserStatus(String status) {
			this.status = status;
		}

		public String value() {
			return this.status;
		}
	}

	public enum IsAccountLinked {
		YES("Yes"), NO("No");

		private String status;

		private IsAccountLinked(String status) {
			this.status = status;
		}

		public String value() {
			return this.status;
		}
	}

	public enum BankAccountStatus {
		OPEN("Open"), DELETED("Deleted"), LOCK("Lock");

		private String status;

		private BankAccountStatus(String status) {
			this.status = status;
		}

		public String value() {
			return this.status;
		}
	}

	public enum BankAccountType {
		SAVING("Saving"), CURRENT("Current");

		private String type;

		private BankAccountType(String type) {
			this.type = type;
		}

		public String value() {
			return this.type;
		}
	}

	public enum TransactionType {
		WITHDRAW("Withdraw"), DEPOSIT("Deposit"), BALANCE_FETCH("Balance Fetch"), ACCOUNT_TRANSFER("Account Transfer");

		private String type;

		private TransactionType(String type) {
			this.type = type;
		}

		public String value() {
			return this.type;
		}
	}

	public enum TransactionNarration {
		BANK_WITHDRAW("Bank Cash Withdraw"), BANK_DEPOSIT("Bank Cash Deposit");

		private String narration;

		private TransactionNarration(String narration) {
			this.narration = narration;
		}

		public String value() {
			return this.narration;
		}
	}

	public enum BankTransactionStatus {
		SUCCESS("Success"), PENDING("Pending"), REJECT("Reject"), APPROVE("Approve");

		private String status;

		private BankTransactionStatus(String status) {
			this.status = status;
		}

		public String value() {
			return this.status;
		}
	}
	
	public enum BeneficiaryStatus {
		ACTIVE("Active"), DEACTIVATED("Deactivated");

		private String status;

		private BeneficiaryStatus(String status) {
			this.status = status;
		}

		public String value() {
			return this.status;
		}
	}
	
	public enum FeeType {
		DEBIT_TRANSACTION("Debit Transaction"),
		CREDIT_TRANSACTION("Credit Transaction"),
		SET_UP_FEE("Set Up Fee"),
		MONTHLY_FEE("Monthly Fee"),
		YEARLY_FEE("Yearly Fee");

		private String type;

		private FeeType(String type) {
			this.type = type;
		}

		public String value() {
			return this.type;
		}
	}
	public enum EmailTemplate  {
		OTP("OTP"), ADD_MONEY("Deposit"), BENEFICIARY_TRANSFER("Account transfer"), RESET_PASSWORD("Reset Password"),RESET_PASSWORD_REQUEST("Reset Password Request"),SIGN_UP_APPROVAL("Sign up Approval");

		private String type;

		private EmailTemplate(String type) {
			this.type = type;
		}

		public String value() {
			return this.type;
		}
	}

}
