package com.onlinebankingsystem.resource;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.onlinebankingsystem.dto.BeneficiaryAccountResponse;
import com.onlinebankingsystem.dto.CommonApiResponse;
import com.onlinebankingsystem.entity.Beneficiary;
import com.onlinebankingsystem.entity.User;
import com.onlinebankingsystem.service.BeneficiaryService;
import com.onlinebankingsystem.service.UserService;
import com.onlinebankingsystem.utility.Constants.BeneficiaryStatus;

@Component
public class BeneficiaryResource {

	private final Logger LOG = LoggerFactory.getLogger(BankAccountResource.class);

	@Autowired
	private BeneficiaryService beneficiaryService;

	@Autowired
	private UserService userService;

	public ResponseEntity<CommonApiResponse> addBeneficiaryAccount(Beneficiary beneficiary) {

		LOG.info("Received request for add beneficiary account");

		CommonApiResponse response = new CommonApiResponse();

		if (beneficiary == null) {
			response.setResponseMessage("bad request, missing data");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}
		
		if (beneficiary.getUserId()  == null) {
			response.setResponseMessage("bad request, user not found");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		if (beneficiary.getAccountNumber() == null || beneficiary.getBankAddress() == null
				|| beneficiary.getBankName() == null || beneficiary.getBeneficiaryName() == null
				|| beneficiary.getCountry() == null || beneficiary.getSwiftCode() == null
				|| beneficiary.getUserId() == null) {
			response.setResponseMessage("bad request, missing input");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		User user = this.userService.getUserById(beneficiary.getUserId());
		
		beneficiary.setStatus(BeneficiaryStatus.ACTIVE.value());
		beneficiary.setUser(user);

		Beneficiary addedBeneficiary = this.beneficiaryService.addBeneficiary(beneficiary);

		if (addedBeneficiary != null) {

			response.setResponseMessage("Beneficiary Account Added Successful!!!");
			response.setSuccess(true);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.OK);
		} else {
			response.setResponseMessage("Failed to add the beneficiary account");
			response.setSuccess(true);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

	}

	public ResponseEntity<BeneficiaryAccountResponse> getBeneficiaryAccountByUserId(Integer userId) {

		LOG.info("Received request for add beneficiary account");

		BeneficiaryAccountResponse response = new BeneficiaryAccountResponse();

		if (userId == null) {
			response.setResponseMessage("bad request, missing data");
			response.setSuccess(true);

			return new ResponseEntity<BeneficiaryAccountResponse>(response, HttpStatus.BAD_REQUEST);
		}

		User user = this.userService.getUserById(userId);

		if (user == null) {
			response.setResponseMessage("bad request, user not found");
			response.setSuccess(true);

			return new ResponseEntity<BeneficiaryAccountResponse>(response, HttpStatus.BAD_REQUEST);
		}

		List<Beneficiary> beneficiaries = this.beneficiaryService.getAllUserBeneficiaryAndStatus(user,
				BeneficiaryStatus.ACTIVE.value());

		if (beneficiaries != null) {

			response.setBeneficiaryAccounts(beneficiaries);
			response.setResponseMessage("Beneficiary Accounts Fetched Successful!!!");
			response.setSuccess(true);

			return new ResponseEntity<BeneficiaryAccountResponse>(response, HttpStatus.OK);
		} else {
			response.setResponseMessage("No Beneficary accounts found!!!");
			response.setSuccess(true);

			return new ResponseEntity<BeneficiaryAccountResponse>(response, HttpStatus.OK);
		}

	}

	public ResponseEntity<CommonApiResponse> updateBeneficiaryAccount(Beneficiary beneficiary) {

		LOG.info("Received request for add beneficiary account");

		CommonApiResponse response = new CommonApiResponse();

		if (beneficiary == null) {
			response.setResponseMessage("bad request, missing data");
			response.setSuccess(true);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		if (beneficiary.getId() == 0 || beneficiary.getAccountNumber() == null || beneficiary.getBankAddress() == null
				|| beneficiary.getBankName() == null || beneficiary.getBeneficiaryName() == null
				|| beneficiary.getCountry() == null || beneficiary.getSwiftCode() == null
				|| beneficiary.getUser() == null) {
			response.setResponseMessage("bad request, missing input");
			response.setSuccess(true);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		beneficiary.setStatus(BeneficiaryStatus.ACTIVE.value());

		Beneficiary fetchedBeneficiary = this.beneficiaryService.getById(beneficiary.getId());

		beneficiary.setUser(fetchedBeneficiary.getUser());

		Beneficiary addedBeneficiary = this.beneficiaryService.updateBeneficiary(beneficiary);

		if (addedBeneficiary != null) {

			response.setResponseMessage("Beneficiary Account Updated Successful!!!");
			response.setSuccess(true);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.OK);
		} else {
			response.setResponseMessage("Failed to update the beneficiary account");
			response.setSuccess(true);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

	}

	public ResponseEntity<CommonApiResponse> deleteBeneficiaryAccount(Integer beneficiaryId) {

		LOG.info("Received request for add beneficiary account");

		CommonApiResponse response = new CommonApiResponse();

		if (beneficiaryId == null) {
			response.setResponseMessage("bad request, missing data");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		Beneficiary fetchedBeneficiary = this.beneficiaryService.getById(beneficiaryId);

		if (fetchedBeneficiary == null) {
			response.setResponseMessage("bad request, beneficiary not found!!!");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		fetchedBeneficiary.setStatus(BeneficiaryStatus.DEACTIVATED.value());

		Beneficiary addedBeneficiary = this.beneficiaryService.updateBeneficiary(fetchedBeneficiary);

		if (addedBeneficiary != null) {

			response.setResponseMessage("Beneficiary Account Deleted Successful!!!");
			response.setSuccess(true);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.OK);
		} else {
			response.setResponseMessage("Failed to delete the beneficiary account");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

	}

}
