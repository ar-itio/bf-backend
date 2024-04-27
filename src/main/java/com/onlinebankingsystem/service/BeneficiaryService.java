package com.onlinebankingsystem.service;

import java.util.List;

import com.onlinebankingsystem.entity.Beneficiary;
import com.onlinebankingsystem.entity.User;

public interface BeneficiaryService {

	Beneficiary addBeneficiary(Beneficiary beneficiary);
	
	Beneficiary updateBeneficiary(Beneficiary beneficiary);
	
	Beneficiary getById(int beneficiaryId);
	
	List<Beneficiary> getAllUserBeneficiaryAndStatus(User user, String status);
	
}
