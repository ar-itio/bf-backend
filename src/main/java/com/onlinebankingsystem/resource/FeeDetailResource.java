package com.onlinebankingsystem.resource;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.onlinebankingsystem.dto.CommonApiResponse;
import com.onlinebankingsystem.dto.EmailTempDetailResponse;
import com.onlinebankingsystem.dto.FeeDetailResponse;
import com.onlinebankingsystem.entity.FeeDetail;
import com.onlinebankingsystem.entity.EmailTempDetails;
import com.onlinebankingsystem.service.EmailTempService;
import com.onlinebankingsystem.service.FeeDetailService;
import com.onlinebankingsystem.utility.Constants.EmailTemplate;
import com.onlinebankingsystem.utility.Constants.FeeType;

@Component
public class FeeDetailResource {
	
	private final Logger LOG = LoggerFactory.getLogger(FeeDetailResource.class);
	
	@Autowired
	private FeeDetailService feeDetailService;
	@Autowired
	private EmailTempService emailTempService;


	public ResponseEntity<CommonApiResponse> addFeeDetail(FeeDetail feeDetail) {
		
		LOG.info("Received request for register user");

		CommonApiResponse response = new CommonApiResponse();

		if (feeDetail == null || feeDetail.getFee() == null || feeDetail.getType() == null || feeDetail.getFeeAmount() == null) {
			response.setResponseMessage("bad request - missing data");
			response.setSuccess(true);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		FeeDetail fetchedFeeDetail = this.feeDetailService.getFeeDetailByType(feeDetail.getType());
		
		if(fetchedFeeDetail == null) {
			this.feeDetailService.addFeeDetail(feeDetail);
		} else {
			fetchedFeeDetail.setFee(feeDetail.getFee());
			fetchedFeeDetail.setFeeAmount(feeDetail.getFeeAmount());
			
			this.feeDetailService.addFeeDetail(fetchedFeeDetail);
		}

		response.setResponseMessage("Fee Detail Updated Successful!!!");
		response.setSuccess(true);

		return new ResponseEntity<CommonApiResponse>(response, HttpStatus.OK);
		
	}

	public ResponseEntity<FeeDetailResponse> fetchFeeDetails() {
		
		LOG.info("Received request for fetching fee details");

		FeeDetailResponse response = new FeeDetailResponse();

		List<FeeDetail> feeDetails = this.feeDetailService.getAllFeeDetails();
		
		if(feeDetails == null) {
			response.setResponseMessage("no fees entry found");
			response.setSuccess(false);

			return new ResponseEntity<FeeDetailResponse>(response, HttpStatus.OK);
		} 

		response.setFeeDetails(feeDetails);
		response.setResponseMessage("Fee Detail Fetched Successful!!!");
		response.setSuccess(true);

		return new ResponseEntity<FeeDetailResponse>(response, HttpStatus.OK);
		
	}

	public ResponseEntity fetchFeeType() {
		
		List<String> feeTypes = new ArrayList<>();
		
		for(FeeType type: FeeType.values()) {
			feeTypes.add(type.value());
		}
		
		return new ResponseEntity(feeTypes, HttpStatus.OK);
	}
	
	
	public ResponseEntity<FeeDetailResponse> fetchFeeByType(String Type) {
		
		FeeDetail feeDetail = this.feeDetailService.getFeeDetailByType(FeeType.DEBIT_TRANSACTION.value());
		if(feeDetail == null) {
			
		return new ResponseEntity(feeDetail, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity(feeDetail, HttpStatus.OK);
	}
	
	
	
	
//add by prince
public ResponseEntity<EmailTempDetailResponse> fetchEmailTemp() {
	
	LOG.info("Received request for fetching fee details");

	EmailTempDetailResponse response = new EmailTempDetailResponse();
	EmailTempDetails e=new EmailTempDetails();

	if(this.emailTempService.getEmailTempDetailsByCode(EmailTemplate.OTP.value())==null) {
		EmailTempDetails pojo1=new EmailTempDetails();
		pojo1.setCode(EmailTemplate.OTP.value());
		pojo1.setEmailMessage("Thank you for choosing [Neobanking Application]\r\n"
				+ ". Use the following OTP to complete your Sign Up procedures. OTP is valid for 5 minutes\r\n"
				+ "");
		pojo1.setEmailSubject("OTP");
		this.emailTempService.addEmailTempDetails(pojo1);		
	} 
	if(this.emailTempService.getEmailTempDetailsByCode(EmailTemplate.SIGN_UP_APPROVAL.value())==null) {
		EmailTempDetails pojo1=new EmailTempDetails();
		pojo1.setCode(EmailTemplate.SIGN_UP_APPROVAL.value());
		pojo1.setEmailMessage("Welcome to [Neobanking Application]! We're thrilled to have you join our community of savvy users who are revolutionizing the way they manage their finances.\r\n"
				+ "\r\n"
				+ "Your account has been successfully created, and you are now part of a modern banking experience designed to make managing your money simple, secure, and convenient.\r\n"
				+ "Happy banking!\r\n"
				+ "");
		pojo1.setEmailSubject("Welcome to [Neobanking Application] - Let's Get Started!");
		this.emailTempService.addEmailTempDetails(pojo1);		
	} 
	
	if(this.emailTempService.getEmailTempDetailsByCode(EmailTemplate.ADD_MONEY.value())==null) {
		EmailTempDetails pojo=new EmailTempDetails();
		pojo.setCode(EmailTemplate.ADD_MONEY.value());
		pojo.setEmailMessage("We are thrilled to inform you that your recent Add fund to your [Neobank] account was successfully \n "
				+ "completed! Your commitment to managing your finances efficiently is commendable, and we're \n"
				+ "delighted to be a part of your financial journey. \n\n\n  Here are the details of your recent transaction: \n\n\n "
				+ "Account No: [account_Number]\r\n"
				+ "Transaction Date: [date]\r\n"
				+ "Amount Transferred: [amount]\r\n"
				+ "Transaction Fee: [fee]\r\n"
				+ "Total Credited Amount: [total_amount]");
		pojo.setEmailSubject("Congratulations! You've Successfully Added Funds to Your Neobank Account ");
		this.emailTempService.addEmailTempDetails(pojo);
	}
	if(this.emailTempService.getEmailTempDetailsByCode(EmailTemplate.BENEFICIARY_TRANSFER.value())==null) {
		EmailTempDetails pojo1=new EmailTempDetails();
		pojo1.setCode(EmailTemplate.BENEFICIARY_TRANSFER.value());
		pojo1.setEmailMessage("We are thrilled to inform you that your recent fund transfer to the [beneficiary_name] account was successfully completed! Your commitment to managing your finances efficiently is commendable, and we're delighted to be a part of your financial journey.\r\n"
				+ "\r\n"
				+ "Here are the details of your recent transaction:\r\n"
				+ "Account No: [account_Number]\r\n"
				+ "Transaction Date: [date]\r\n"
				+ "Amount Transferred: [amount]\r\n"
				+ "Transaction Fee: [fee]\r\n"
				+ "Total Credited Amount: [total_amount]");
		pojo1.setEmailSubject("Beneficiary  trsansfer");
		this.emailTempService.addEmailTempDetails(pojo1);		
	} 
	if(this.emailTempService.getEmailTempDetailsByCode(EmailTemplate.RESET_PASSWORD.value())==null) {
		EmailTempDetails pojo1=new EmailTempDetails();
		pojo1.setCode(EmailTemplate.RESET_PASSWORD.value());
		pojo1.setEmailMessage("Dear Customer, You have successfully changed your [Neobanking Application] Login password [date]. Do not share with anyone. -SBI.\r\n"
				+ "\r\n"
				+ "Do not disclose any confidential information such as Username, Password, OTP etc. to anyone.\r\n"
				+ "\r\n"
				+ "Happy banking!\r\n"
				+ "");
		pojo1.setEmailSubject("Reset Password");
		this.emailTempService.addEmailTempDetails(pojo1);		
	} 
	if(this.emailTempService.getEmailTempDetailsByCode(EmailTemplate.RESET_PASSWORD_REQUEST.value())==null) {
		EmailTempDetails pojo1=new EmailTempDetails();
		pojo1.setCode(EmailTemplate.RESET_PASSWORD_REQUEST.value());
		pojo1.setEmailMessage("This email is to Confirm that you requested a password reset. To complete the password reset process , click the link below.\r\n"
				+ "                                                                 [password]\r\n"
				+ "\r\n"
				+ "Happy banking!\r\n"
				+ "");
		pojo1.setEmailSubject("Reset Password Request");
		this.emailTempService.addEmailTempDetails(pojo1);		
	} 
	
	List<EmailTempDetails>emailTempDetails = this.emailTempService.getAllEmailTempDetails();
	response.setEmailTempDetails(emailTempDetails);
	response.setResponseMessage("Fee Detail Fetched Successful!!!");
	response.setSuccess(true);

	return new ResponseEntity<EmailTempDetailResponse>(response, HttpStatus.OK);
	
}

public ResponseEntity<CommonApiResponse> updateEmailTemp(EmailTempDetails emailTempDetails) {
	
	LOG.info("Received request for register user");

	CommonApiResponse response = new CommonApiResponse();

	if (emailTempDetails == null ) {
		response.setResponseMessage("bad request - missing data");
		response.setSuccess(true);

		return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
	}

	EmailTempDetails fetchedemailTemp = this.emailTempService.getEmailTempDetailsByCode(emailTempDetails.getCode());
	
	if(fetchedemailTemp == null) {
		this.emailTempService.addEmailTempDetails(emailTempDetails);
	} else {
		fetchedemailTemp.setEmailSubject(emailTempDetails.getEmailSubject());
		fetchedemailTemp.setEmailMessage(emailTempDetails.getEmailMessage());
		
		this.emailTempService.addEmailTempDetails(fetchedemailTemp);
	}

	response.setResponseMessage("Fee Detail Updated Successful!!!");
	response.setSuccess(true);

	return new ResponseEntity<CommonApiResponse>(response, HttpStatus.OK);
	
}

}

