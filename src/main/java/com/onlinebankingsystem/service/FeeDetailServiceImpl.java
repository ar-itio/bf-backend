package com.onlinebankingsystem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlinebankingsystem.dao.FeeDetailDao;
import com.onlinebankingsystem.entity.FeeDetail;

@Service
public class FeeDetailServiceImpl implements FeeDetailService {

	@Autowired
	private FeeDetailDao feeDetailDao;
	
	@Override
	public FeeDetail addFeeDetail(FeeDetail feeDetail) {
		// TODO Auto-generated method stub
		return feeDetailDao.save(feeDetail);
	}

	@Override
	public FeeDetail getFeeDetailById(int feeDetailId) {
		
		Optional<FeeDetail> optional = this.feeDetailDao.findById(feeDetailId);
		
		if(optional.isPresent()) {
			return optional.get();
		}
		
		return null;
	}

	@Override
	public FeeDetail getFeeDetailByType(String type) {
		// TODO Auto-generated method stub
		return this.feeDetailDao.findByType(type);
	}

	@Override
	public List<FeeDetail> getAllFeeDetails() {
		// TODO Auto-generated method stub
		return feeDetailDao.findAll();
	}

}
