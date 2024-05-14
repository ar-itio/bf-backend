package com.onlinebankingsystem.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.onlinebankingsystem.entity.HostingDetail;

@Repository
public interface HostingDetailDao extends JpaRepository<HostingDetail, Long> {

    HostingDetail findFirstById(Long id);

}
