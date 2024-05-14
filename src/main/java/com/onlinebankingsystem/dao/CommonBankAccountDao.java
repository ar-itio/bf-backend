package com.onlinebankingsystem.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.onlinebankingsystem.entity.CommonBankAccount;

@Repository
public interface CommonBankAccountDao extends JpaRepository<CommonBankAccount, Long> {

    CommonBankAccount findByIban(String iban);

    CommonBankAccount deleteByIban(String iban);

}