package com.onlinebankingsystem.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.onlinebankingsystem.entity.AdminAccount;

@Repository
public interface AdminAccountDao extends JpaRepository<AdminAccount, Long> {

    AdminAccount findFirstById(Long id);
}