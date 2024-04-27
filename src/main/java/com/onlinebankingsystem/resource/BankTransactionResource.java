package com.onlinebankingsystem.resource;
import java.text.SimpleDateFormat;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.onlinebankingsystem.dto.BankTransactionRequestDto;
import com.onlinebankingsystem.dto.BankTransactionResponse;
import com.onlinebankingsystem.dto.CommonApiResponse;
import com.onlinebankingsystem.dto.UserStatusUpdateRequestDto;
import com.onlinebankingsystem.entity.BankTransaction;
import com.onlinebankingsystem.entity.Beneficiary;
import com.onlinebankingsystem.entity.EmailTempDetails;
import com.onlinebankingsystem.entity.FeeDetail;
import com.onlinebankingsystem.entity.User;
import com.onlinebankingsystem.service.BankTransactionService;
import com.onlinebankingsystem.service.BeneficiaryService;
import com.onlinebankingsystem.service.EmailService;
import com.onlinebankingsystem.service.EmailTempService;
import com.onlinebankingsystem.service.FeeDetailService;
import com.onlinebankingsystem.service.UserService;
import com.onlinebankingsystem.utility.Constants.BankTransactionStatus;
import com.onlinebankingsystem.utility.Constants.EmailTemplate;
import com.onlinebankingsystem.utility.Constants.FeeType;
import com.onlinebankingsystem.utility.Constants.TransactionType;
import com.onlinebankingsystem.utility.TransactionIdGenerator;

@Component
public class BankTransactionResource {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//Add by Prince For Filter transaction

	@Autowired
	private BankTransactionService bankTransactionService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private BeneficiaryService beneficiaryService;

	@Autowired
	private FeeDetailService feeDetailService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private EmailTempService emailTempService;
	
