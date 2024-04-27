package com.onlinebankingsystem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlinebankingsystem.dao.BeneficiaryDao;
import com.onlinebankingsystem.entity.BankTransaction;
import com.onlinebankingsystem.entity.Beneficiary;
import com.onlinebankingsystem.entity.User;

@Service
public class BeneficiaryServiceImpl implements BeneficiaryService {

	@Autowired
	private BeneficiaryDao beneficiaryDao;
	
	@Override
	public Beneficiary addBeneficiary(Beneficiary beneficiary) {
		// TODO Auto-generated method stub
		return beneficiaryDao.save(beneficiary);
	}

	@Override
	public Beneficiary updateBeneficiary(Beneficiary beneficiary) {
		// TODO Auto-generated method stub
		return beneficiaryDao.save(beneficiary);
	}

	@Override
	public Beneficiary getById(int beneficiaryId) {
		// TODO Auto-generated method stub
		Optional<Beneficiary> optional = beneficiaryDao.findById(beneficiaryId);

		if (optional.isPresent()) {
			return optional.get();
		}
		
		return null;
	}

	@Override
	public List<Beneficiary> getAllUserBeneficiaryAndStatus(User user, String status) {
		// TODO Auto-generated method stub
		return beneficiaryDao.findByUserAndStatus(user, status);
	}

	
	
}
