package com.onlinebankingsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.onlinebankingsystem.dto.BeneficiaryAccountResponse;
import com.onlinebankingsystem.dto.CommonApiResponse;
import com.onlinebankingsystem.entity.Beneficiary;
import com.onlinebankingsystem.resource.BeneficiaryResource;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("api/beneficiary/")
@CrossOrigin(origins = "http://localhost:3000")
public class BeneficiaryController {

	@Autowired
	private BeneficiaryResource beneficiaryResource;

	@PostMapping("add")
	@Operation(summary = "Api to add Beneficiary account")
	public ResponseEntity<CommonApiResponse> addBeneficiaryAccount(@RequestBody Beneficiary beneficiary)
			throws Exception {
		return this.beneficiaryResource.addBeneficiaryAccount(beneficiary);
	}

	@GetMapping("fetch")
	@Operation(summary = "Api to fetch Beneficiary account by used id")
	public ResponseEntity<BeneficiaryAccountResponse> getBeneficiaryAccountByUserId(
			@RequestParam("userId") Integer userId) throws Exception {
		return this.beneficiaryResource.getBeneficiaryAccountByUserId(userId);
	}

	@PutMapping("update")
	@Operation(summary = "Api to update Beneficiary account")
	public ResponseEntity<CommonApiResponse> updateBeneficiaryAccount(@RequestBody Beneficiary beneficiary)
			throws Exception {
		return this.beneficiaryResource.updateBeneficiaryAccount(beneficiary);
	}

	@DeleteMapping("delete")
	@Operation(summary = "Api to delete the Beneficiary account")
	public ResponseEntity<CommonApiResponse> deleteBeneficiaryAccount(@RequestParam("beneficiaryId") Integer beneficiaryId)
			throws Exception {
		return this.beneficiaryResource.deleteBeneficiaryAccount(beneficiaryId);
	}

}
