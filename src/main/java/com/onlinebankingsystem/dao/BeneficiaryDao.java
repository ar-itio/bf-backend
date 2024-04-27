package com.onlinebankingsystem.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.onlinebankingsystem.entity.Beneficiary;
import com.onlinebankingsystem.entity.User;

@Repository
public interface BeneficiaryDao extends JpaRepository<Beneficiary, Integer> {

	List<Beneficiary> findByUserAndStatus(User user, String status);

}
