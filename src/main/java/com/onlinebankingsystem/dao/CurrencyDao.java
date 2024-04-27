package com.onlinebankingsystem.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.onlinebankingsystem.entity.Currency;

@Repository
public interface CurrencyDao extends JpaRepository <Currency, Long> {
	
	Currency findByCode(String code);
 
}