	public ResponseEntity<CommonApiResponse> addMoney(BankTransactionRequestDto request) {

		CommonApiResponse response = new CommonApiResponse();

		if (request == null || request.getAmount() == null || request.getUserId() == 0) {
			response.setResponseMessage("bad request, missing data");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		if (request.getUserId() == 0) {
			response.setResponseMessage("bad request, Bank user not selected");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		User customer = this.userService.getUserById(request.getUserId());

		if (customer == null) {
			response.setResponseMessage("bad request, customer not found");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}
		
		FeeDetail feeDetail = this.feeDetailService.getFeeDetailByType(FeeType.CREDIT_TRANSACTION.value());
		
		if(feeDetail == null) {
			response.setResponseMessage("Fee Detail not found, Internal error!!!");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		//add by prince
		BigDecimal totalAmount = request.getAmount();
		BigDecimal feePer= totalAmount.multiply(feeDetail.getFee()).divide(new BigDecimal("100")); // Add By Prince	
		BigDecimal feeAmount =feePer.compareTo(feeDetail.getFeeAmount())>0?feePer:feeDetail.getFeeAmount();
		BigDecimal amountToAdd = totalAmount.subtract(feeAmount);
		
		BankTransaction transaction = new BankTransaction();
		transaction.setFee(String.valueOf(feeAmount) +"[" + String.valueOf(feeDetail.getFee())+"%]");
		transaction.setAmount(amountToAdd);
		transaction.setBillAmount(totalAmount);
		transaction.setSenderName(request.getSenderName());
		transaction.setSenderAddress(request.getSenderAddress());
		transaction.setDescription(request.getDescription());
		transaction.setUser(customer);
		transaction.setType(TransactionType.DEPOSIT.value());
		transaction.setStatus(BankTransactionStatus.PENDING.value());
		transaction.setTransactionRefId(TransactionIdGenerator.generateUniqueTransactionRefId());
	     Date date=new Date(); //Add by Prince For Filter transaction
		transaction.setDate(formatter.format(date));//Add by Prince For Filter transaction

		bankTransactionService.addTransaction(transaction);

		response.setResponseMessage("Add Money Request Intiated!!!");
		response.setSuccess(true);
		return new ResponseEntity<CommonApiResponse>(response, HttpStatus.OK);
	}

	public ResponseEntity<CommonApiResponse> accountTransfer(BankTransactionRequestDto request) {

		CommonApiResponse response = new CommonApiResponse();

		if (request == null || request.getAmount() == null || request.getUserId() == 0
				|| request.getAccountNumber() == null || request.getBankName() == null
				|| request.getSwiftCode() == null) {
			response.setResponseMessage("bad request, missing data");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		if (request.getUserId() == 0) {
			response.setResponseMessage("bad request, Bank user not selected");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		User customer = this.userService.getUserById(request.getUserId());

		if (customer == null) {
			response.setResponseMessage("bad request, customer not found");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		if (customer.getAccountBalance().compareTo(request.getAmount()) < 0) {
			response.setResponseMessage("Insufficient Balance, Failed to transfer amount!!!");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.OK);
		}

//		private String beneficiaryName; // Transfer Money
//
//		private String accountNumber; // Transfer Money
//
//		private String swiftCode; // Transfer Money
//
//		private String bankName; // Transfer Money
//
//		private String bankAddress; // Transfer Money
//
//		private String purpose; // Transfer Money
//		
        
		FeeDetail feeDetail = this.feeDetailService.getFeeDetailByType(FeeType.DEBIT_TRANSACTION.value());
		
		if(feeDetail == null) {
			response.setResponseMessage("Fee Detail not found, Internal error!!!");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		BigDecimal totalAmount = request.getAmount();
		BigDecimal feePer= totalAmount.multiply(feeDetail.getFee()).divide(new BigDecimal("100"));
		BigDecimal feeAmount =feePer.compareTo(feeDetail.getFeeAmount())>0?feePer:feeDetail.getFeeAmount();
		BigDecimal amountToTransfer = totalAmount.add(feeAmount);
		
		BankTransaction transaction = new BankTransaction();
		transaction.setAmount(totalAmount);
		transaction.setBillAmount(amountToTransfer);
		transaction.setFee(String.valueOf(feeAmount) +"[" + String.valueOf(feeDetail.getFee())+"%]");
		transaction.setBeneficiaryName(request.getBeneficiaryName());
		transaction.setAccountNumber(request.getAccountNumber());
		transaction.setSwiftCode(request.getSwiftCode());
		transaction.setBankName(request.getBankName());
		transaction.setBankAddress(request.getBankAddress());
		transaction.setPurpose(request.getPurpose());
		transaction.setUser(customer);
		transaction.setType(TransactionType.ACCOUNT_TRANSFER.value());
		transaction.setStatus(BankTransactionStatus.PENDING.value());
		transaction.setTransactionRefId(TransactionIdGenerator.generateUniqueTransactionRefId());
	     Date date=new Date(); //Add by Prince For Filter transaction
		transaction.setDate(formatter.format(date)); //Add by Prince For Filter transaction

		bankTransactionService.addTransaction(transaction);

		response.setResponseMessage("Account Transfer Request Intiated!!!");
		response.setSuccess(true);

		return new ResponseEntity<CommonApiResponse>(response, HttpStatus.OK);
	}

	public ResponseEntity<BankTransactionResponse> fetchPendingTransaction() {

		BankTransactionResponse response = new BankTransactionResponse();

		List<BankTransaction> transactions = this.bankTransactionService
				.getTransactionByStatusIn(Arrays.asList(BankTransactionStatus.PENDING.value()));

		if (CollectionUtils.isEmpty(transactions)) {
			response.setResponseMessage("No Pending Transactions found!!!");
			response.setSuccess(false);

			return new ResponseEntity<BankTransactionResponse>(response, HttpStatus.OK);
		}

		response.setTransactions(transactions);
		response.setResponseMessage("Account Transfer Request Intiated!!!");
		response.setSuccess(true);

		return new ResponseEntity<BankTransactionResponse>(response, HttpStatus.OK);
	}

	public ResponseEntity<CommonApiResponse> updateTransactionStatus(UserStatusUpdateRequestDto request) {

		CommonApiResponse response = new CommonApiResponse();
		if (request == null || request.getUserId() == 0 || request.getStatus() == null) {
			response.setResponseMessage("bad request, missing data");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		if (request.getUserId() == 0) {
			response.setResponseMessage("bad request, Bank user not selected");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		BankTransaction transaction = this.bankTransactionService.getTransactionId(request.getUserId());

		User customer = transaction.getUser();
		String Code="";

		if (request.getStatus().equals(BankTransactionStatus.REJECT.value())) {
			transaction.setStatus(BankTransactionStatus.REJECT.value());
			bankTransactionService.addTransaction(transaction);

			response.setResponseMessage("Transaction Rejected Successful");
			response.setSuccess(true);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.OK);
		}

		else {
			transaction.setStatus(BankTransactionStatus.SUCCESS.value());
			bankTransactionService.addTransaction(transaction);

			if (transaction.getType().equals(TransactionType.DEPOSIT.value())) {
				customer.setAccountBalance(customer.getAccountBalance().add(transaction.getAmount()));
				Code=EmailTemplate.ADD_MONEY.value();
			} else if (transaction.getType().equals(TransactionType.ACCOUNT_TRANSFER.value())) {
				Code=EmailTemplate.BENEFICIARY_TRANSFER.value();
				BigDecimal actualTransferAmount = transaction.getAmount();
				String feeAmountInString = transaction.getFee().split("\\[")[0];
				BigDecimal feeAmount =  new BigDecimal(feeAmountInString);
				
				BigDecimal amounToDebit = actualTransferAmount.add(feeAmount);
				
				customer.setAccountBalance(customer.getAccountBalance().subtract(amounToDebit));
			}

			userService.updateUser(customer);

			response.setResponseMessage("Transaction Approved Successful");
			response.setSuccess(true);
			sendPasswordGenerationMail(customer, Code,transaction);
			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.OK);
		}

	}

	public ResponseEntity<BankTransactionResponse> fetchSuccessTransaction() {

		BankTransactionResponse response = new BankTransactionResponse();

		List<BankTransaction> transactions = this.bankTransactionService
				.getTransactionByStatusIn(Arrays.asList(BankTransactionStatus.SUCCESS.value()));

		if (CollectionUtils.isEmpty(transactions)) {
			response.setResponseMessage("No Pending Transactions found!!!");
			response.setSuccess(false);

			return new ResponseEntity<BankTransactionResponse>(response, HttpStatus.OK);
		}

		response.setTransactions(transactions);
		response.setResponseMessage("Account Transfer Request Intiated!!!");
		response.setSuccess(true);

		return new ResponseEntity<BankTransactionResponse>(response, HttpStatus.OK);
	}

	public ResponseEntity<BankTransactionResponse> fetchCustomerTransactions(int customerId) {

		BankTransactionResponse response = new BankTransactionResponse();

		if (customerId == 0) {
			response.setResponseMessage("Customer Id not found");
			response.setSuccess(false);

			return new ResponseEntity<BankTransactionResponse>(response, HttpStatus.OK);
		}

		User customer = this.userService.getUserById(customerId);

		if (customer == null) {
			response.setResponseMessage("bad request, customer not found");
			response.setSuccess(false);

			return new ResponseEntity<BankTransactionResponse>(response, HttpStatus.BAD_REQUEST);
		}

		List<BankTransaction> transactions = this.bankTransactionService
				.getTransactionByStatusInAndUser(Arrays.asList(BankTransactionStatus.PENDING.value(),
						BankTransactionStatus.SUCCESS.value(), BankTransactionStatus.REJECT.value()), customer);

		if (CollectionUtils.isEmpty(transactions)) {
			response.setResponseMessage("No Pending Transactions found!!!");
			response.setSuccess(false);

			return new ResponseEntity<BankTransactionResponse>(response, HttpStatus.OK);
		}

		response.setTransactions(transactions);
		response.setResponseMessage("Account Transfer Request Intiated!!!");
		response.setSuccess(true);

		return new ResponseEntity<BankTransactionResponse>(response, HttpStatus.OK);
	}

	public ResponseEntity<BankTransactionResponse> fetchCustomerTransactionsByTransactionRefId(String transactionRefId,
			int customerId) {

		BankTransactionResponse response = new BankTransactionResponse();

		if (customerId == 0) {
			response.setResponseMessage("Customer Id not found");
			response.setSuccess(false);

			return new ResponseEntity<BankTransactionResponse>(response, HttpStatus.OK);
		}

		User customer = this.userService.getUserById(customerId);

		if (customer == null) {
			response.setResponseMessage("bad request, customer not found");
			response.setSuccess(false);

			return new ResponseEntity<BankTransactionResponse>(response, HttpStatus.BAD_REQUEST);
		}

		List<BankTransaction> transactions = this.bankTransactionService
				.getTransactionByTransactionRedIdInAndUser(transactionRefId, customer);

		if (CollectionUtils.isEmpty(transactions)) {
			response.setResponseMessage("No Pending Transactions found!!!");
			response.setSuccess(false);

			return new ResponseEntity<BankTransactionResponse>(response, HttpStatus.OK);
		}

		response.setTransactions(transactions);
		response.setResponseMessage("Account Transfer Request Intiated!!!");
		response.setSuccess(true);

		return new ResponseEntity<BankTransactionResponse>(response, HttpStatus.OK);
	}

	public ResponseEntity<CommonApiResponse> quickAccountTransfer(BankTransactionRequestDto request) {

		CommonApiResponse response = new CommonApiResponse();

		if (request == null || request.getAmount() == null || request.getUserId() == 0) {
			response.setResponseMessage("bad request, missing data");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}
		
		if (request.getBeneficiaryId() ==null) {
			response.setResponseMessage("bad request, beneficary not selected");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		if (request.getUserId() == 0) {
			response.setResponseMessage("bad request, user not selected");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		User customer = this.userService.getUserById(request.getUserId());

		if (customer == null) {
			response.setResponseMessage("bad request, customer not found");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		if (customer.getAccountBalance().compareTo(request.getAmount()) < 0) {
			response.setResponseMessage("Insufficient Balance, Failed to transfer amount!!!");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.OK);
		}
		
		Beneficiary beneficiary = this.beneficiaryService.getById(request.getBeneficiaryId());
		
		if(beneficiary == null) {
			response.setResponseMessage("bad request - beneficiart not found!!!");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.OK);
		}

//		private String beneficiaryName; // Transfer Money
//
//		private String accountNumber; // Transfer Money
//
//		private String swiftCode; // Transfer Money
//
//		private String bankName; // Transfer Money
//
//		private String bankAddress; // Transfer Money
//
//		private String purpose; // Transfer Money
//		

		FeeDetail feeDetail = this.feeDetailService.getFeeDetailByType(FeeType.DEBIT_TRANSACTION.value());
		
		if(feeDetail == null) {
			response.setResponseMessage("Fee Detail not found, Internal error!!!");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		BigDecimal totalAmount = request.getAmount();
		BigDecimal feePer= totalAmount.multiply(feeDetail.getFee()).divide(new BigDecimal("100"));
		BigDecimal feeAmount =feePer.compareTo(feeDetail.getFeeAmount())>0?feePer:feeDetail.getFeeAmount();
		BigDecimal amountToTransfer = totalAmount.subtract(feeAmount);
		
		BankTransaction transaction = new BankTransaction();
		transaction.setFee(String.valueOf(feeAmount) +"[" + String.valueOf(feeDetail.getFee())+"%]");
		transaction.setAmount(amountToTransfer);
		transaction.setBeneficiaryName(beneficiary.getBeneficiaryName());
		transaction.setAccountNumber(beneficiary.getAccountNumber());
		transaction.setSwiftCode(beneficiary.getSwiftCode());
		transaction.setBankName(beneficiary.getBankName());
		transaction.setBankAddress(beneficiary.getBankAddress());
		transaction.setCountry(beneficiary.getCountry());
		transaction.setPurpose(request.getPurpose());
		transaction.setUser(customer);
		transaction.setType(TransactionType.ACCOUNT_TRANSFER.value());
		transaction.setStatus(BankTransactionStatus.PENDING.value());
		transaction.setTransactionRefId(TransactionIdGenerator.generateUniqueTransactionRefId());
		transaction.setCurrency(request.getCurrency());
	     Date date=new Date(); //Add by Prince For Filter transaction
		transaction.setDate(formatter.format(date)); //Add by Prince For Filter transaction

		bankTransactionService.addTransaction(transaction);

		response.setResponseMessage("Quick Account Transfer Request Intiated!!!");
		response.setSuccess(true);

		return new ResponseEntity<CommonApiResponse>(response, HttpStatus.OK);
	}
	
	private void sendPasswordGenerationMail(User customer, String code,BankTransaction transaction) {
	    // Fetch the email template based on the provided code
	    EmailTempDetails fetchedEmailTemp = this.emailTempService.getEmailTempDetailsByCode(code);

	    if (fetchedEmailTemp != null) {
	        // Get the email message and replace placeholders with user information
	        String msg = fetchedEmailTemp.getEmailMessage();
	        msg = replaceUserPlaceholders(msg, customer,transaction);
	        // Get the email subject and replace placeholders with user information
	        String subject = fetchedEmailTemp.getEmailSubject();
	        subject = replaceUserPlaceholders(subject, customer,transaction);
	        // Construct the email body
	        StringBuilder emailBody = new StringBuilder();
	        emailBody.append("<html><body>");
			emailBody.append("<h3>Dear " + customer.getName() + ",</h3>");
	        emailBody.append(msg);
	        emailBody.append("<p>Best Regards,<br/>Bank</p>");
	        emailBody.append("</body></html>");

	        // Send the email
	        this.emailService.sendEmail(customer.getEmail(), subject, emailBody.toString());
	    }
	}

	// Helper method to replace user placeholders in a string
	private String replaceUserPlaceholders(String template, User customer,BankTransaction transaction) {
	    // Replace placeholders with user information, handling null values
	    template = template.replace("[first_name]", customer.getFirstName() != null ? customer.getFirstName() : "");
	    template = template.replace("[last_name]", customer.getLastName() != null ? customer.getLastName() : "");
	    template = template.replace("[contactNumber]", customer.getContactNumber() != null ? customer.getContactNumber() : "");
	    template = template.replace("[gender]", customer.getGender() != null ? customer.getGender() : "");
	    template = template.replace("[date_of_birth]", customer.getDateOfBirth() != null ? customer.getDateOfBirth() : "");
	    template = template.replace("[address]", customer.getAddress() != null ? customer.getAddress() : "");
	    template = template.replace("[address2]", customer.getAddress2() != null ? customer.getAddress2() : "");
	    template = template.replace("[city]", customer.getCity() != null ? customer.getCity() : "");
	    template = template.replace("[state]", customer.getState() != null ? customer.getState() : "");
	    template = template.replace("[country]", customer.getCountry() != null ? customer.getCountry() : "");
	    template = template.replace("[individual_or_corporate]", customer.getIndividualOrCorporate() != null ? customer.getIndividualOrCorporate() : "");
	    template = template.replace("[employment_status]", customer.getEmploymentStatus() != null ? customer.getEmploymentStatus() : "");
	    template = template.replace("[role_in_company]", customer.getRoleInCompany() != null ? customer.getRoleInCompany() : "");
	    template = template.replace("[business_activity]", customer.getBusinessActivity() != null ? customer.getBusinessActivity() : "");
	    template = template.replace("[enter_activity]", customer.getEnterActivity() != null ? customer.getEnterActivity() : "");
	    template = template.replace("[company_name]", customer.getCompanyName() != null ? customer.getCompanyName() : "");
	    template = template.replace("[company_registration_number]", customer.getCompanyRegistrationNumber() != null ? customer.getCompanyRegistrationNumber() : "");
	    template = template.replace("[date_of_incorporation]", customer.getDateOfIncorporation() != null ? customer.getDateOfIncorporation() : "");
	    template = template.replace("[country_of_incorporation]", customer.getCountryOfIncorporation() != null ? customer.getCountryOfIncorporation() : "");
	    template = template.replace("[company_address]", customer.getCompanyAddress() != null ? customer.getCompanyAddress() : "");
	    template = template.replace("[nationality]", customer.getNationality() != null ? customer.getNationality() : "");
	    template = template.replace("[place_of_birth]", customer.getPlaceOfBirth() != null ? customer.getPlaceOfBirth() : "");
	    template = template.replace("[id_type]", customer.getIdType() != null ? customer.getIdType() : "");
	    template = template.replace("[id_number]", customer.getIdNumber() != null ? customer.getIdNumber() : "");
	    template = template.replace("[id_expiry_date]", customer.getIdExpiryDate() != null ? customer.getIdExpiryDate() : "");
	    template = template.replace("[account_number]", customer.getAccountNumber() != null ? customer.getAccountNumber() : "");
	    
	    //Transactions
        template = template.replace("[amount]", transaction.getAmount() != null ? transaction.getAmount().toString() : "");
        template = template.replace("[sender_name]", transaction.getSenderName() != null ? transaction.getSenderName() : "");
        template = template.replace("[sender_address]", transaction.getSenderAddress() != null ? transaction.getSenderAddress() : "");
        template = template.replace("[description]", transaction.getDescription() != null ? transaction.getDescription() : "");
        template = template.replace("[beneficiary_name]", transaction.getBeneficiaryName() != null ? transaction.getBeneficiaryName() : "");
      //  template = template.replace("[account_number]", transaction.getAccountNumber() != null ? transaction.getAccountNumber() : "");
        template = template.replace("[swift_code]", transaction.getSwiftCode() != null ? transaction.getSwiftCode() : "");
        template = template.replace("[bank_name]", transaction.getBankName() != null ? transaction.getBankName() : "");
        template = template.replace("[bank_address]", transaction.getBankAddress() != null ? transaction.getBankAddress() : "");
        template = template.replace("[country]", transaction.getCountry() != null ? transaction.getCountry() : "");
        template = template.replace("[purpose]", transaction.getPurpose() != null ? transaction.getPurpose() : "");
        template = template.replace("[type]", transaction.getType() != null ? transaction.getType() : "");
        template = template.replace("[status]", transaction.getStatus() != null ? transaction.getStatus() : "");
        template = template.replace("[transaction_ref_id]", transaction.getTransactionRefId() != null ? transaction.getTransactionRefId() : "");
        template = template.replace("[date]", transaction.getDate() != null ? transaction.getDate() : "");
        template = template.replace("[currency]", transaction.getCurrency() != null ? transaction.getCurrency() : "");
        template = template.replace("[fee]", transaction.getFee() != null ? transaction.getFee() : "");
        template = template.replace("[total_amount]", transaction.getAmount().add(new BigDecimal(transaction.getFee().split("\\[")[0])) != null ? transaction.getAmount().toString() : "");
 
        template=template.replace("\n", "<br>");
	    return template;
	}
}
