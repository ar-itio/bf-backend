package com.onlinebankingsystem.service;

import java.util.List;

import com.onlinebankingsystem.entity.FeeDetail;

public interface FeeDetailService {

	FeeDetail addFeeDetail(FeeDetail feeDetail);
	
	FeeDetail getFeeDetailById(int feeDetailId);
	
	FeeDetail getFeeDetailByType(String type);
	
	List<FeeDetail> getAllFeeDetails();
	
}
