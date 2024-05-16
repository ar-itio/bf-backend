package com.onlinebankingsystem.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.onlinebankingsystem.entity.UserAccounts;

@Repository
public interface UserAccountDao extends JpaRepository<UserAccounts, Integer> {
    List<UserAccounts> findByUserId(String userId);

    List<UserAccounts> findByStatus(String Status);

    UserAccounts findByAccountNumber(String Acno);

    UserAccounts findById(int Acno);

}
