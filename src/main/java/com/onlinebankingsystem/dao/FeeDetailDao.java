package com.onlinebankingsystem.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.onlinebankingsystem.entity.FeeDetail;

@Repository
public interface FeeDetailDao extends JpaRepository<FeeDetail, Integer>{
	
	FeeDetail findByType(String type);

}
