package com.onlinebankingsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.onlinebankingsystem.dto.CommonApiResponse;
import com.onlinebankingsystem.dto.EmailTempDetailResponse;
import com.onlinebankingsystem.dto.FeeDetailResponse;
import com.onlinebankingsystem.entity.EmailTempDetails;
import com.onlinebankingsystem.entity.FeeDetail;
import com.onlinebankingsystem.resource.FeeDetailResource;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("api/fee/detail/")
@CrossOrigin
public class FeeDetailController {
	
	@Autowired
	private FeeDetailResource feeDetailResource;


	@PostMapping("add")
	@Operation(summary = "Api to add or update the fee detail")
	public ResponseEntity<CommonApiResponse> addFeeDetail(@RequestBody FeeDetail feeDetail) throws Exception {
		return this.feeDetailResource.addFeeDetail(feeDetail);
	}
	
	@GetMapping("fetch/all")
	@Operation(summary = "Api to fetch all fee details")
	public ResponseEntity<FeeDetailResponse> fetchFeeDetail() throws Exception {
		return this.feeDetailResource.fetchFeeDetails();
	}
	
	@GetMapping("type")
	@Operation(summary = "Api to fetch all fee type")
	public ResponseEntity fetchFeeType() throws Exception {
		return this.feeDetailResource.fetchFeeType();
	}
	@GetMapping("fetch/type")
	@Operation(summary = "Api to fetch all fee type")
	public ResponseEntity fetchFeeByType() throws Exception {
		return this.feeDetailResource.fetchFeeByType("");
	}
	
	@GetMapping("fetch/emailTemp/all")
	@Operation(summary = "Api to fetch all fee details")
	public ResponseEntity<EmailTempDetailResponse> fetchEmailTemp() throws Exception {
		return this.feeDetailResource.fetchEmailTemp();
	}
	@PostMapping("update/emailTemp")
	public ResponseEntity<CommonApiResponse> UpdateEmailTemp(@RequestBody EmailTempDetails emailTempDetails) throws Exception {
		return this.feeDetailResource.updateEmailTemp(emailTempDetails);
	}
}
