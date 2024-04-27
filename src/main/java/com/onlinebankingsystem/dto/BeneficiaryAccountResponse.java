package com.onlinebankingsystem.dto;

import java.util.ArrayList;
import java.util.List;

import com.onlinebankingsystem.entity.Beneficiary;

public class BeneficiaryAccountResponse extends CommonApiResponse {

	private List<Beneficiary> beneficiaryAccounts = new ArrayList<>();

	public List<Beneficiary> getBeneficiaryAccounts() {
		return beneficiaryAccounts;
	}

	public void setBeneficiaryAccounts(List<Beneficiary> beneficiaryAccounts) {
		this.beneficiaryAccounts = beneficiaryAccounts;
	}

}
