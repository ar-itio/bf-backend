package com.onlinebankingsystem.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.onlinebankingsystem.entity.EmailTempDetails;

@Repository
public interface EmailTempDetailsDao  extends JpaRepository<EmailTempDetails, Integer>{
	
	EmailTempDetails findByCode(String type);

}
