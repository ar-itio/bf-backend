package com.onlinebankingsystem.dto;

import java.util.ArrayList;
import java.util.List;

import com.onlinebankingsystem.entity.FeeDetail;

public class FeeDetailResponse extends CommonApiResponse {

	List<FeeDetail> feeDetails = new ArrayList<>();

	public List<FeeDetail> getFeeDetails() {
		return feeDetails;
	}

	public void setFeeDetails(List<FeeDetail> feeDetails) {
		this.feeDetails = feeDetails;
	}

}
